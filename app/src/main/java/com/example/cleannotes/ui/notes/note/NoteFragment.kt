package com.example.cleannotes.ui.notes.note

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.DeleteNote
import com.example.cleannotes.event.GetNoteById
import com.example.cleannotes.ui.notes.NoteViewModel
import com.example.cleannotes.util.buildDialog
import com.example.domain.model.Note
import com.example.domain.state.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.note_fragment.*

@AndroidEntryPoint
class NoteFragment : BaseFragment(R.layout.note_fragment) {

    private val viewModel: NoteViewModel by viewModels()
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
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                OnSuccessNoteActionState -> onSuccessActionState()
                is OnSuccessLoadNoteState -> {
                    val localNote = state.note
                    note = localNote
                    onSuccessState(data = localNote)
                }
                is OnErrorNoteState -> displayMessage(requireView(), message = state.message)
            }
        }
    }

    private fun onSuccessState(data: Note) {
        Log.i("Notes", data.groupId.toString())
        noteTitle.text = data.title
        noteDescription.text = data.description
    }

    private fun onSuccessActionState() {
        displayMessage(requireView(), message = "Success!")
        Navigation.findNavController(requireView()).navigate(
            NoteFragmentDirections.toNoteListScreen()
        )
    }

    private fun updateNote(id: Long) {
        Navigation.findNavController(requireView()).navigate(
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