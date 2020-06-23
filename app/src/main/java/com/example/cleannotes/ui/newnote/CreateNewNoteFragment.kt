package com.example.cleannotes.ui.newnote

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.CreateNewNote
import com.example.cleannotes.util.NoteValidator
import com.example.domain.state.OnErrorState
import com.example.domain.state.OnSuccessActionState
import kotlinx.android.synthetic.main.create_new_note_fragment.*
import org.koin.android.ext.android.inject

class CreateNewNoteFragment : BaseFragment(R.layout.create_new_note_fragment) {

    private val validator: NoteValidator by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupButton()
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                OnSuccessActionState -> onSuccessState()
                is OnErrorState -> onErrorState(message = state.message)

            }
        })
    }

    private fun onErrorState(message: String) {
        displayMessage(message = message)
    }

    private fun onSuccessState() {
        Navigation.findNavController(btnAddNewNote).navigate(
            CreateNewNoteFragmentDirections.toNoteListScreen()
        )
    }

    private fun setupButton() {
        btnAddNewNote.setOnClickListener {
            val title = newNoteTitle.text.toString()
            val description = newNoteDescription.text.toString()
            createNewNote(title = title, description = description)
        }
    }

    private fun createNewNote(title: String, description: String) {
        if (validator.validate(title, description)) {
            val event = CreateNewNote(title = title, description = description)
            viewModel.obtainEvent(event = event)
        } else {
            displayMessage(message = "All fields must be fill!")
        }
    }
}
