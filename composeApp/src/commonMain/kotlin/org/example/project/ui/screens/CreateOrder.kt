package org.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.ui.components.Button
import org.example.project.ui.components.Form
import org.example.project.ui.components.SideMenu
import org.example.project.ui.components.Table

class CreateOrder : Screen {
    @Composable
    override fun Content() {
        MaterialTheme {
            Row {
                SideMenu()
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {

                        Form(modifier = Modifier.fillMaxWidth().padding(48.dp)) {

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text("Address")
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("123456789")
                                    Text("123 Phường Phúc La, Quận Hà Đông, Hà Nội")
                                    Text("Default")
                                    Button(
                                        text = "Change",
                                        modifier = Modifier.width(120.dp),
                                        onClick = {}
                                    )
                                }
                            }



                            Table(
                                headers = listOf("Product", "Quantity", "Price", "Total"),
                                rows = listOf(
                                    listOf("Product", "Quantity", "Price", "Total"),
                                    listOf("Product", "Quantity", "Price", "Total")
                                )
                            )
                            Text("Transport")
                            Text("Voucher")
                            Text("Transport")
                            Row {
                                Text("Payment")
                            }

                            // Pricing Summary
                            Form {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Sub total")
                                    Text("1000")
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Shipping")
                                    Text("100")
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Total")
                                    Text("1000")
                                }
                                Button(text = "Place order", onClick = {})
                            }
                        }
                    }
                }
            }

        }
    }
}
