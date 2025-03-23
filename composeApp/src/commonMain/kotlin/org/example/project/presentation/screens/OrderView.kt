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
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AlertType
import org.example.project.data.api.OrderApi
import org.example.project.data.api.OrderStatusApi
import org.example.project.data.api.UserApi
import org.example.project.data.repository.OrderRepository
import org.example.project.data.repository.OrderStatusRepository
import org.example.project.data.repository.UserRepository
import org.example.project.domain.model.Account
import org.example.project.domain.model.Order
import org.example.project.domain.model.OrderStatus
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.*
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Navigator
import org.example.project.presentation.components.common.ScrollableNavigator
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.OrderStatusViewModel
import org.example.project.presentation.viewmodel.OrderViewModel
import org.example.project.presentation.viewmodel.UserViewModel

class OrderView : Screen {
    @Composable
    override fun Content() {
        val currentAccount = SessionData.getCurrentAccount()
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val currentPage = remember { mutableStateOf(0) }
        val totalPage = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(true)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)
        val orderViewModel = OrderViewModel(OrderRepository(OrderApi()))
        val orderList = mutableStateOf(emptyList<Order>())
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
            handlerGetAllOrders(
                totalPage=totalPage,
                currentPage=currentPage,
                orderViewModel=orderViewModel,
                orderList=orderList,
                showLoadingOverlay=showLoadingOverlay,
                showErrorDialog=showErrorDialog,
                alertType=alertType,
                currentAccount = currentAccount
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
                        scope.launch {
                            handlerGetAllOrders(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                currentAccount = currentAccount
                            )
                        }
                        UserOrderItem(buttonGroup = { pendingButtonGroup() })
                    }
                    1 -> {
                        scope.launch {
                            handlerGetAllOrdersByStatus(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                orderStatus = mutableStateOf(OrderStatus(id = 0)),
                                currentAccount = currentAccount
                            )
                        }
                        UserOrderItem(buttonGroup = { pendingButtonGroup() })
                    }
                    2 -> {
                        scope.launch {
                            handlerGetAllOrdersByStatus(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                orderStatus = mutableStateOf(OrderStatus(id = 1)),
                                currentAccount = currentAccount
                            )
                        }
                        UserOrderItem(buttonGroup = { shippedButtonGroup() })
                    }
                    3 -> {
                        scope.launch {
                            handlerGetAllOrdersByStatus(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                orderStatus = mutableStateOf(OrderStatus(id = 2)),
                                currentAccount = currentAccount
                            )
                        }
                        UserOrderItem(buttonGroup = { deliveredButtonGroup() })
                    }
                    4 -> {
                        scope.launch {
                            handlerGetAllOrdersByStatus(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                orderStatus = mutableStateOf(OrderStatus(id = 3)),
                                currentAccount = currentAccount
                            )
                        }
                        UserOrderItem(buttonGroup = { cancelledRefundedButtonGroup() })
                    }
                    5 -> {
                        scope.launch {
                            handlerGetAllOrdersByStatus(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                orderStatus = mutableStateOf(OrderStatus(id = 4)),
                                currentAccount = currentAccount
                            )
                        }
                        UserOrderItem(buttonGroup = { processingButtonGroup() })
                    }
                    6 -> {
                        scope.launch {
                            handlerGetAllOrdersByStatus(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                orderStatus = mutableStateOf(OrderStatus(id = 5)),
                                currentAccount = currentAccount
                            )
                        }
                        UserOrderItem(buttonGroup = { processingButtonGroup() })
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
            orderStatusViewModel.orderStatussList.value.sortedBy { it.id }.forEach {
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

suspend fun handlerGetAllOrders(
    currentAccount: Account?,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            when(currentAccount?.accountRole?.name) {
                AccountRoleType.Administrator.name -> {
                    orderViewModel.getAllOrders(currentPage.value)
                }
                AccountRoleType.User.name -> {
                    val userViewModel = UserViewModel(UserRepository(UserApi()))
                    userViewModel.getUsersByAccountId(0, currentAccount.id?:0)
                    val user = userViewModel.usersList.value.first()
                    orderViewModel.getOrdersByUserId(currentPage.value, user.id?:0)
                }
            }
            totalPage.value = orderViewModel.totalPage.value ?: 0
            orderList.value = orderViewModel.ordersList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderViewModel.operationStatus,
    )
}

suspend fun handlerGetAllOrdersByStatus(
    currentAccount: Account?,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    orderStatus: MutableState<OrderStatus>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            val userViewModel = UserViewModel(UserRepository(UserApi()))
            userViewModel.getUsersByAccountId(0, currentAccount?.id?:0)
            val user = userViewModel.usersList.value.first()
            orderViewModel.getOrdersByOrderStatusId(currentPage.value, orderStatus.value.id ?: 0)
            totalPage.value = orderViewModel.totalPage.value ?: 0
            orderList.value = orderViewModel.ordersList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderViewModel.operationStatus,
    )
}

suspend fun handlerAddOrder(
    currentAccount: Account?,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    order: MutableState<Order>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            //orderViewModel.createOrder(order.value)
            handlerGetAllOrders(
                totalPage = totalPage,
                currentPage = currentPage,
                orderViewModel = orderViewModel,
                orderList = orderList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType,
                currentAccount = currentAccount
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderViewModel.operationStatus,
        onSuccess = {
            if (orderViewModel.createdOrder.value == null) {
                alertType.value = AlertType.Duplication
                showErrorDialog.value = true
            } else {
                alertType.value = AlertType.Success
                showErrorDialog.value = true
            }
        },
        onFailure = {
            alertType.value = AlertType.Default
        }
    )
}

suspend fun handlerEditOrder(
    currentAccount: Account?,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    order: MutableState<Order>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            orderViewModel.updateOrder(order.value.id ?: 0, order.value)
            handlerGetAllOrders(
                totalPage = totalPage,
                currentPage = currentPage,
                orderViewModel = orderViewModel,
                orderList = orderList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType,
                currentAccount=currentAccount,
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderViewModel.operationStatus,
    )
}