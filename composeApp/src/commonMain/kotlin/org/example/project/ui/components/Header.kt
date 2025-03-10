package org.example.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.project.pushWithLimitScreen
import org.example.project.ui.forCompactAndMedium
import org.example.project.ui.forExpanded
import org.example.project.ui.screens.Profile
import org.example.project.ui.screens.Register
import org.example.project.ui.screens.SignIn
import org.example.project.ui.screens.UserViewOrder
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography


@Composable
fun Header(
    colors: ButtonColor = Themes.Light.primaryLayout
) {
    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .background(color = colors.defaultBackground!!)
                .border(Size.Stroke.Border, colors.border!!)
                .padding(Size.Space.S800),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Size.Space.S600)
        ) {

            Text(text = "elcom", style = Typography.Style.Heading3)
            Spacer(modifier = Modifier.weight(1f))
            Row(modifier = Modifier.forCompactAndMedium()) {
                TextButton(
                    text = "",
                    icon = Icons.Outlined.Menu,
                    color = Themes.Light.navigationPill,
                    onClick = {
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S600),
                modifier = Modifier.forExpanded()
            ) {
                NavigationPillList()
                HeaderAuth()
            }

        }
    }
}


@Composable
fun NavigationPillList() {
    val navigator = LocalNavigator.current
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
    ) {
        TextButton(
            text = "Products",
            color = Themes.Light.navigationPill,
            onClick = {

            }
        )
        TextButton(
            text = "Categories",
            color = Themes.Light.navigationPill,
            onClick = {

            }
        )
        TextButton(
            text = "Orders",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, UserViewOrder()
                )
            }
        )
        TextButton(
            text = "Vouchers",
            color = Themes.Light.navigationPill,
            onClick = {

            }
        )
        TextButton(
            text = "Account",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(navigator, Profile())
            }
        )
        TextButton(
            text = "Support",
            color = Themes.Light.navigationPill,
            onClick = {

            }
        )
    }
}

@Composable
fun HeaderAuth() {
    val navigator = LocalNavigator.current
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        TextButton(
            text = "Sign In",
            color = Themes.Light.neutralButton,
            modifier = Modifier.weight(1f),
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, SignIn()
                )
            }
        )
        TextButton(
            text = "Register",
            modifier = Modifier.weight(1f),
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, Register()
                )
            }
        )
    }
}