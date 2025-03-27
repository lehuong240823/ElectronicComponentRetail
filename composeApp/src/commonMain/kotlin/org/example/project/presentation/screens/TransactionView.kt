package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AlertType
import org.example.project.data.api.TransactionApi
import org.example.project.data.api.TransactionStatusApi
import org.example.project.data.repository.TransactionRepository
import org.example.project.data.repository.TransactionStatusRepository
import org.example.project.domain.model.Account
import org.example.project.domain.model.Order
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
import org.example.project.presentation.viewmodel.OrderViewModel
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
        val alertType = mutableStateOf(AlertType.Default)
        val transactionViewModel = TransactionViewModel(TransactionRepository(TransactionApi()))
        val transactionList = mutableStateOf(listOf<Transaction>())
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
                        selectedTabIndex = selectedTabIndex,
                        currentPage = currentPage
                    )
                } else {
                    ScrollableNavigator(
                        tabTitles = tabTitles.value,
                        selectedTabIndex = selectedTabIndex,
                        currentPage = currentPage
                    )
                }

                fetchTransactionByAccountRoleAndTab(
                    scope = scope,
                    selectedTabIndex = selectedTabIndex,
                    totalPage = totalPage,
                    currentPage = currentPage,
                    transactionViewModel = transactionViewModel,
                    transactionList = transactionList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType,
                    currentAccount = currentAccount
                )

                TransactionTable(
                    transactionList = transactionList
                )

                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {
                            scope.launch {
                                fetchTransactionByAccountRoleAndTab(
                                    scope = scope,
                                    selectedTabIndex = selectedTabIndex,
                                    totalPage = totalPage,
                                    currentPage = currentPage,
                                    transactionViewModel = transactionViewModel,
                                    transactionList = transactionList,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType,
                                    currentAccount = currentAccount
                                )
                            }
                        }
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
            when (SessionData.getCurrentAccount()?.accountRole?.name) {
                AccountRoleType.Administrator.name -> {
                    transactionViewModel.getAllTransactions(currentPage.value)
                }

                AccountRoleType.User.name -> {
                    SessionData.getCurrentUser()?.id?.let {
                        transactionViewModel.getTransactionsByUserId(
                            currentPage.value,
                            it
                        )
                    }
                }
            }
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
    //transactionStatus: MutableState<TransactionStatus>,
    selectedTabIndex: MutableState<Int>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            when (SessionData.getCurrentAccount()?.accountRole?.name) {
                AccountRoleType.Administrator.name -> {
                    transactionViewModel.getTransactionsByTransactionStatusId(currentPage.value, selectedTabIndex.value.toByte())
                }

                AccountRoleType.User.name -> {
                    SessionData.getCurrentUser()?.id?.let {
                        transactionViewModel.getTransactionsByTransactionStatusIdAndUserId(
                            currentPage.value,
                            it,
                            selectedTabIndex.value.toByte()
                        )
                    }
                }
            }
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

fun fetchTransactionByAccountRoleAndTab(
    scope: CoroutineScope,
    selectedTabIndex: MutableState<Int>,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    transactionViewModel: TransactionViewModel,
    transactionList: MutableState<List<Transaction>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    currentAccount: Account?,
) {
    when (selectedTabIndex.value) {
        0 -> {
            scope.launch {
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
        }

        else -> {
            scope.launch {
                handlerGetAllTransactionsByStatus(
                    totalPage = totalPage,
                    currentPage = currentPage,
                    transactionViewModel = transactionViewModel,
                    transactionList = transactionList,
                    selectedTabIndex = selectedTabIndex,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
            }
        }
    }
}


