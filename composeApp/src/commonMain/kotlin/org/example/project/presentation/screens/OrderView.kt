package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.OrderStatusApi
import org.example.project.data.api.TransactionStatusApi
import org.example.project.data.repository.OrderStatusRepository
import org.example.project.data.repository.TransactionStatusRepository
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.*
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Navigator
import org.example.project.presentation.components.common.ScrollableNavigator
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.OrderStatusViewModel
import org.example.project.presentation.viewmodel.TransactionStatusViewModel

class OrderView : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val currentPage = remember { mutableStateOf(0) }
        val totalPage = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(true)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)

        val tabTitles = mutableStateOf(listOf("All"))
        val orderStatusViewModel = OrderStatusViewModel(
            OrderStatusRepository(
                OrderStatusApi()
            )
        )

        scope.launch {
            handlerGetAllOrderStatuses(
                orderStatusViewModel = orderStatusViewModel,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                tabTitles = tabTitles,
            )
        }

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            val selectedTabIndex = remember { mutableStateOf(0) }
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
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
        AlertDialog(
            showDialog = showErrorDialog,
            rootMaxWidth = rootMaxWidth
        )
    }
}

suspend fun handlerGetAllOrderStatuses(
    orderStatusViewModel: OrderStatusViewModel,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    tabTitles: MutableState<List<String>>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            val titles = mutableListOf("All")
            orderStatusViewModel.getAllOrderStatuss(0)
            orderStatusViewModel.orderStatussList.value.forEach {
                if (!it.name.isNullOrEmpty())
                    titles.add(it.name)
            }
            tabTitles.value = titles
        }
    )
    checkError(
        showErrorDialog = showErrorDialog,
        operationStatus = orderStatusViewModel.operationStatus,
        onSuccess = {}
    )
}