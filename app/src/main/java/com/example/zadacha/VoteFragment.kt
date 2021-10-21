package com.example.zadacha

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.zadacha.databinding.FragmentVoteBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject


class VoteFragment : Fragment(R.layout.fragment_vote) {

    private var _binding: FragmentVoteBinding? = null
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVoteBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readData() {
            Log.d("Tag", it.size.toString())
            val firstSong = it.first()
            binding.radioButton1.setText(firstSong.toString())
            binding.radioButton2.setText(firstSong.toString())
            binding.radioButton3.setText(firstSong.toString())


        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun readData(mySongs: (List<Song>) -> Unit) {
        db.collection("Songs")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = ArrayList<Song>()
                    for (document in task.result!!) {
                        val song = document.toObject<Song>()
                        list.add(song)
                    }
                    mySongs(list)
                }
            }
    }

}


















