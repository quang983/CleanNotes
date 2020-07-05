package com.example.cleannotes.ui.notes.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.DeleteAllNotes
import com.example.cleannotes.event.LoadAllNotes
import com.example.cleannotes.ui.interfaces.OnItemClicked
import com.example.cleannotes.ui.notes.main.adapter.NoteListAdapter
import com.example.cleannotes.util.buildDialog
import com.example.cleannotes.util.hide
import com.example.cleannotes.util.show
import com.example.domain.model.Note
import com.example.domain.state.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.note_list_fragment.*

@AndroidEntryPoint
class NoteListFragment : BaseFragment(R.layout.note_list_fragment),
    OnItemClicked {

    private val viewModel: NoteListViewModel by viewModels()
    private lateinit var recyclerAdapter: NoteListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        setupRecycler()
        setupButton()
        viewModel.obtainEvent(event = LoadAllNotes)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.note_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_all_notes -> deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                EmptyNotesState -> onEmptyState()
                OnSuccessNoteActionState -> onSuccessActionState()
                is OnSuccessLoadNoteListState -> onSuccessState(data = state.data)
                is OnErrorNoteState -> displayMessage(requireView(), message = state.message)
            }
        }
    }

    override fun onClick(id: Long) {
        Navigation.findNavController(requireView()).navigate(
            NoteListFragmentDirections.toNote().setNoteId(id)
        )
    }

    private fun onSuccessActionState() {
        displayMessage(noteList, "Success!")
        viewModel.obtainEvent(event = LoadAllNotes)
    }

    private fun onSuccessState(data: List<Note>) {
        noteListLoadingBar.hide()
        emptyNoteListMessage.hide()
        noteList.show()
        recyclerAdapter.updateNoteList(newNotes = data)
    }

    private fun onEmptyState() {
        noteListLoadingBar.hide()
        noteList.hide()
        emptyNoteListMessage.show()
    }

    private fun setupButton() {
        btnCreateNewNote.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(
                NoteListFragmentDirections.toCreateNoteScreen()
            )
        }
    }

    private fun setupRecycler() {
        recyclerAdapter = NoteListAdapter(this@NoteListFragment)
        noteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

    private fun deleteAllNotes() {
        val dialog = buildDialog(
            requireContext(),
            "Delete notes",
            viewModel::obtainEvent,
            DeleteAllNotes
        )
        dialog.show()
    }
}
