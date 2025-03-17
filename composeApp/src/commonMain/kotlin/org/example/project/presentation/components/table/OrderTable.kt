package org.example.project.presentation.components.table

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import org.example.project.domain.model.Order
import org.example.project.domain.model.OrderItem
import org.example.project.domain.model.Product
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography

@Composable
@Preview
fun OrderTable(
    headers: List<String> = listOf("Product", "Quantity", "Price", "Total"),
    weights: List<Float> = listOf(5f, 1f, 1f, 1f),
    textAligns: List<TextAlign> = listOf(TextAlign.Left, TextAlign.Right, TextAlign.Right, TextAlign.Right),
    order: Order? = null
) {

    val orderItems = mutableStateOf(listOf(OrderItem(
        product = Product(name = "Product"),
        quantity = 2,
        price = 11111.toBigDecimal()
    )))

    Table(
        headers = headers,
        weights = weights,
        textAligns = textAligns,
        tableRowsContent = {
            orderItems.value.forEach { orderItem ->
                OrderRow(
                    weights = weights,
                    textAligns = textAligns,
                    orderItem = orderItem
                )
            }
        }
    )
}

@Composable
fun OrderRow(
    weights: List<Float>,
    textAligns: List<TextAlign>,
    orderItem: OrderItem,
    ) {
    var row = listOf(orderItem.product!!.name,
        orderItem.quantity,
        orderItem.price!!.doubleValue(),
        orderItem.price!!.doubleValue().times(orderItem.quantity!!))

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