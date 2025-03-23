package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.example.project.data.args.ReviewArgs

import org.jetbrains.compose.ui.tooling.preview.Preview

import org.example.project.ui.screens.ManageSuppliers
import org.example.project.ui.screens.ManageVouchers
import org.example.project.ui.screens.Register
import org.example.project.ui.screens.Review

import org.example.project.ui.screens.ManageCategories
import org.example.project.ui.screens.ManageProducts

import org.example.project.ui.screens.CustomerProducts
import org.example.project.ui.screens.ShoppingCart

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

//        Screen arguments
        val reviewArgs: ReviewArgs = ReviewArgs()

        NavHost(navController = navController, startDestination = "register") {
            composable("register") { Register(navController) }
            composable("manageSuppliers") { ManageSuppliers(navController) }
            composable("manageVouchers") { ManageVouchers(navController) }
            composable("review") { Review(navController, reviewArgs) }
            composable("manageCategories") { ManageCategories(navController) }
            composable("manageProducts") { ManageProducts(navController) }
            composable("customerProducts") { CustomerProducts(navController) }
            composable("shoppingCart") { ShoppingCart(navController) }
        }
    }
}
