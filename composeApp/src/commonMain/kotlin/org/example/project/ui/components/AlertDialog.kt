package org.example.project.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun AlertDialog(title: String, message: String, showDialog: Boolean) {
    var showDialog by remember { mutableStateOf(value = false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                Button(text = "Confirm",
                    onClick = { showDialog = false })
            },
            dismissButton = {
                Button(text = "Dimiss",
                    onClick = { showDialog = true })
            }
        )
    }

}