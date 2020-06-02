package com.example.cleannotes.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.LoadAllNotes
import com.example.cleannotes.ui.OnNoteClick
import com.example.cleannotes.ui.main.adapter.NoteListAdapter
import com.example.domain.model.Note
import com.example.domain.state.OnEmptyDataState
import com.example.domain.state.OnErrorState
import com.example.domain.state.OnLoadingState
import com.example.domain.state.OnSuccessState
import kotlinx.android.synthetic.main.note_list_fragment.*

class NoteListFragment : BaseFragment(R.layout.note_list_fragment), OnNoteClick {

    private lateinit var recyclerAdapter: NoteListAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerAdapter = NoteListAdapter(this@NoteListFragment)
        setupRecycler()
        setupButton()
        viewModel.obtainEvent(event = LoadAllNotes)
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                OnLoadingState -> onLoadingState()
                OnEmptyDataState -> onEmptyState()
                is OnSuccessState<*> -> onSuccessState(data = state.data as List<Note>)
                is OnErrorState -> onErrorState(message = state.message)
            }
        })
    }

    override fun onClick(noteId: Long) {
        Navigation.findNavController(btnCreateNewNote).navigate(
            NoteListFragmentDirections.toNote().setNoteId(noteId)
        )
    }

    private fun onSuccessState(data: List<Note>) {
        recyclerAdapter.updateNoteList(newNotes = data)
    }

    private fun onErrorState(message: String) {
        displayMessage(message = message)
    }

    private fun onEmptyState() {
        displayMessage(message = "Empty list")
    }

    private fun onLoadingState() {
        displayMessage(message = "Loading")
    }

    private fun setupButton() {
        btnCreateNewNote.setOnClickListener {
            Navigation.findNavController(it).navigate(
                NoteListFragmentDirections.toCreateNoteScreen()
            )
        }
    }

    private fun setupRecycler() {
        noteList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
        }
    }

}
