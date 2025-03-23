package org.example.project

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.example.project.presentation.screens.*
import org.example.project.presentation.theme.Typography

@Composable

fun App() {
    val inter = Typography.loadInterFontFamily()
    CompositionLocalProvider(LocalTextStyle.provides(LocalTextStyle.current.merge(fontFamily = inter))) {
    Navigator(ProductList())
    }

}


