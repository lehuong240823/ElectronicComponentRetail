package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.TransactionStatusApi
import org.example.project.data.repository.TransactionStatusRepository
import org.example.project.domain.model.Transaction
import org.example.project.domain.model.TransactionStatus
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Navigator
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.common.ScrollableNavigator
import org.example.project.presentation.components.table.TransactionTable
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.TransactionStatusViewModel
import org.example.project.presentation.viewmodel.TransactionViewModel

class TransactionView: Screen {
    @Composable
    override fun Content() {
        val currentAccount = SessionData.getCurrentAccount()
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
            handlerGetAllTransactionStatuses(
                transactionStatusViewModel = transactionStatusViewModel,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                tabTitles = tabTitles,
            )
        }

        AlertDialog(
            showDialog = showErrorDialog,
            rootMaxWidth = rootMaxWidth
        )

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {

            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (rootMaxWidth.value.isExpanded()) {
                    Navigator(
                        tabTitles = tabTitles.value,
                        selectedTabIndex = selectedTabIndex
                    )
                } else {
                    ScrollableNavigator(
                        tabTitles = tabTitles.value,
                        selectedTabIndex = selectedTabIndex
                    )
                }

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

suspend fun handlerGetAllTransactionStatuses(
    transactionStatusViewModel: TransactionStatusViewModel,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    tabTitles: MutableState<List<String>>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            val titles = mutableListOf("All")
            transactionStatusViewModel.getAllTransactionStatuss(0)
            transactionStatusViewModel.transactionStatussList.value.sortedBy { it.id }.forEach {
                if (!it.name.isNullOrEmpty())
                    titles.add(it.name)
            }
            tabTitles.value = titles
        }
    )
    checkError(
        showErrorDialog = showErrorDialog,
        operationStatus = transactionStatusViewModel.operationStatus,
        onSuccess = {}
    )
}

suspend fun handlerGetAllTransactions(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    transactionViewModel: TransactionViewModel,
    transactionList: MutableState<List<Transaction>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            transactionViewModel.getAllTransactions(currentPage.value)
            totalPage.value = transactionViewModel.totalPage.value ?: 0
            transactionList.value = transactionViewModel.transactionsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = transactionViewModel.operationStatus,
    )
}

suspend fun handlerGetAllTransactionsByStatus(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    transactionViewModel: TransactionViewModel,
    transactionList: MutableState<List<Transaction>>,
    transactionStatus: MutableState<TransactionStatus>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            transactionViewModel.getTransactionsByTransactionStatusId(
                currentPage.value,
                transactionStatus.value.id ?: 0
            )
            totalPage.value = transactionViewModel.totalPage.value ?: 0
            transactionList.value = transactionViewModel.transactionsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = transactionViewModel.operationStatus,
    )
}

/*suspend fun handlerEditTransaction(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    transactionViewModel: TransactionViewModel,
    transactionList: MutableState<List<Transaction>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    transaction: MutableState<Transaction>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            transactionViewModel.updateTransaction(transaction.value.id ?: 0, transaction.value)
            handlerGetAllTransactions(
                totalPage = totalPage,
                currentPage = currentPage,
                transactionViewModel = transactionViewModel,
                transactionList = transactionList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = transactionViewModel.operationStatus,
    )
}*/