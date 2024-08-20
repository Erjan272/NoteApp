package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteBinding
import com.example.noteapp.utils.PreferenceHelper

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        val sharedPreferences = PreferenceHelper()
        sharedPreferences.unit(requireContext())
        binding.btnSave.setOnClickListener {
            val et = binding.etText.text.toString()
            sharedPreferences.text = et
            binding.tvSave.text = et
        }
        binding.tvSave.text = sharedPreferences.text

        binding.btnNext.setOnClickListener{
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment,null)
        }
    }

}