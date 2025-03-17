package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.presentation.components.table.CategoryTable
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.theme.Size

class CategoryView: Screen {
    @Composable
    override fun Content() {
        val totalPage = mutableStateOf(1)
        val currentPage = mutableStateOf(1)

        ColumnBackground {
                Column(
                    modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CategoryTable()
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

