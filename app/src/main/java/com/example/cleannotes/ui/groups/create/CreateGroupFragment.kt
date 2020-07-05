package com.example.cleannotes.ui.groups.create

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import com.example.cleannotes.R
import com.example.cleannotes.base.BaseFragment
import com.example.cleannotes.event.CreateNewGroup
import com.example.cleannotes.util.interfaces.GroupValidator
import com.example.domain.model.Group
import com.example.domain.state.OnErrorGroupState
import com.example.domain.state.OnSuccessGroupActionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_group.*
import javax.inject.Inject

@AndroidEntryPoint
class CreateGroupFragment: BaseFragment(R.layout.fragment_create_group) {

    @Inject lateinit var validator: GroupValidator
    private val viewModel: CreateGroupViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
    }

    override fun observeState() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                OnSuccessGroupActionState -> onSuccess()
                is OnErrorGroupState -> displayMessage(requireView(), message = state.message)
            }
        }
    }

    private fun onSuccess() {
        Navigation.findNavController(requireView()).navigate(
            CreateGroupFragmentDirections.toGroupList()
        )
    }

    private fun setupButton() {
        btnCreateGroup.setOnClickListener {
            val name = newGroupName.text.toString()
            if (validator.validate(name = name)) {
                val group = Group(name = name)
                viewModel.obtainEvent(event = CreateNewGroup(group = group))
            } else {
                displayMessage(requireView(), message = "All fields may be fill!")
            }
        }
    }
}