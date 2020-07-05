package com.example.cleannotes.ui.groups.group

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.DeleteGroup
import com.example.cleannotes.event.LoadGroupById
import com.example.cleannotes.ui.groups.group.adapter.GroupAdapter
import com.example.cleannotes.ui.interfaces.OnItemClicked
import com.example.cleannotes.util.buildDialog
import com.example.cleannotes.util.hide
import com.example.cleannotes.util.show
import com.example.domain.model.Note
import com.example.domain.state.EmptyGroupsState
import com.example.domain.state.OnErrorGroupState
import com.example.domain.state.OnSuccessGroupActionState
import com.example.domain.state.OnSuccessLoadGroupState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_group.*

@AndroidEntryPoint
class GroupFragment : BaseFragment(R.layout.fragment_group), OnItemClicked {

    private var groupId: Long = 0

    private lateinit var groupAdapter: GroupAdapter
    private val viewModel: GroupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            groupId = GroupFragmentArgs.fromBundle(it).groupId
        }
        viewModel.obtainEvent(event = LoadGroupById(id = groupId))
        groupAdapter = GroupAdapter(this@GroupFragment)
        setupRecycler()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.group_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_group -> deleteGroup()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                EmptyGroupsState -> onEmptyState()
                OnSuccessGroupActionState -> onSuccessAction()
                is OnSuccessLoadGroupState -> onSuccessState(data = state.data)
                is OnErrorGroupState -> displayMessage(requireView(), message = state.message)
            }
        }
    }

    private fun onSuccessAction() {
        Navigation.findNavController(requireView()).navigate(
            GroupFragmentDirections.toGroupList()
        )
    }

    private fun onSuccessState(data: List<Note>) {
        groupAdapter.updateList(newNotes = data)
        groupEmptyMessage.hide()
        groupNotesList.show()
    }

    private fun onEmptyState() {
        groupNotesList.hide()
        groupEmptyMessage.show()
    }

    override fun onClick(id: Long) {
        Navigation.findNavController(requireView()).navigate(
            GroupFragmentDirections.toNote().setNoteId(id)
        )
    }

    private fun deleteGroup() {
        val dialog = buildDialog(
            requireContext(),
            "Delete group",
            viewModel::obtainEvent,
            event = DeleteGroup(groupId = groupId)
        )
        dialog.show()
    }

    private fun setupRecycler() {
        groupNotesList.apply {
            adapter = groupAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}