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
import org.example.project.ui.components.ColumnBackground
import org.example.project.ui.components.Header
import org.example.project.ui.components.TransactionTable
import org.example.project.ui.theme.Size

class TransactionView: Screen {
    @Composable
    override fun Content() {
        ColumnBackground {
            item {
                Column(
                    modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                ) {
                    TransactionTable()

                }
            }
        }
    }
}