package org.example.project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Order
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Composable
fun UserOrderItem(
    order: Order? = null,
    buttonGroup: @Composable () -> Unit,
    color: ButtonColor = Themes.Light.primaryLayout
) {
    Column(
        modifier = Modifier.background(color = color.defaultBackground!!, shape = RectangleShape)
            .padding(Size.Space.S400),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            BodyText(
                modifier = Modifier
                    //.border(Size.Stroke.Border, color.primaryText!!, CustomRoundedCorner())
                .padding(Size.Space.S200),
                text = "Delivered",
                textStyle = Typography.Style.BodyText.copy(fontWeight = FontWeight.Bold)
            )
        }
        Divider()
        UserOrderProduct()
        UserOrderProduct()
        Divider()
        buttonGroup()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserOrderProduct() {
    FlowRow {
        Image(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Outlined.Image,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
        ) {
            BodyText(text = "Product name")
            BodyText(
                text = "x2",
                textStyle = Typography.Style.BodyText.copy(color = Themes.Light.primaryLayout.secondaryText!!),
            )
        }
        Spacer(Modifier.weight(1f))
        BodyText(
            modifier = Modifier.fillMaxRowHeight(),
            text = "153.246"
        )
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
        TextButton(
            text = "Pay Now",
            onClick = {

            }
        )
        TextButton(
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
        TextButton(
            text = "Contact Support",
            onClick = {

            }
        )
    }
}

@Composable
fun shippedButtonGroup() {
    orderItemButtonGroup {
        TextButton(
            text = "Track Order",
            onClick = {

            }
        )
        TextButton(
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
        TextButton(
            text = "Leave a Review",
            onClick = {

            }
        )
        TextButton(
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
        TextButton(
            text = "Reorder",
            onClick = {

            }
        )
    }
}

@Composable
fun failedRefundedButtonGroup() {
    orderItemButtonGroup {
        TextButton(
            text = "Retry Payment",
            onClick = {

            }
        )
    }
}

@Composable
fun returnedRefundedButtonGroup() {
    orderItemButtonGroup {
        TextButton(
            text = "Check Refund Status",
            onClick = {

            }
        )
    }
}