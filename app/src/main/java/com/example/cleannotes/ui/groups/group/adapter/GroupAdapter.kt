package com.example.cleannotes.ui.groups.group.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleannotes.R
import com.example.cleannotes.ui.interfaces.OnItemClicked
import com.example.domain.model.Note
import kotlinx.android.synthetic.main.note_card.view.*

class GroupAdapter(
    private val listener: OnItemClicked
): RecyclerView.Adapter<GroupViewHolder>() {

    private val notes = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.note_card, parent, false
        )
        return GroupViewHolder(view = view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note = note, listener = listener)
    }

    fun updateList(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }
}

class GroupViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    fun bind(note: Note, listener: OnItemClicked) {
        view.noteCardTitle.text = note.title
        view.setOnClickListener {
            listener.onClick(id = note.id!!)
        }
    }
}