package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.table.CartTable
import org.example.project.presentation.theme.Size

class CartView: Screen {
    @Composable
    override fun Content() {
        val showLoadingOverlay = mutableStateOf(false)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val selectAllChecked = remember { mutableStateOf(false) }

        ColumnBackground(
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CartTable(
                    selectAllChecked = selectAllChecked,
                    onselectAllCheckedChange = {
                        selectAllChecked.value = it
                    }
                )
                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {}
                    )
                }
            }
        }
    }
}

