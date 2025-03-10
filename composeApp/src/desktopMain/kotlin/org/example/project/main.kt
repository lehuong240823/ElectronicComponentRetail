package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    System.setProperty("sun.java2d.uiScale", "1.0") // Force 1:1 scaling (Desktop)
    System.setProperty("compose.ui.rendering", "skia") // Ensure Skia rendering
    Window(
        onCloseRequest = ::exitApplication,
        title = "ElectronicComponentRetail",
        state = WindowState(WindowPlacement.Maximized)
    ) {
        App()
    }
}