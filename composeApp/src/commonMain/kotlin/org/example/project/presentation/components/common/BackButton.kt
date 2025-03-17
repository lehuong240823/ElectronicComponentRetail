package org.example.project.presentation.components.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator

@Composable
fun BackButton() {
    val navigator = LocalNavigator.current
    IconButton(
        onClick = {
            navigator?.pop()
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null
        )
    }
}