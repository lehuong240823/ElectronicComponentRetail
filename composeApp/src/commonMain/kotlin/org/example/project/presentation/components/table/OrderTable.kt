package org.example.project.presentation.components.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Cart
import org.example.project.domain.model.Order
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography

@Composable
@Preview
fun OrderTable(
    headers: List<String> = listOf("Product", "Quantity", "Price", "Total"),
    weights: List<Float> = listOf(5f, 1f, 1f, 1f),
    textAligns: List<TextAlign> = listOf(TextAlign.Left, TextAlign.Right, TextAlign.Right, TextAlign.Right),
    order: Order? = null,
    selectedProduct: SnapshotStateList<Cart>,
) {

    Table(
        headers = headers,
        weights = weights,
        textAligns = textAligns,
        tableRowsContent = {
            selectedProduct.forEach { cart ->
                OrderRow(
                    weights = weights,
                    textAligns = textAligns,
                    cart = mutableStateOf(cart)
                )
            }
        }
    )
}

@Composable
fun OrderRow(
    weights: List<Float>,
    textAligns: List<TextAlign>,
    cart: MutableState<Cart>,
    ) {
    var row = listOf(cart.value.product?.name,
        cart.value.quantity,
        cart.value.product?.price!!.doubleValue(),
        cart.value.product?.price!!.doubleValue().times(cart.value.quantity!!))

    Row(modifier = Modifier.fillMaxWidth()) {
        row.forEachIndexed { idx, cell ->
            if (idx == 0) {
                Row(
                    modifier = Modifier
                        .weight(if (!weights.isEmpty()) weights[idx] else 1f),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Outlined.Image,
                        contentDescription = null,
                    )
                    BodyText(
                        text = cell.toString(),
                        style = Typography.Style.BodyText,
                        textAlign = if (!textAligns.isEmpty() && textAligns[idx] != null) textAligns[idx] else TextAlign.Left
                    )
                }
            } else {
                BodyText(
                    text = cell.toString(),
                    modifier = Modifier
                        .weight(if (!weights.isNullOrEmpty()) weights[idx] else 1f),
                    style = Typography.Style.BodyText,
                    textAlign = if (!textAligns.isNullOrEmpty()) textAligns[idx] else TextAlign.Left
                )
            }
        }
    }
}