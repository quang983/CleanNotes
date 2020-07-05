package com.example.cleannotes.ui.notes.update

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.GetNoteById
import com.example.cleannotes.event.UpdateNote
import com.example.cleannotes.ui.notes.NoteViewModel
import com.example.cleannotes.util.interfaces.NoteValidator
import com.example.domain.model.Note
import com.example.domain.state.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.update_note_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class UpdateNoteFragment : BaseFragment(R.layout.update_note_fragment) {

    private var noteId: Long = 0
    @Inject lateinit var validator: NoteValidator
    private val viewModel: NoteViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            noteId = UpdateNoteFragmentArgs.fromBundle(it).noteId
            viewModel.obtainEvent(event = GetNoteById(id = noteId))
        }
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                OnSuccessNoteActionState -> onSuccessAction()
                is OnSuccessLoadNoteState -> onSuccessState(data = state.note)
                is OnErrorNoteState -> displayMessage(requireView(), message = state.message)
            }
        }
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
            displayMessage(view = updatedNoteTitle, message = "All fields must be fill!")
        }
    }

    private fun setupFields(note: Note) {
        updatedNoteTitle.setText(note.title, TextView.BufferType.EDITABLE)
        updatedNoteDescription.setText(note.description, TextView.BufferType.EDITABLE)
    }

    private fun onSuccessAction() {
        displayMessage(requireView(), message = "Success")
        Navigation.findNavController(requireView()).navigate(
            UpdateNoteFragmentDirections.toNoteList()
        )
    }
}