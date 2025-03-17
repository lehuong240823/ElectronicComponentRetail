package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.elcom
import org.example.project.presentation.*
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.screens.*
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.pushWithLimitScreen
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Header(
    modifier: Modifier = Modifier,
    rootMaxWidth: MutableState<Int>,
    colors: ButtonColor = Themes.Light.primaryLayout
) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .background(color = colors.defaultBackground!!)
                .border(Size.Stroke.Border, colors.border!!)
                .padding(if(rootMaxWidth.value.isExpanded()) Size.Space.S800 else Size.Space.S600)
                .onGloballyPositioned { coordinates ->
                    //rootMaxWidth = coordinates.size.width
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Size.Space.S600)
        ) {
            Icon(
                modifier = Modifier.height(20.dp),
                painter = painterResource(Res.drawable.elcom),
                contentDescription = "Logo",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            if(rootMaxWidth.value.isCompactOrMedium()) {
                    ExposedDropdownMenuButton(
                        icon = Icons.Outlined.Menu,
                    ) {
                        HeaderAuth(
                            maxItemsInEachRow = 1
                        )
                    }
            }
            if(rootMaxWidth.value.isExpanded()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S600),
                ) {
                    NavigationPillList()
                    HeaderAuth()
                    HeaderAuthWIthCredential()
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
            CustomButton(
                text = "Products",
                color = Themes.Light.navigationPill,
                onClick = {
                    pushWithLimitScreen(
                        navigator = navigator, ProductList()
                    )
                }
            )
            CustomButton(
                text = "Categories",
                color = Themes.Light.navigationPill,
                onClick = {

                }
            )
            CustomButton(
                text = "Orders",
                color = Themes.Light.navigationPill,
                onClick = {
                    pushWithLimitScreen(
                        navigator = navigator, UserViewOrder()
                    )
                }
            )
            CustomButton(
                text = "Vouchers",
                color = Themes.Light.navigationPill,
                onClick = {

                }
            )
            CustomButton(
                text = "Account",
                color = Themes.Light.navigationPill,
                onClick = {
                    pushWithLimitScreen(navigator, Profile())
                }
            )
            CustomButton(
                text = "Support",
                color = Themes.Light.navigationPill,
                onClick = {

                }
            )
        }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeaderAuth(
    maxItemsInEachRow: Int = 2
) {
    val navigator = LocalNavigator.current
    Row (
        modifier = Modifier.wrapContentSize().widthIn(max = 240.dp),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
        //maxItemsInEachRow = maxItemsInEachRow,
        //overflow = FlowRowOverflow.Visible
    ) {
        CustomButton(
            text = "Sign In",
            color = Themes.Light.neutralButton,
            modifier = Modifier.weight(1f),
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, SignIn()
                )
            }
        )

        CustomButton(
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

@Composable
fun HeaderAuthWIthCredential() {
    val navigator = LocalNavigator.current
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        IconButton(onClick = {
            pushWithLimitScreen(
                navigator = navigator, CartView()
            )
        }) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null
            )
        }
        IconButton(onClick = {

        }) {
            Icon(
                imageVector = Icons.Outlined.Face,
                contentDescription = null
            )
        }
    }
}