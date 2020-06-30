package com.example.cleannotes.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cleannotes.ui.AppViewModel
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(layout: Int) : Fragment(layout) {

    protected val viewModel: AppViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeState()
    }

    abstract fun observeState()

    protected fun displayMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
            .show()
    }

}