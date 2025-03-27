package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AlertType
import org.example.project.data.api.OrderApi
import org.example.project.data.api.OrderItemApi
import org.example.project.data.api.OrderStatusApi
import org.example.project.data.api.ProductImageApi
import org.example.project.data.repository.OrderItemRepository
import org.example.project.data.repository.OrderRepository
import org.example.project.data.repository.OrderStatusRepository
import org.example.project.data.repository.ProductImageRepository
import org.example.project.domain.model.Account
import org.example.project.domain.model.Order
import org.example.project.domain.model.OrderItem
import org.example.project.domain.model.OrderStatus
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.UserOrderItem
import org.example.project.presentation.components.card.handlerGetAllOrderItemsByOrderId
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Navigator
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.common.ScrollableNavigator
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.OrderItemViewModel
import org.example.project.presentation.viewmodel.OrderStatusViewModel
import org.example.project.presentation.viewmodel.OrderViewModel
import org.example.project.presentation.viewmodel.ProductImageViewModel

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
        val orderList = remember { mutableStateOf(emptyList<Order>()) }
        val tabTitles = mutableStateOf(listOf("All"))
        val orderStatusList = mutableStateOf(emptyList<OrderStatus>())
        val selectedTabIndex = remember { mutableStateOf(0) }
        val productImageViewModel = ProductImageViewModel(ProductImageRepository(ProductImageApi()))

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
                orderStatusList = orderStatusList,
            )
        }

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
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
                fetchByAccountRoleAndTab(
                    scope = scope,
                    selectedTabIndex = selectedTabIndex,
                    totalPage = totalPage,
                    currentPage = currentPage,
                    orderViewModel = orderViewModel,
                    orderList = orderList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType,
                    currentAccount = currentAccount,
                )
                orderList.value.forEach { order ->
                    val orderItemList = mutableStateOf(emptyList<OrderItem>())
                    val orderItemViewModel = OrderItemViewModel(OrderItemRepository(OrderItemApi()))

                    scope.launch {
                        handlerGetAllOrderItemsByOrderId(
                            orderItemViewModel = orderItemViewModel,
                            orderItemList = orderItemList,
                            order = order,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType
                        )
                    }
                    UserOrderItem(
                        order = mutableStateOf(order),
                        orderStatusList = orderStatusList,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        alertType = alertType,
                        orderItemList = orderItemList,
                        rootMaxWidth = rootMaxWidth,
                        scope = scope,
                        totalPage = totalPage,
                        currentPage = currentPage,
                        orderViewModel = orderViewModel,
                        orderList = orderList,
                        selectedTabIndex = selectedTabIndex,
                        productImageViewModel = productImageViewModel,
                    )
                }


                if (totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {
                            fetchByAccountRoleAndTab(
                                scope = scope,
                                selectedTabIndex = selectedTabIndex,
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                currentAccount = currentAccount,
                            )
                        }
                    )
                }
            }
            AlertDialog(
                showDialog = showErrorDialog,
                rootMaxWidth = rootMaxWidth
            )
        }

    }
}

suspend fun handlerGetAllOrderStatuses(
    orderStatusViewModel: OrderStatusViewModel,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    tabTitles: MutableState<List<String>>,
    orderStatusList: MutableState<List<OrderStatus>>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            val titles = mutableListOf("All")
            orderStatusViewModel.getAllOrderStatuss(0)
            orderStatusList.value = orderStatusViewModel.orderStatussList.value
            orderStatusList.value.sortedBy { it.id }.forEach {
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
            when (SessionData.getCurrentAccount()?.accountRole?.name) {
                AccountRoleType.Administrator.name -> {
                    orderViewModel.getAllOrders(currentPage.value)
                }
                AccountRoleType.User.name -> {
                    SessionData.getCurrentUser()?.id?.let { orderViewModel.getOrdersByUserId(currentPage.value, it) }
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
                    orderViewModel.getOrdersByOrderStatusId(currentPage.value, selectedTabIndex.value.toByte())
                }

                AccountRoleType.User.name -> {
                    SessionData.getCurrentUser()?.id?.let {
                        orderViewModel.getOrdersByOrderStatusIdAndUserId(
                            currentPage.value,
                            it,
                            selectedTabIndex.value.toByte()
                        )
                    }
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

suspend fun handlerAddOrder(
    scope: CoroutineScope,
    rootMaxWidth: MutableState<Int>,
    currentAccount: Account?,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    order: MutableState<Order>,
    orderStatusList: MutableState<List<OrderStatus>>,
    selectedTabIndex: MutableState<Int>,
    cartItemIds: MutableState<List<Int>>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            orderViewModel.createOrder(
                order.value,
                cartItemIds = cartItemIds.value
            )
            fetchByAccountRoleAndTab(
                scope = scope,
                selectedTabIndex = selectedTabIndex,
                totalPage = totalPage,
                currentPage = currentPage,
                orderViewModel = orderViewModel,
                orderList = orderList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType,
                currentAccount = currentAccount,
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
    scope: CoroutineScope,
    currentAccount: Account?,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    order: MutableState<Order>,
    selectedTabIndex: MutableState<Int>,
    orderStatusList: MutableState<List<OrderStatus>>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            orderViewModel.updateOrder(order.value.id ?: 0, order.value)
            fetchByAccountRoleAndTab(
                scope = scope,
                selectedTabIndex = selectedTabIndex,
                totalPage = totalPage,
                currentPage = currentPage,
                orderViewModel = orderViewModel,
                orderList = orderList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType,
                currentAccount = currentAccount,
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderViewModel.operationStatus,
        onSuccess = {
            if (orderViewModel.updatedOrder.value != null) {
                alertType.value = AlertType.UpdateOrderSuccess
                showErrorDialog.value = true
            }
        }
    )
}

fun fetchByAccountRoleAndTab(
    scope: CoroutineScope,
    selectedTabIndex: MutableState<Int>,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    currentAccount: Account?,
) {
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
        }

        else -> {
            scope.launch {
                handlerGetAllOrdersByStatus(
                    totalPage = totalPage,
                    currentPage = currentPage,
                    orderViewModel = orderViewModel,
                    orderList = orderList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType,
                    selectedTabIndex = selectedTabIndex,
                    currentAccount = currentAccount
                )
            }
        }
    }
}


