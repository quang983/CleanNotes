package com.example.cleannotes.util

import android.app.AlertDialog
import android.content.Context
import com.example.cleannotes.event.Event

fun buildDialog(
    context: Context,
    title: String,
    action: (event: Event) -> Unit,
    event: Event
): AlertDialog {
    return AlertDialog.Builder(context)
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