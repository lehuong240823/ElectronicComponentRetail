package org.example.project.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Order
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

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
                style = Typography.Style.BodyText.copy(fontWeight = FontWeight.Bold)
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
                style = Typography.Style.BodyText.copy(color = Themes.Light.primaryLayout.secondaryText!!),
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