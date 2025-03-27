package org.example.project.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.*
import org.example.project.data.repository.*
import org.example.project.domain.model.*
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.card.VoucherItem
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.table.OrderTable
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.presentation.viewmodel.*
import org.example.project.pushWithLimitScreen

class CreateOrder(
    private val _selectedProduct: MutableList<Cart>,
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope{ Dispatchers.Default}
        val showLoadingOverlay = mutableStateOf(true)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val showErrorDialog = remember { mutableStateOf(false) }
        val cartViewModel = CartViewModel(CartRepository(CartApi()))
        val alertType = mutableStateOf(AlertType.Default)
        val currentAccount = mutableStateOf(SessionData.getCurrentAccount() ?: Account())
        val currentUser = mutableStateOf(SessionData.getCurrentUser() ?: User())
        val currentAdmin = mutableStateOf(SessionData.getCurrentAdmin() ?: Administrator())
        val userViewModel = UserViewModel(UserRepository(UserApi()))
        val addressViewModel = UserAddressViewModel(UserAddressRepository(UserAddressApi()))
        val paymentMethodViewModel = PaymentMethodViewModel(PaymentMethodRepository(PaymentMethodApi()))
        val paymentMethodList = mutableStateOf(listOf<PaymentMethod>())
        val paymentMethod = mutableStateOf(PaymentMethod())
        val transactionViewModel=TransactionViewModel(TransactionRepository(TransactionApi()))
        val transaction= mutableStateOf(Transaction())
        val order = mutableStateOf(Order())
        val userAddressList = mutableStateOf(emptyList<UserAddress>())
        val selectedProduct = mutableStateListOf<Cart>().apply {
            addAll(_selectedProduct)
        }
        val address =  remember {mutableStateOf(UserAddress())}
        val addressText =
            mutableStateOf(
                listOf(
                    address.value.street,
                    address.value.ward,
                    address.value.district,
                    address.value.province
                )
                    .filterNotNull()
                    .joinToString(separator = ", ")
            )

        scope.launch {
            _selectedProduct.forEachIndexed { index, cart ->
                val _cart: MutableState<Cart?> = mutableStateOf(cart)
                cart.id?.let {
                    handlerGetCartById(
                        cartViewModel = cartViewModel,
                        cartId = it,
                        cart = _cart,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        alertType = alertType
                    )
                }
                if (_cart.value != null) {
                    selectedProduct[index] = _cart.value!!
                } else {
                    selectedProduct.removeAt(index)
                }
            }
            handlerGetAllPaymentMethod(
                paymentMethodViewModel = paymentMethodViewModel,
                paymentMethodList = paymentMethodList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }

        AlertDialog(
            showDialog = showErrorDialog,
            alertType = alertType,
            rootMaxWidth = rootMaxWidth
        )

        ColumnBackground(
            showLoadingOverlay = showLoadingOverlay,
            rootMaxWidth = rootMaxWidth,
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
            ) {
                AddressPanel(
                    rootMaxWidth = rootMaxWidth,
                    scope = scope,
                    addressViewModel = addressViewModel,
                    userViewModel = userViewModel,
                    currentUser = currentUser,
                    userAddressList = userAddressList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType,
                    navigator = navigator,
                    address = address,
                    addressText = addressText
                )
                ProductPanel(
                    selectedProduct = selectedProduct
                )
                VoucherPanel(
                    rootMaxWidth = rootMaxWidth
                )
                PaymentPanel(
                    paymentMethodList = paymentMethodList,
                    paymentMethod = paymentMethod
                )
                TotalPanel(
                    selectedProduct = selectedProduct,
                    scope = scope,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType,
                    addressText = addressText,
                    address = address,
                    cartViewModel = cartViewModel,
                    _selectedProduct = _selectedProduct,
                    transactionViewModel = transactionViewModel,
                    transaction = transaction,
                    order = order,
                    paymentMethod = paymentMethod
                )
            }
        }
    }
}

@Composable
fun ProductPanel(
    selectedProduct: SnapshotStateList<Cart>,
) {
    OrderTable(
        selectedProduct = selectedProduct
    )
}

@Composable
fun TransportPanel() {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row {
            Image(
                imageVector = Icons.Outlined.LocalShipping,
                contentDescription = null
            )
            Spacer(Modifier.width(Size.Space.S200))
            Text(
                text = "Transport",
                style = Typography.Style.BodyStrong
            )
            Spacer(Modifier.weight(1f))
            CustomButton(
                text = "Change Transport",
                color = Themes.Light.neutralButton,
                onClick = {}
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VoucherPanel(
    rootMaxWidth: MutableState<Int>
) {
    Form(
        modifier = Modifier.fillMaxWidth(),
    ) {
        var showDialog = remember { mutableStateOf(value = false) }

        AlertDialog(
            title = "Select a Voucher",
            message = null,
            showDialog = showDialog,
            rootMaxWidth = rootMaxWidth,
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S400)
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Size.Space.S400),
                    ) {
                        InputField(
                            label = "Voucher Code",
                            placeHolder = "Voucher Code",
                            modifier = Modifier.weight(4f),
                            value = "",
                            onValueChange = {}
                        )
                        CustomButton(
                            modifier = Modifier.weight(1f).align(Alignment.Bottom),
                            text = "Apply",
                            onClick = {}
                        )
                    }
                    VoucherItem(onClick = {})
                }
            }
        )

        Row {
            Image(
                imageVector = Icons.Outlined.ConfirmationNumber,
                contentDescription = null
            )
            Spacer(Modifier.width(Size.Space.S200))
            Text(
                text = "Voucher",
                style = Typography.Style.BodyStrong
            )
            Spacer(Modifier.weight(1f))
            CustomButton(
                text = "Change",
                color = Themes.Light.neutralButton,
                onClick = {
                    showDialog.value = true
                }
            )
        }
    }
}

@Composable
fun PaymentPanel(
    paymentMethodList: MutableState<List<PaymentMethod>>,
    paymentMethod: MutableState<PaymentMethod>
) {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        val paymentViewModel = PaymentMethodViewModel(PaymentMethodRepository(PaymentMethodApi()))
        //paymentViewModel.getAllPaymentMethods(0)
        Row {
            Image(
                imageVector = Icons.Outlined.ConfirmationNumber,
                contentDescription = null
            )
            Spacer(Modifier.width(Size.Space.S200))
            Text(
                text = "Payment",
                style = Typography.Style.BodyStrong
            )
            Spacer(Modifier.weight(1f))
            ExposedDropdownInputField(
                modifier = Modifier.width(200.dp),
                placeholder = "Method",
                textFieldValue = mutableStateOf(paymentMethod.value.name?:""),
                options = paymentMethodList.value.map {
                    it.name?:""
                },
                onValueChange = { change ->
                    paymentMethod.value = paymentMethodList.value.find { it.name == change }!!
                },
                filterOption = false,
            )
            /*CustomButton(
                text = "Change Payment",
                color = Themes.Light.neutralButton,
                onClick = {

                }
            )*/
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddressPanel(
    scope: CoroutineScope,
    rootMaxWidth: MutableState<Int>,
    addressViewModel: UserAddressViewModel,
    userViewModel: UserViewModel,
    currentUser: MutableState<User>,
    userAddressList: MutableState<List<UserAddress>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    navigator: Navigator?,
    address: MutableState<UserAddress>,
    addressText: MutableState<String>
) {
    var showDialog = remember { mutableStateOf(value = false) }

    scope.launch {
        handlerGetUserAddressByUserId(
            addressViewModel = addressViewModel,
            userViewModel = userViewModel,
            currentUser = currentUser,
            userAddressList = userAddressList,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType
        )
    }

    AlertDialog(
        title = "Select address",
        message = null,
        showDialog = showDialog,
        rootMaxWidth = rootMaxWidth,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400)
            ) {
                userAddressList.value.forEach {
                    AddressItem(
                        modifier = Modifier.clickable {
                            address.value = it
                        },
                        rootMaxWidth = rootMaxWidth,
                        address = it,
                        showButton = false
                    )
                }
            }
        }
    )

    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            FlowRow(
                overflow = FlowRowOverflow.Visible
            ) {
                Image(
                    imageVector = Icons.Outlined.Payment,
                    contentDescription = null
                )
                Spacer(Modifier.width(Size.Space.S200))
                Text(
                    text = "Shipping Address",
                    style = Typography.Style.Heading6
                )
            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                //verticalAlignment = Alignment.CenterVertically
                verticalArrangement = Arrangement.Center,
                overflow = FlowRowOverflow.Visible
            ) {
                if(SessionData.getCurrentUser()?.fullName == null || SessionData.getCurrentAccount()?.phoneNumber == null){
                    alertType.value = AlertType.MissingPersonalInfo
                    showErrorDialog.value = true
                    pushWithLimitScreen(navigator, AccountView())
                }
                FlowRow(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
                ) {
                    Text(
                        text = SessionData.getCurrentUser()?.fullName ?: "",
                        style = Typography.Style.BodyStrong
                    )
                    Text(
                        text = SessionData.getCurrentAccount()?.phoneNumber ?: "",
                        style = Typography.Style.BodyStrong
                    )
                    Spacer(Modifier.width(Size.Space.S600))
                    Text(
                        text = addressText.value,
                        style = Typography.Style.BodyText
                    )
                }
                Spacer(Modifier.weight(1f))
                FlowRow(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
                ) {
                    /*CustomButton(text = "Default",
                        color = Themes.Light.secondaryBrandTag,
                        onClick = {}
                    )*/
                    CustomButton(
                        text = "Choose",
                        icon = Icons.Outlined.Edit,
                        onClick = {
                            showDialog.value = true
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun TotalPanel(
    selectedProduct: SnapshotStateList<Cart>,
    scope: CoroutineScope,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    addressText: MutableState<String>,
    address: MutableState<UserAddress>,
    cartViewModel: CartViewModel,
    _selectedProduct: MutableList<Cart>,
    transactionViewModel: TransactionViewModel,
    transaction: MutableState<Transaction>,
    order: MutableState<Order>,
    paymentMethod: MutableState<PaymentMethod>
    ) {
    val subTotal = remember { mutableStateOf(
        selectedProduct.sumOf {
            it.quantity!!.times(
                it.product?.price?.doubleValue()?:0.0) }.toString()
    ) }

    Form(modifier = Modifier.fillMaxWidth()) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Form(
                padding = 0.dp,
                color = Themes.Light.navigationPill,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BodyText(text = "Sub total")
                    BodyText(text = subTotal.value)
                }
                /*Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BodyText(text = "Shipping")
                    BodyText(text = "100")
                }*/
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BodyText(text = "Total")
                    BodyText(text = subTotal.value.toString())
                }
                CustomButton(
                    text = "Place order",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        when {
                            addressText.value.isNullOrEmpty() -> {
                                alertType.value = AlertType.AddressNull
                                showErrorDialog.value = true
                            }
                            paymentMethod.value.name.isNullOrEmpty() -> {
                                alertType.value = AlertType.PaymentNull
                                showErrorDialog.value = true
                            }
                            else -> {
                                scope.launch {
                                    _selectedProduct.forEachIndexed { index, cart ->
                                        val _cart: MutableState<Cart?> = mutableStateOf(cart)
                                        cart.id?.let {
                                            handlerGetCartById(
                                                cartViewModel = cartViewModel,
                                                cartId = it,
                                                cart = _cart,
                                                showLoadingOverlay = showLoadingOverlay,
                                                showErrorDialog = showErrorDialog,
                                                alertType = alertType
                                            )
                                        }
                                        if (_cart.value != null) {
                                            selectedProduct[index] = _cart.value!!
                                        } else {
                                            selectedProduct.removeAt(index)
                                        }
                                    }
                                    order.value = Order(
                                        user = SessionData.getCurrentUser(),
                                        userAddress = address.value,
                                        address = addressText.value,
                                        createdAt = Clock.System.now()
                                    )

                                    createOrder(
                                        order = order,
                                        itemList = selectedProduct,
                                        showLoadingOverlay = showLoadingOverlay,
                                        showErrorDialog = showErrorDialog,
                                        alertType = alertType,
                                        transactionViewModel = transactionViewModel,
                                        transaction = transaction,
                                        paymentMethod = paymentMethod
                                    )

                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

suspend fun createOrder(
    order: MutableState<Order>,
    itemList: List<Cart>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    transactionViewModel: TransactionViewModel,
    transaction: MutableState<Transaction>,
    paymentMethod: MutableState<PaymentMethod>
) {
    val orderViewModel = OrderViewModel(OrderRepository(OrderApi()))
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            itemList.map { it.id!! }.let {
                orderViewModel.createOrder(
                    order.value,
                    it
                )
            }
            if (orderViewModel.createdOrder.value?.id!=null) {
                order.value = orderViewModel.createdOrder.value!!
                transaction.value = Transaction(
                    order = order.value,
                    paymentMethod = paymentMethod.value,
                    paymentMethodName = paymentMethod.value.name,
                    transactionStatus = TransactionStatus(id = 1),
                    transactionTime = Clock.System.now()
                )
                println(
                    order.value
                )
                handlerCreateTransaction(
                    transactionViewModel = transactionViewModel,
                    transaction = transaction,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = orderViewModel.operationStatus,
        onSuccess = {
            alertType.value = AlertType.PlaceOrderSuccessful
            showErrorDialog.value = true
        }
    )
}

suspend fun handlerGetAllPaymentMethod(
    paymentMethodViewModel: PaymentMethodViewModel,
    paymentMethodList: MutableState<List<PaymentMethod>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            paymentMethodViewModel.getAllPaymentMethods(0)
            paymentMethodList.value = paymentMethodViewModel.paymentMethodsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = paymentMethodViewModel.operationStatus,
    )
}

suspend fun handlerCreateTransaction(
    transactionViewModel: TransactionViewModel,
    transaction: MutableState<Transaction>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            transactionViewModel.createTransaction(
                transaction = transaction.value
            )
            transaction.value = transactionViewModel.createdTransaction.value!!
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = transactionViewModel.operationStatus,
        onSuccess = {
            alertType.value = AlertType.PlaceOrderSuccessful
            showErrorDialog.value = true
        }
    )
}
