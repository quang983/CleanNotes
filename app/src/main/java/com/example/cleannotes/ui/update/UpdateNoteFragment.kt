package com.example.cleannotes.ui.update

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.GetNoteById
import com.example.cleannotes.event.UpdateNote
import com.example.cleannotes.ui.note.NoteViewModel
import com.example.domain.model.Note
import com.example.domain.state.*
import kotlinx.android.synthetic.main.update_note_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateNoteFragment : BaseFragment(R.layout.update_note_fragment) {

    private val viewModel: NoteViewModel by viewModel()
    private var noteId: Long = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            noteId = UpdateNoteFragmentArgs.fromBundle(it).noteId
            viewModel.obtainEvent(event = GetNoteById(id = noteId))
        }
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                OnSuccessActionState -> onSuccessAction()
                is OnSuccessState<*> -> onSuccessState(data = state.data as Note)
                is OnErrorState -> onErrorState(message = state.message)
            }
        })
    }

    private fun onErrorState(message: String) {
        displayMessage(message = message)
    }

    private fun onSuccessState(data: Note) {
        setupFields(note = data)
        btnUpdateNote.setOnClickListener {
            val newTitle = updatedNoteTitle.text.toString()
            val newDescription = updatedNoteDescription.text.toString()
            if (newTitle.isNotEmpty() && newDescription.isNotEmpty()) {
                val note = Note(id = noteId, title = newTitle, description = newDescription)
                viewModel.obtainEvent(
                    event = UpdateNote(note = note)
                )
            }
        }
    }

    private fun setupFields(note: Note) {
        updatedNoteTitle.setText(note.title, TextView.BufferType.EDITABLE)
        updatedNoteDescription.setText(note.description, TextView.BufferType.EDITABLE)
    }

    private fun onSuccessAction() {
        displayMessage(message = "Success")
        Navigation.findNavController(btnUpdateNote).navigate(
            UpdateNoteFragmentDirections.backToNoteScreen().setNoteId(noteId)
        )
    }
}