package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.ui.screens.CreateOrder
import org.example.project.ui.screens.SignIn
import org.example.project.ui.screens.TransactionView
import org.example.project.ui.screens.UserViewOrder

@Composable

fun App() {

    MaterialTheme {
        //Navigator(SignIn())
        Navigator(TransactionView())

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
