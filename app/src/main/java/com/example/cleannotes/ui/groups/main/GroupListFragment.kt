package com.example.cleannotes.ui.groups.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.LoadAllGroups
import com.example.cleannotes.ui.groups.main.adapter.GroupListAdapter
import com.example.cleannotes.ui.interfaces.OnItemClicked
import com.example.cleannotes.util.hide
import com.example.cleannotes.util.show
import com.example.domain.model.Group
import com.example.domain.state.EmptyGroupsState
import com.example.domain.state.OnErrorGroupState
import com.example.domain.state.OnSuccessLoadGroupListState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_group_list.*

@AndroidEntryPoint
class GroupListFragment : BaseFragment(R.layout.fragment_group_list), OnItemClicked {

    private lateinit var groupsAdapter: GroupListAdapter
    private val viewModel: GroupListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        groupsAdapter = GroupListAdapter(this@GroupListFragment)
        setupRecycler()
        setupButton()
        viewModel.obtainEvent(event = LoadAllGroups)
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                EmptyGroupsState -> onEmptyGroupState()
                is OnSuccessLoadGroupListState -> onSuccessGroupListState(data = state.data)
                is OnErrorGroupState -> displayMessage(
                    requireView(), message = state.message
                )
            }
        }
    }

    private fun onSuccessGroupListState(data: List<Group>) {
        groupsAdapter.updateList(newGroups = data)
        emptyGroupListMessage.hide()
        groupList.show()
    }

    private fun onEmptyGroupState() {
        groupList.hide()
        emptyGroupListMessage.show()
    }

    override fun onClick(id: Long) {
        Navigation.findNavController(requireView()).navigate(
            GroupListFragmentDirections.toGroup().setGroupId(id)
        )
    }

    private fun setupButton() {
        btnCreateNewGroup.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(
                GroupListFragmentDirections.toCreateGroup()
            )
        }
    }

    private fun setupRecycler() {
        groupList.apply {
            adapter = groupsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}