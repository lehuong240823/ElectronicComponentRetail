package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.ui.screens.SignIn

@Composable
@androidx.compose.desktop.ui.tooling.preview.Preview
fun App() {


    MaterialTheme {
        Navigator(SignIn())
    }
}


fun pushWithLimitScreen(navigator: Navigator?, screen: Screen) {
    navigator?.size?.let {
        if (it >= 3) {
            var screens = navigator.items.drop(1)
            navigator.replaceAll(screens)
        }
    }
    navigator?.push(screen)
}
