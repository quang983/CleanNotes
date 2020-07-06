package com.example.cleannotes.ui.notes.newnote

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.CreateNewNote
import com.example.cleannotes.event.LoadAllGroups
import com.example.cleannotes.util.hide
import com.example.cleannotes.util.interfaces.NoteValidator
import com.example.domain.model.Group
import com.example.domain.state.OnErrorNoteState
import com.example.domain.state.OnSuccessLoadGroupListState
import com.example.domain.state.OnSuccessNoteActionState
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_new_note_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateNewNoteFragment : BaseFragment(R.layout.create_new_note_fragment) {

    private val viewModel: NewNoteViewModel by viewModels()
    @Inject
    lateinit var validator: NoteValidator

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.obtainEvent(event = LoadAllGroups)
        setupButton()
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                OnSuccessNoteActionState -> onSuccessState()
                is OnSuccessLoadGroupListState -> onSuccessLoadGroups(groups = state.data)
                is OnErrorNoteState -> displayMessage(requireView(), message = state.message)
            }
        }
    }

    private fun onSuccessLoadGroups(groups: List<Group>) {
        if (groups.isNotEmpty()) {
            for (group in groups) {
                val chip = buildChip(group = group)
                chipGroup.addView(chip)
            }
        } else {
            groupsLabel.hide()
            chipGroup.hide()
        }
    }

    private fun onSuccessState() {
        Navigation.findNavController(requireView()).navigate(
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
            val groupId = chipGroup.checkedChipId.toLong()
            viewModel.obtainEvent(
                event = CreateNewNote(
                    title = title,
                    description = description,
                    group = groupId
                )
            )
        } else {
            displayMessage(requireView(), message = "All fields must be fill!")
        }
    }

    private fun buildChip(group: Group): Chip {
        val chip = Chip(chipGroup.context)
        chip.apply {
            id = group.id?.toInt()!!
            text = group.name
            isClickable = true
            isCheckable = true
        }
        return chip
    }
}
