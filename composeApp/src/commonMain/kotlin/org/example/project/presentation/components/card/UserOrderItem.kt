package org.example.project.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import org.example.project.*
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AlertType
import org.example.project.core.enums.OrderStatusType
import org.example.project.domain.model.Account
import org.example.project.domain.model.Order
import org.example.project.domain.model.OrderItem
import org.example.project.domain.model.OrderStatus
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.screens.ProductDetail
import org.example.project.presentation.screens.handlerEditOrder
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.presentation.viewmodel.OrderItemViewModel
import org.example.project.presentation.viewmodel.OrderViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserOrderItem(
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    order: MutableState<Order>,
    color: ButtonColor = Themes.Light.primaryLayout,
    orderStatusList: MutableState<List<OrderStatus>>,
    orderItemList: MutableState<List<OrderItem>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    rootMaxWidth: MutableState<Int>,
    selectedTabIndex: MutableState<Int>,
) {
    val currentAccount = SessionData.getCurrentAccount()

    Column(
        modifier = Modifier.background(color = color.defaultBackground!!, shape = RectangleShape)
            .padding(Size.Space.S400),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
    ) {
        OrderStatusOnAccountRoleChange(
            currentAccount = currentAccount,
            order = order,
            orderStatusList = orderStatusList,
            rootMaxWidth = rootMaxWidth,
            scope = scope,
            totalPage = totalPage,
            currentPage = currentPage,
            orderViewModel = orderViewModel,
            orderList = orderList,
            showLoadingOverlay = showLoadingOverlay,
            alertType = alertType,
            showErrorDialog = showErrorDialog,
            selectedTabIndex = selectedTabIndex
        )
        Divider()
        Column(
            modifier = Modifier.padding(Size.Space.S300),
            verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
        ) {
            FlowRow {
                BodyText(
                    text = "Create time: ",
                    color = Themes.Light.primaryLayout.copy(primaryText = Themes.Light.primaryLayout.secondaryText)
                )
                order.value.createdAt?.format(
                    DateTimeComponents.Format {
                        date(LocalDate.Formats.ISO)
                        char(' ')
                        time(LocalTime.Format {
                            hour()
                            char(':')
                            minute(); char(':')
                            second()
                        })
                    }
                )?.let {
                    BodyText(
                        text = it,
                    )
                }
            }
            FlowRow {
                BodyText(
                    text = "Customer name: ",
                    color = Themes.Light.primaryLayout.copy(primaryText = Themes.Light.primaryLayout.secondaryText)
                )
                BodyText(
                    text = order.value.user?.fullName ?: "",
                )
            }
            FlowRow {
                BodyText(
                    text = "Address: ",
                    color = Themes.Light.primaryLayout.copy(primaryText = Themes.Light.primaryLayout.secondaryText)
                )
                BodyText(
                    text = order.value.address ?: "",
                )
            }
            FlowRow {
                BodyText(
                    text = "Voucher: ",
                    color = Themes.Light.primaryLayout.copy(primaryText = Themes.Light.primaryLayout.secondaryText)
                )
                BodyText(
                    text = order.value.voucher?.code ?: "None",
                )
            }
        }
        Divider()
        orderItemList.value.forEach { item ->
            UserOrderProduct(
                item = item,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }

        Divider()
        Row(
            modifier = Modifier.align(Alignment.End).padding(vertical = Size.Space.S300),
        ) {
            BodyText(
                text = "Total: ",
            )
            BodyText(
                text = CURRENCY.plus(order.value.amount?.toPlainString()),
                style = Typography.Style.BodyStrong
            )
        }


        when (currentAccount?.accountRole?.name) {
            AccountRoleType.User.name -> {
                when (order.value.orderStatus?.name) {
                    OrderStatusType.Pending.name -> {
                        pendingButtonGroup()
                    }

                    OrderStatusType.Processing.name -> {
                        processingButtonGroup()
                    }

                    OrderStatusType.Shipped.name -> {
                        shippedButtonGroup()
                    }

                    OrderStatusType.Delivered.name -> {
                        deliveredButtonGroup()
                    }

                    OrderStatusType.Cancelled.name -> {
                        cancelledRefundedButtonGroup()
                    }

                    OrderStatusType.Failed.name -> {
                        failedRefundedButtonGroup()
                    }

                    OrderStatusType.Returned.name -> {
                        cancelledRefundedButtonGroup()
                    }

                    OrderStatusType.Refunded.name -> {
                        failedRefundedButtonGroup()
                    }
                }
            }

            AccountRoleType.Administrator.name -> {
                /*CustomButton(
                    modifier = Modifier.align(Alignment.End),
                    text = "Detail",
                    onClick = {

                    }
                )*/
            }
        }
    }
}

@Composable
fun OrderStatusOnAccountRoleChange(
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderViewModel: OrderViewModel,
    orderList: MutableState<List<Order>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    selectedTabIndex: MutableState<Int>,
    currentAccount: Account?,
    order: MutableState<Order>,
    orderStatusList: MutableState<List<OrderStatus>>,
    rootMaxWidth: MutableState<Int>,
    ) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = Size.Space.S200, alignment = Alignment.End),
    ) {
        when(currentAccount?.accountRole?.name) {
            AccountRoleType.Administrator.name -> {
                orderStatusList.value
                    .sortedBy { it.id }
                    .map { it.name ?: "" }
                    .filter { !NOT_INCLUDE_ORDER_STATUS_FOR_ADMIN.contains(it) }
                    .let { it ->
                        ExposedDropdownInputField(
                            modifier = Modifier.widthIn(max = 200.dp),
                            textFieldValue = mutableStateOf(order.value.orderStatus?.name ?: ""),
                            filterOption = false,
                            options = it,
                            enabled = false,
                            onValueChange = { change ->
                                order.value = order.value
                                    .copy(orderStatus = orderStatusList.value.find { status -> status.name == change }
                                    )
                            }
                        )
                    }
                CustomButton(
                    text = "Update",
                    onClick = {
                        scope.launch {
                            handlerEditOrder(
                                scope = scope,
                                currentAccount = currentAccount,
                                totalPage = totalPage,
                                currentPage = currentPage,
                                orderViewModel = orderViewModel,
                                orderList = orderList,
                                showLoadingOverlay = showLoadingOverlay,
                                alertType = alertType,
                                showErrorDialog = showErrorDialog,
                                order = order,
                                selectedTabIndex = selectedTabIndex,
                                orderStatusList = orderStatusList
                            )
                        }
                    }
                )

            }
            AccountRoleType.User.toString() -> {
                BodyText(
                    modifier = Modifier
                        .padding(Size.Space.S200),
                    text = order.value.orderStatus?.name ?: "",
                    style = Typography.Style.BodyText.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserOrderProduct(
    item: OrderItem,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    val navigator = LocalNavigator.current

    FlowRow(
        modifier = Modifier.clickable {
            if (item.product != null) {
                pushWithLimitScreen(
                    navigator = navigator, ProductDetail(item.product)
                )
            } else {
                alertType.value = AlertType.ProductNotFound
            }
        },
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Outlined.Image,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
        ) {
            BodyText(text = item.productName ?: "")
            BodyText(
                text = "x".plus(item.quantity),
                style = Typography.Style.BodyText.copy(color = Themes.Light.primaryLayout.secondaryText!!),
            )
        }
        Spacer(Modifier.weight(1f))
        item.price?.toPlainString()?.let {
            BodyText(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = CURRENCY.plus(it)
            )
        }
    }
}

@Composable
fun orderItemButtonGroup(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = Size.Space.S200,
            alignment = Alignment.End
        ),
    ) {
        content()
    }
}

@Composable
fun pendingButtonGroup() {
    orderItemButtonGroup {
        CustomButton(
            text = "Pay Now",
            onClick = {

            }
        )
        CustomButton(
            text = "Cancel Order",
            color = Themes.Light.neutralButton,
            onClick = {

            }
        )
    }
}

@Composable
fun processingButtonGroup() {
    orderItemButtonGroup {
        CustomButton(
            text = "Contact Support",
            onClick = {

            }
        )
    }
}

@Composable
fun shippedButtonGroup() {
    orderItemButtonGroup {
        CustomButton(
            text = "Track Order",
            onClick = {

            }
        )
        CustomButton(
            text = "Contact Support",
            color = Themes.Light.neutralButton,
            onClick = {

            }
        )
    }
}

@Composable
fun deliveredButtonGroup() {
    orderItemButtonGroup {
        CustomButton(
            text = "Leave a Review",
            onClick = {

            }
        )
        CustomButton(
            text = "Return Order",
            color = Themes.Light.neutralButton,
            onClick = {

            }
        )
    }
}

@Composable
fun cancelledRefundedButtonGroup() {
    orderItemButtonGroup {
        CustomButton(
            text = "Reorder",
            onClick = {

            }
        )
    }
}

@Composable
fun failedRefundedButtonGroup() {
    orderItemButtonGroup {
        CustomButton(
            text = "Retry Payment",
            onClick = {

            }
        )
    }
}

@Composable
fun returnedRefundedButtonGroup() {
    orderItemButtonGroup {
        CustomButton(
            text = "Check Refund Status",
            onClick = {

            }
        )
    }
}

suspend fun handlerGetAllOrderItemsByStatusOrderId(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    orderItemViewModel: OrderItemViewModel,
    orderItemList: MutableState<List<OrderItem>>,
    order: MutableState<Order>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            orderItemViewModel.getOrderItemsByOrderId(currentPage.value, order.value.id?:0)
            totalPage.value = orderItemViewModel.totalPage.value ?: 0
            orderItemList.value = orderItemViewModel.orderItemsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderItemViewModel.operationStatus,
    )
}

suspend fun handlerGetAllOrderItemsByOrderId(
    currentPage: MutableState<Int> = mutableStateOf(0),
    orderItemViewModel: OrderItemViewModel,
    orderItemList: MutableState<List<OrderItem>>,
    order: Order?,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            orderItemViewModel.getOrderItemsByOrderId(
                currentPage = currentPage.value,
                order = order?.id ?: 0,
            )
            orderItemList.value = orderItemViewModel.orderItemsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderItemViewModel.operationStatus,
    )
}