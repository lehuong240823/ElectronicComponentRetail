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
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import org.example.project.domain.model.Cart
import org.example.project.domain.model.Product
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Checkbox
import org.example.project.presentation.components.input.QuantityGroup
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography

@Composable
fun CartTable(
    headers: List<String> = listOf("Product", "Unit Price", "Quantity", "Amount", "Actions"),
    weights: List<Float> = listOf(4f, 1f, 2f, 1f, 1f),
    textAligns: List<TextAlign> = listOf(TextAlign.Left, TextAlign.Right, TextAlign.Center, TextAlign.Right, TextAlign.Center),
    cartList: MutableState<List<Cart>> = mutableStateOf(listOf(Cart())),
    showSelectAllCheckBox: Boolean = true,
    selectAllChecked: MutableState<Boolean> = remember { mutableStateOf(false) },
    onselectAllCheckedChange: (Boolean) -> Unit = {},
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
            CartRow(
                weights = weights,
                Cart(
                    product = Product(name = "Product",
                        price = 1234.toBigDecimal())
                )
            )
        }
    )
}

@Composable
fun CartRow(
    weights: List<Float>,
    cart: Cart
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = remember { mutableStateOf(false) },
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
                text = cart.product?.name?: "",
                style = Typography.Style.BodyText,
                //textAlign = if (!textAligns.isEmpty() && textAligns[idx] != null) textAligns[idx] else TextAlign.Left
            )
        }

        BodyText(
            modifier = Modifier.weight(weights[1]),
            text = cart.product?.price?.doubleValue().toString(),
            style = Typography.Style.BodyText,
            textAlign = TextAlign.Right
        )

        Box( Modifier.weight(weights[2]))
        {
            QuantityGroup(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        BodyText(
            modifier = Modifier.weight(weights[3]),
            text = cart.product?.price?.doubleValue().toString(),
            style = Typography.Style.BodyText,
            textAlign = TextAlign.Right
        )

        IconButton(
            modifier = Modifier
                .weight(weights[4]),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.DeleteOutline,
                contentDescription = null
            )
        }
    }
}