package org.example.project.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.table.OrderTable
import org.example.project.presentation.components.card.VoucherItem
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

class CreateOrder : Screen {
    @Composable
    override fun Content() {
        MaterialTheme {
            ColumnBackground {
                //item {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                        verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                    ) {
                        AddressPanel()
                        ProductPanel()
                        TransportPanel()
                        VoucherPanel()
                        PaymentPanel()
                        TotalPanel()
                    }
                //}
            }
        }
    }
}

@Composable
fun ProductPanel() {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        OrderTable()
    }
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
fun VoucherPanel() {
    Form(
        modifier = Modifier.fillMaxWidth(),
    ) {
        var showDialog by remember { mutableStateOf(value = false) }

        AlertDialog(
            title = "Select a Voucher",
            showDialog = showDialog,
            usePlatformDefaultWidth = false,
            maxWidth = 640.dp,
            onDismissRequest = { showDialog = false },
            onConfirmation = { showDialog = false },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S400)
                ) {
                    Row(
                        modifier = Modifier.wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Size.Space.S400),
                    ) {
                        BodyText(
                            modifier = Modifier.weight(2f),
                            text = "Voucher Code"
                        )
                        InputField(
                            modifier = Modifier.weight(5f),
                            value = "",
                            onValueChange = {}
                        )
                        CustomButton(
                            modifier = Modifier.weight(1f),
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
                    showDialog = true
                }
            )
        }
    }
}

@Composable
fun PaymentPanel() {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
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
            CustomButton(
                text = "Change Payment",
                color = Themes.Light.neutralButton,
                onClick = {}
            )
        }
    }
}

@Composable
fun AddressPanel() {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
                ) {
                    Text(
                        text = "Full name",
                        style = Typography.Style.BodyStrong
                    )
                    Text(
                        text = "123456789",
                        style = Typography.Style.BodyStrong
                    )
                    Spacer(Modifier.width(Size.Space.S600))
                    Text(
                        text = "123 Phường Phúc La, Quận Hà Đông, Hà Nội",
                        style = Typography.Style.BodyText
                    )
                }
                Spacer(Modifier.weight(1f))
                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
                ) {
                    CustomButton(text = "Default",
                        color = Themes.Light.secondaryBrandTag,
                        onClick = {}
                    )
                    CustomButton(
                        text = "Change",
                        icon = Icons.Outlined.Edit,
                        onClick = {}
                    )
                }

            }
        }
    }
}

@Composable
fun TotalPanel() {
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
                    BodyText(text = "1000")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BodyText(text = "Shipping")
                    BodyText(text = "100")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    BodyText(text = "Total")
                    BodyText(text = "1000")
                }
                CustomButton(
                    text = "Place order",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {}
                )
            }
        }
    }
}