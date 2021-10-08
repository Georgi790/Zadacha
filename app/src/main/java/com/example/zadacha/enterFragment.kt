package com.example.zadacha

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.zadacha.databinding.FragmentEnterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_enter.*
import kotlinx.android.synthetic.main.fragment_register.*

class enterFragment : Fragment(R.layout.fragment_enter) {
    private lateinit var auth: FirebaseAuth;

    private var _binding: FragmentEnterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentEnterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }

    }
    private fun updateUI(user: FirebaseUser?) {
    }

      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
          auth = Firebase.auth
        binding.LoginButton.setOnClickListener {

            if (binding.mailInput.text.toString().isNotEmpty() && binding.passwordInput.text.toString().isNotEmpty())
                    {
                val email = binding.mailInput.text.toString()
                val password = binding.passwordInput.text.toString()

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                           if(auth.currentUser!=null)
                            findNavController().navigate(R.id.action_enterFragment_to_voteFragment)
                        } else {
                            Toast.makeText(view.context, "Login faild, try again or make a registration", Toast.LENGTH_SHORT).show()
                        }

                    }

            }

        }
        binding.RegisterButton.setOnClickListener {
            findNavController().navigate(R.id.action_enterFragment_to_registerFragment)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    }












