package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.screens.Profile
import org.example.project.presentation.screens.TransactionView
import org.example.project.presentation.screens.UserViewOrder
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.pushWithLimitScreen


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SideMenu(
    modifier: Modifier = Modifier,
    isExpanded: MutableState<Boolean>,
    color: ButtonColor = Themes.Light.primaryLayout
) {
    val navigator = LocalNavigator.current
    FlowColumn(
        modifier = modifier
            .background(color = color.defaultBackground!!)
            .border(width = Size.Stroke.Border, color = color.border!!)
            .padding(Size.Space.S200)
            .wrapContentWidth()
            .widthIn(48.dp, 260.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
        horizontalArrangement = if (isExpanded.value) Arrangement.Start else Arrangement.Center
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

@Composable
fun MenuItem(
    text: String,
    icon: ImageVector,
    textVisibility: Boolean = false,
    onClick: () -> Unit,
) {
    CustomButton(
        text = text,
        icon = icon,
        isFillMaxWidth = true,
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.Start,
        color = Themes.Light.navigationPill,
        textVisibility = textVisibility,
        onClick = onClick
    )
}
