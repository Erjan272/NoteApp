package com.example.noteapp.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.ItemNoteBinding
import com.example.noteapp.interfaces.OnClickItem
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale


class NoteAdapter(
    private val onLongClick:OnClickItem,
    private val onClick:OnClickItem
): ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root) {

       private val dateFormat = SimpleDateFormat("EEEE, dd MMM", Locale.getDefault())
       private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        fun bind(item: NoteModel){

            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description

            val currentDate = dateFormat.format(Date())
            val currentTime = timeFormat.format(Date())

            binding.tvSaveData.text = currentDate
            binding.tvSaveTime.text = currentTime

            val background = GradientDrawable()
            background.cornerRadius = 20f
            if (!item.color.isNullOrEmpty()) {
                try {
                    background.setColor(Color.parseColor(item.color))
                } catch (e: IllegalArgumentException) {
                    background.setColor(Color.GREEN)
                }
            } else {
                background.setColor(Color.TRANSPARENT)
            }
            binding.itemColor.background = background
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnLongClickListener{
            onLongClick.onLongClick(getItem(position))
            true
        }
        holder.itemView.setOnClickListener{
            onClick.onClick(getItem(position))
        }
    }

    class DiffCallback:DiffUtil.ItemCallback<NoteModel>(){
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

    }
}