package org.example.project.ui.widgets

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomAppBar(navController: NavController) {
    TopAppBar(
        actions = {
            Button(
                onClick = { navController.navigate("customerProducts") }
            ) { Text("Customer View", fontWeight = FontWeight.Normal, color = Color.White) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = { navController.navigate("manageSuppliers") }
            ) { Text("Suppliers", fontWeight = FontWeight.Normal) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = { navController.navigate("manageVouchers") }
            ) { Text("Vouchers", fontWeight = FontWeight.Normal) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = { navController.navigate("manageCategories") }
            ) { Text("Categories", fontWeight = FontWeight.Normal) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = { navController.navigate("manageProducts") }
            ) { Text("Products", fontWeight = FontWeight.Normal) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = { navController.navigate("review") }
            ) { Text("Review", fontWeight = FontWeight.Normal) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = {}
            ) { Text("Resources", fontWeight = FontWeight.Normal) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = {}
            ) { Text("Pricing", fontWeight = FontWeight.Normal) }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                elevation = ButtonDefaults.elevation(0.dp),
                onClick = {}
            ) { Text("Contacts", fontWeight = FontWeight.Normal) }
        },
        backgroundColor = Color.White,
        title = {}
    )
}