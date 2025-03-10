package org.example.project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.ui.components.*
import org.example.project.ui.theme.Size

class UserViewOrder: Screen {
    @Composable
    override fun Content() {
        ColumnBackground {
            item {
                val selectedTabIndex = remember { mutableStateOf(0) }
                Column(
                    modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                ) {
                    Navigator(
                        tabTitles = listOf("All", "Pending", "Shipped", "Delivered", "Cancelled", "Refunded"),
                        selectedTabIndex = selectedTabIndex,
                    )
                    when (selectedTabIndex.value) {
                        0 -> {
                            UserOrderItem(buttonGroup = { pendingButtonGroup() })
                        }
                        1 -> {
                            UserOrderItem(buttonGroup = { pendingButtonGroup() })
                        }
                        2 -> {
                            UserOrderItem(buttonGroup = { shippedButtonGroup() })
                        }
                        3 -> {
                            UserOrderItem(buttonGroup = { deliveredButtonGroup() })
                        }
                        4 -> {
                            UserOrderItem(buttonGroup = { cancelledRefundedButtonGroup() })
                        }
                        5 -> {
                            UserOrderItem(buttonGroup = { returnedRefundedButtonGroup() })
                        }
                    }
                }
            }
        }
    }
}