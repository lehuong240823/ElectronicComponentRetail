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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.CartApi
import org.example.project.data.repository.CartRepository
import org.example.project.domain.model.Account
import org.example.project.domain.model.Administrator
import org.example.project.domain.model.Cart
import org.example.project.domain.model.User
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.table.CartTable
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.CartViewModel
import org.example.project.pushWithLimitScreen

class CartView: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(true)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val showErrorDialog = remember { mutableStateOf(false) }
        val alertType = mutableStateOf(AlertType.Default)
        val currentAccount = mutableStateOf(SessionData.getCurrentAccount() ?: Account())
        val currentUser = mutableStateOf(SessionData.getCurrentUser() ?: User())
        val currentAdmin = mutableStateOf(SessionData.getCurrentAdmin() ?: Administrator())
        val selectAllChecked = remember { mutableStateOf(false) }
        val cartList = mutableStateOf(listOf<Cart>())
        val cartViewModel = CartViewModel(CartRepository(CartApi()))
        val selectedProduct = mutableListOf<Cart>()
        scope.launch {
            handlerGetAllCartsByUserId(
                totalPage = totalPage,
                currentPage = currentPage,
                cartViewModel = cartViewModel,
                cartList = cartList,
                user = currentUser,
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
            showBuyButton = true,
            onBuyButtonClick = {
                if (selectedProduct.size > 0) {
                    pushWithLimitScreen(navigator, CreateOrder(selectedProduct))
                } else {
                    alertType.value = AlertType.CartSelectedNull
                    showErrorDialog.value = true
                }
            }
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
                    },
                    cartList = cartList,
                    cartViewModel = cartViewModel,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType,
                    scope = scope,
                    totalPage = totalPage,
                    currentPage = currentPage,
                    user = currentUser,
                    selectedProduct = selectedProduct,
                )
                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {
                            scope.launch {
                                handlerGetAllCartsByUserId(
                                    totalPage = totalPage,
                                    currentPage = currentPage,
                                    cartViewModel = cartViewModel,
                                    cartList = cartList,
                                    user = currentUser,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

suspend fun handlerGetCartById(
    cartViewModel: CartViewModel,
    cartId: Int,
    cart: MutableState<Cart?>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            cartViewModel.getCart(cartId)
            cart.value = cartViewModel.cart.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = cartViewModel.operationStatus,
    )
}

suspend fun handlerGetAllCartsByUserId(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    cartViewModel: CartViewModel,
    cartList: MutableState<List<Cart>>,
    user: MutableState<User>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            cartViewModel.getCartsByUserId(currentPage.value, user.value.id ?: 0)
            totalPage.value = cartViewModel.totalPage.value ?: 0
            cartList.value = cartViewModel.cartsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = cartViewModel.operationStatus,
    )
}

suspend fun handlerAddToCart(
    cartViewModel: CartViewModel,
    cart: MutableState<Cart>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            cartViewModel.createCart(cart.value)
            cartViewModel.createdCart.value?.let {
                cart.value = it
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = cartViewModel.operationStatus,
    )
}

suspend fun handlerDeleteCart(
    cartViewModel: CartViewModel,
    cart: MutableState<Cart>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            cart.value.id?.let {
                cartViewModel.deleteCart(it)
            }
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = cartViewModel.operationStatus,
    )
}