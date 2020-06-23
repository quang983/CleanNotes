package com.example.cleannotes.ui.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.DeleteNote
import com.example.cleannotes.event.GetNoteById
import com.example.cleannotes.util.buildDialog
import com.example.domain.model.Note
import com.example.domain.state.*
import kotlinx.android.synthetic.main.note_fragment.*

class NoteFragment : BaseFragment(R.layout.note_fragment) {

    private var note: Note? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            val noteId = NoteFragmentArgs.fromBundle(it).noteId
            viewModel.obtainEvent(event = GetNoteById(id = noteId))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_actions_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.update_note -> {
                note?.id?.let { id ->
                    updateNote(id = id)
                }
            }
            R.id.delete_note -> {
                note?.let {
                    deleteNote(note = it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                OnLoadingState -> onLoadingState()
                OnSuccessActionState -> onSuccessActionState()
                is OnSuccessLoadNoteState -> {
                    val localNote = state.note
                    note = localNote
                    onSuccessState(data = localNote)
                }
                is OnErrorState -> onErrorState(message = state.message)
            }
        })
    }

    private fun onSuccessState(data: Note) {
        noteTitle.text = data.title
        noteDescription.text = data.description
    }

    private fun onErrorState(message: String) {
        displayMessage(view = noteTitle, message = message)
    }

    private fun onSuccessActionState() {
        displayMessage(view = noteTitle, message = "Success!")
        Navigation.findNavController(noteTitle).navigate(
            NoteFragmentDirections.toNoteListScreen()
        )
    }

    private fun onLoadingState() {
        displayMessage(view = noteTitle, message = "Loading")
    }

    private fun updateNote(id: Long) {
        Navigation.findNavController(noteTitle).navigate(
            NoteFragmentDirections.toUpdateNoteScreen().setNoteId(id)
        )
    }

    private fun deleteNote(note: Note) {
        val dialog = buildDialog(
            requireContext(),
            "Delete note",
            viewModel::obtainEvent,
            event = DeleteNote(note = note)
        )
        dialog.show()
    }
}