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
                note?.id?.let {
                    Navigation.findNavController(noteTitle).navigate(
                        NoteFragmentDirections.toUpdateNoteScreen().setNoteId(it)
                    )
                }
            }
            R.id.delete_note -> {
                note?.let {
                    viewModel.obtainEvent(event = DeleteNote(note = it))
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
                is OnSuccessState<*> -> {
                    val localNote = state.data as Note
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
        displayMessage(message = message)
    }

    private fun onSuccessActionState() {
        displayMessage(message = "Success!")
        Navigation.findNavController(noteTitle).navigate(
            NoteFragmentDirections.toNoteListScreen()
        )
    }

    private fun onLoadingState() {
        displayMessage(message = "Loading")
    }
}