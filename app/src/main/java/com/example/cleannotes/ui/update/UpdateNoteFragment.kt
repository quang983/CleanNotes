package com.example.cleannotes.ui.update

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.GetNoteById
import com.example.cleannotes.event.UpdateNote
import com.example.cleannotes.util.NoteValidator
import com.example.domain.model.Note
import com.example.domain.state.*
import kotlinx.android.synthetic.main.update_note_fragment.*
import org.koin.android.ext.android.inject

class UpdateNoteFragment : BaseFragment(R.layout.update_note_fragment) {

    private var noteId: Long = 0
    private val validator: NoteValidator by inject()

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
            updateNote(title = newTitle, description = newDescription)
        }
    }

    private fun updateNote(title: String, description: String) {
        if (validator.validate(title, description)) {
            val note = Note(id = noteId, title = title, description = description)
            viewModel.obtainEvent(event = UpdateNote(note = note))
        } else {
            displayMessage(message = "All fields must be fill!")
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