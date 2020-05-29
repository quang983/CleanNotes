package com.example.cleannotes.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import com.example.cleannotes.R
import com.example.cleannotes.event.LoadAllNotes
import com.example.domain.model.Note
import com.example.domain.state.OnEmptyDataState
import com.example.domain.state.OnErrorState
import com.example.domain.state.OnLoadingState
import com.example.domain.state.OnSuccessState
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteListFragment : Fragment(R.layout.note_list_fragment) {

    private val viewModel: NoteListViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.obtainEvent(event = LoadAllNotes)
        observeState()
    }

    private fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                OnLoadingState -> onLoadingState()
                OnEmptyDataState -> onEmptyState()
                is OnSuccessState<*> -> onSuccessState(data = state.data as List<Note>)
                is OnErrorState -> onErrorState(message = state.message)
            }
        })
    }

    private fun onSuccessState(data: List<Note>) {

    }

    private fun onErrorState(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun onEmptyState() {
        Toast.makeText(context, "Empty list", Toast.LENGTH_SHORT).show()
    }

    private fun onLoadingState() {
        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
    }

}
