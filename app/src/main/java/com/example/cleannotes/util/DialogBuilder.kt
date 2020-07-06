package com.example.cleannotes.util

import androidx.appcompat.app.AlertDialog
import android.content.Context
import com.example.cleannotes.event.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun buildDialog(
    context: Context,
    title: String,
    action: (event: Event) -> Unit,
    event: Event
): AlertDialog {
    return MaterialAlertDialogBuilder(context)
        .setTitle(title)
        .setMessage("Are you sure?")
        .setPositiveButton("Yes") { _, _ ->
            action(event)
        }
        .setNegativeButton("No") { _, _ ->
            return@setNegativeButton
        }
        .create()
}
