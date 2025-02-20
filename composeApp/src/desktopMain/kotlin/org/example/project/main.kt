package org.example.project

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.ui.screens.ManageSuppliers

@Composable
fun app() {
    // Gọi ManageSuppliers để hiển thị giao diện
    ManageSuppliers()
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ElectronicComponentRetail",
    ) {
        app()
    }
}