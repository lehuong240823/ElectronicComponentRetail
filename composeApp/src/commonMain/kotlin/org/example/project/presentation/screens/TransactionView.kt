package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.data.api.TransactionStatusApi
import org.example.project.data.repository.TransactionStatusRepository
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.table.TransactionTable
import org.example.project.presentation.components.common.Navigator
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.TransactionStatusViewModel

class TransactionView: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope{ Dispatchers.Default}
        val showLoadingOverlay = mutableStateOf(true)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val showErrorDialog = remember { mutableStateOf(false) }

        val selectedTabIndex = mutableStateOf(0)
        val tabTitles = mutableStateOf(listOf("All"))
        val transactionStatusViewModel = TransactionStatusViewModel(
            TransactionStatusRepository(
                TransactionStatusApi()
            )
        )

        scope.launch {
            val titles = mutableListOf("All")
            transactionStatusViewModel.getAllTransactionStatuss(0)
            transactionStatusViewModel.transactionStatussList.value.forEach {
                if (!it.name.isNullOrEmpty())
                    titles.add(it.name)
            }
            tabTitles.value  = titles
            showLoadingOverlay.value = false

            if (transactionStatusViewModel.operationStatus.value.contains("Failed")){
                showErrorDialog.value = true
            }
        }

        AlertDialog(
            showDialog = showErrorDialog,
            rootMaxWidth = rootMaxWidth
        )

        ColumnBackground(
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Navigator(
                    tabTitles = tabTitles.value,
                    selectedTabIndex = selectedTabIndex
                )
                TransactionTable()
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

