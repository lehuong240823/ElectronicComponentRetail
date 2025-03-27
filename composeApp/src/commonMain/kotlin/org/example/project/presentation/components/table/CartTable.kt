package org.example.project.presentation.components.table

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.core.enums.AlertType
import org.example.project.domain.model.Cart
import org.example.project.domain.model.User
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Checkbox
import org.example.project.presentation.components.input.QuantityGroup
import org.example.project.presentation.screens.handlerAddToCart
import org.example.project.presentation.screens.handlerDeleteCart
import org.example.project.presentation.screens.handlerGetAllCartsByUserId
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography
import org.example.project.presentation.viewmodel.CartViewModel

@Composable
fun CartTable(
    headers: List<String> = listOf("Product", "Unit Price", "Quantity", "Total", "Actions"),
    weights: List<Float> = listOf(4f, 1f, 2f, 1f, 1f),
    textAligns: List<TextAlign> = listOf(TextAlign.Left, TextAlign.Right, TextAlign.Center, TextAlign.Right, TextAlign.Center),
    cartList: MutableState<List<Cart>>,
    showSelectAllCheckBox: Boolean = false,
    selectAllChecked: MutableState<Boolean> = remember { mutableStateOf(false) },
    onselectAllCheckedChange: (Boolean) -> Unit = {},
    cartViewModel: CartViewModel,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    user: MutableState<User>,
    selectedProduct: MutableList<Cart>,
) {
    Table(
        headers = headers,
        weights = weights,
        tableHeader = {
            TableHeader(
                headers = headers,
                weights = weights,
                textAligns = textAligns,
                showSelectAllCheckBox = showSelectAllCheckBox,
                selectAllChecked = selectAllChecked,
                onselectAllCheckedChange = onselectAllCheckedChange
            )
        },
        tableRowsContent = {
            cartList.value.forEach { cart ->
                val _cart = mutableStateOf(cart)
                CartRow(
                    weights = weights,
                    cart = _cart,
                    onDelete = {
                        scope.launch {
                            handlerDeleteCart(
                                cartViewModel = cartViewModel,
                                cart = _cart,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType
                            )
                            handlerGetAllCartsByUserId(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                cartViewModel = cartViewModel,
                                cartList = cartList,
                                user = user,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType
                            )
                        }
                    },
                    onCartChange = { change ->
                        scope.launch {
                            if (change == cart.quantity!!) {
                                handlerDeleteCart(
                                    cartViewModel = cartViewModel,
                                    cart = _cart,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType
                                )
                            } else {
                                _cart.value = _cart.value.copy(
                                    quantity = (change - cart.quantity)
                                )
                                handlerAddToCart(
                                    cartViewModel = cartViewModel,
                                    cart = _cart,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType
                                )
                            }
                            handlerGetAllCartsByUserId(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                cartViewModel = cartViewModel,
                                cartList = cartList,
                                user = user,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType
                            )
                        }
                    },
                    selectedProduct = selectedProduct
                )
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CartRow(
    weights: List<Float>,
    cart: MutableState<Cart>,
    onDelete: () -> Unit,
    onCartChange: (Int) -> Unit,
    selectedProduct: MutableList<Cart>,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = remember { mutableStateOf(false) },
            onCheckedChange = {
                if (it) {
                    if(selectedProduct.size<20) selectedProduct.add(cart.value)
                } else {
                    selectedProduct.remove(cart.value)
                }
            }
        )
        Row(
            modifier = Modifier
                .weight(weights[0]),
            horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Outlined.Image,
                contentDescription = null,
            )
            BodyText(
                text = cart.value.product?.name ?: "",
                style = Typography.Style.BodyText,
            )
        }

        BodyText(
            modifier = Modifier.weight(weights[1]),
            text = cart.value.product?.price?.doubleValue().toString(),
            style = Typography.Style.BodyText,
            textAlign = TextAlign.Right
        )

        Box( Modifier.weight(weights[2]))
        {
            QuantityGroup(
                modifier = Modifier.align(Alignment.Center),
                quantity = mutableStateOf(cart.value.quantity ?: 1),
                onValueChange = onCartChange
            )
        }

        BodyText(
            modifier = Modifier.weight(weights[3]),
            text = cart.value.quantity?.let { cart.value.product?.price?.doubleValue()?.times(it) }.toString(),
            style = Typography.Style.BodyText,
            textAlign = TextAlign.Right
        )

        IconButton(
            modifier = Modifier
                .weight(weights[4]),
            onClick = onDelete
        ) {
            Icon(
                imageVector = Icons.Outlined.DeleteOutline,
                contentDescription = null
            )
        }
    }
}