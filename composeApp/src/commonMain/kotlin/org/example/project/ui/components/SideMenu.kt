package org.example.project.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_google
import org.example.project.pushWithLimitScreen
import org.example.project.ui.screens.CreateOrder
import org.example.project.ui.screens.Profile
import org.example.project.ui.screens.TransactionView
import org.example.project.ui.screens.UserViewOrder
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SideMenu(
    isExpanded: MutableState<Boolean>,
    color: ButtonColor = Themes.Light.primaryLayout
) {
    val navigator = LocalNavigator.current
    MaterialTheme {
        FlowColumn (
            modifier = Modifier
                .background(color = color.defaultBackground!!)
                .border(width = Size.Stroke.Border, color = color.border!!)
                .padding(Size.Space.S200)
                .wrapContentWidth()
                .widthIn(48.dp, 260.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
            horizontalArrangement = if(isExpanded.value) Arrangement.Start else Arrangement.Center
        ) {
            /*Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = Size.Space.S200),
                    //alignment = if(isExpanded) Alignment.Start else Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RoundIconButton(Res.drawable.ic_google)
                AnimatedVisibility(visible = isExpanded) {
                    BodyText(
                        modifier = Modifier.wrapContentSize(),
                        text = "Email"
                    )
                }
            }*/
            Divider(modifier = Modifier.fillMaxColumnWidth())
            Column(Modifier.wrapContentSize()) {
                MenuItem(
                    text = "Collapse",
                    icon = if (isExpanded.value) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                    textVisibility = isExpanded.value,
                    onClick = {
                        isExpanded.value = !isExpanded.value
                    }
                )
                MenuItem(
                    text = "Orders",
                    icon = Icons.Outlined.Inventory2,
                    textVisibility = isExpanded.value,
                    onClick = {
                        pushWithLimitScreen(navigator, UserViewOrder())
                    }
                )
                MenuItem(
                    text = "Transactions",
                    icon = Icons.Outlined.AttachMoney,
                    textVisibility = isExpanded.value,
                    onClick = {
                        pushWithLimitScreen(navigator, TransactionView())
                    }
                )
                MenuItem(
                    text = "Vouchers",
                    icon = Icons.Outlined.Redeem,
                    textVisibility = isExpanded.value,
                    onClick = { }
                )
                MenuItem(
                    text = "Settings",
                    icon = Icons.Outlined.Settings,
                    textVisibility = isExpanded.value,
                    onClick = {
                        pushWithLimitScreen(navigator, Profile())
                    }
                )
                MenuItem(
                    text = "Notifications",
                    icon = Icons.Outlined.Notifications,
                    textVisibility = isExpanded.value,
                    onClick = { }
                )
                Spacer(modifier = Modifier.weight(1f))
                MenuItem(
                    text = "Sign out",
                    icon = Icons.AutoMirrored.Outlined.Logout,
                    textVisibility = isExpanded.value,
                    onClick = { }
                )
            }
        }

    }
}

@Composable
fun MenuItem(
    text: String,
    icon: ImageVector,
    textVisibility: Boolean = false,
    onClick: () -> Unit,
) {
    TextButton(
        text = text,
        icon = icon,
        isMenuItem = true,
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Start,
        color = Themes.Light.navigationPill,
        textVisibility = textVisibility,
        onClick = onClick
    )
}
