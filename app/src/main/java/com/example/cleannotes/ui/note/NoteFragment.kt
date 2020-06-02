package com.example.cleannotes.ui.note

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.event.DeleteNote
import com.example.cleannotes.event.GetNoteById
import com.example.domain.model.Note
import com.example.domain.state.*
import kotlinx.android.synthetic.main.note_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteFragment : Fragment(R.layout.note_fragment) {

    private val viewModel: NoteViewModel by viewModel()
    private var note: Note? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            val noteId = NoteFragmentArgs.fromBundle(it).noteId
            viewModel.obtainEvent(event = GetNoteById(id = noteId))
        }
        observeState()
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

    private fun observeState() {
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
        showMessage(message = message)
    }

    private fun onSuccessActionState() {
        showMessage(message = "Success!")
        Navigation.findNavController(noteTitle).navigate(
            NoteFragmentDirections.toNoteListScreen()
        )
    }

    private fun onLoadingState() {
        showMessage(message = "Loading")
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}