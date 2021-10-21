package com.example.zadacha

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.zadacha.databinding.FragmentRegisterBinding
import com.example.zadacha.databinding.FragmentRegisterBinding.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_enter.*
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload();
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.registerButton.setOnClickListener {
            binding.registerButton.isEnabled = false

            val email = binding.enterMail.text.toString()
            val password = binding.enterPassword.text.toString()
            val repeatpassword = binding.repeatPassword.text.toString()

            if (binding.enterMail.text.toString().isEmpty() || binding.enterPassword.text.toString()
                    .isEmpty()
                || binding.repeatPassword.text.toString().isEmpty() ||
                binding.enterAge.text.toString().isEmpty()
            ) {
                Toast.makeText(view.context, "empty field", Toast.LENGTH_SHORT).show()
                binding.registerButton.isEnabled = true
                return@setOnClickListener
            }
            if (password != repeatpassword) {
                Toast.makeText(view.context, "Repeat password again", Toast.LENGTH_SHORT).show()
                binding.registerButton.isEnabled = true
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    binding.registerButton.isEnabled = true
                    if (task.isSuccessful) {
                        val db = Firebase.firestore
                        val users = hashMapOf(
                            "age" to binding.enterAge.text.toString().toInt(),
                            "email" to binding.enterMail.text.toString(),
                        )

                        db.collection("users")
                            .add(users)
                            .addOnSuccessListener { documentReference ->
                                Log.d(
                                    TAG,
                                    "DocumentSnapshot added with ID: ${documentReference.id}"
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }

                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        updateUI(user)

                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            requireActivity(), "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun reload() {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}





