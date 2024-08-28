package com.example.noteapp.ui.fragments.note


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private var noteId: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNote()
        setupListener()
        Time()
    }

    private fun updateNote() {
        arguments?.let {
            noteId = it.getInt("noteId", -1)
        }
        if(noteId != -1){
            val argsNote = App.appDataBase?.noteDao()?.getById(noteId)
            argsNote?.let {model ->
                binding.etTitle.setText(model.title)
                binding.etDescription.setText(model.description)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setupListener() {
        binding.btnBlack.setOnClickListener {
            binding.detailFragment.setBackgroundColor(Color.parseColor("#191818"))
        }
        binding.btnWhite.setOnClickListener {
            binding.detailFragment.setBackgroundColor(Color.parseColor("#EBE4C9"))
        }
        binding.btnDarkRed.setOnClickListener {
            binding.detailFragment.setBackgroundColor(Color.parseColor("#571818"))
        }

        binding.btnAddNote.setOnClickListener {

            val etTitle = binding.etTitle.text.toString().trim()
            val etDescription = binding.etDescription.text.toString().trim()

            val drawable = binding.detailFragment.background
            val color = if (drawable is ColorDrawable) {
                drawable.color
            } else {
                Color.TRANSPARENT
            }
            val fragmentColor = String.format("#%06X", (0xFFFFFF and color))

            if (noteId != -1){
                val updateModel = NoteModel(etTitle, etDescription, fragmentColor)
                updateModel.id = noteId
                App.appDataBase?.noteDao()?.update(updateModel)
            }
            else{
                App.appDataBase?.noteDao()?.insertNote(NoteModel(etTitle, etDescription, fragmentColor))
            }
            findNavController().navigateUp()

        }
        binding.btnArrow.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun Time() {
        val calendar = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd MMMM", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val formattedTime = timeFormat.format(calendar.time)
        binding.tvDate.text = formattedDate
        binding.tvTime.text = formattedTime

    }



}