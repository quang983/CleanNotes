package com.example.cleannotes.ui.notes.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleannotes.R
import com.example.cleannotes.ui.interfaces.OnItemClicked
import com.example.domain.model.Note
import kotlinx.android.synthetic.main.note_card.view.*

class NoteListAdapter(
    private val onItemClickedListener: OnItemClicked
): RecyclerView.Adapter<NoteViewHolder>() {

    private val notes: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.note_card, parent, false
        )
        return NoteViewHolder(view = view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note = note, listener = onItemClickedListener)
    }

    fun updateNoteList(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

}

class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    fun bind(note: Note, listener: OnItemClicked) {
        view.noteCardTitle.text = note.title
        view.setOnClickListener {
            listener.onClick(id = note.id!!)
        }
    }

}