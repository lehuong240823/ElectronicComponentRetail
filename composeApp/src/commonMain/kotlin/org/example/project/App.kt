package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.project.ui.screens.ManageSuppliers
import org.example.project.ui.screens.ManageVouchers
import org.example.project.ui.screens.Register

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "register") {
            composable("register") { Register(navController) }
            composable("manageSuppliers") { ManageSuppliers(navController) }
            composable("manageVouchers") { ManageVouchers(navController) }
        }
    }
}
