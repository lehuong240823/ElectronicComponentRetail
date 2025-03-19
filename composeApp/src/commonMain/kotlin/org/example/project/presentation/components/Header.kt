package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.elcom
import org.example.project.SessionData
import org.example.project.core.enums.AccountRoleType
import org.example.project.domain.model.Account
import org.example.project.presentation.*
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.screens.*
import org.example.project.presentation.screens.administrator.AdministratorCategoryView
import org.example.project.presentation.screens.administrator.AdministratorProviderView
import org.example.project.presentation.screens.administrator.AdministratorVoucherView
import org.example.project.presentation.screens.user.UserCategoryView
import org.example.project.presentation.screens.user.UserProviderView
import org.example.project.presentation.screens.user.UserVoucherView
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
    val currentAccount = SessionData.getCurrentAccount()
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .background(color = colors.defaultBackground!!)
            .border(Size.Stroke.Border, colors.border!!)
            .padding(if(rootMaxWidth.value.isExpanded()) Size.Space.S800 else Size.Space.S600),
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

            }
        }
        if(rootMaxWidth.value.isExpanded()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S600),
            ) {
                when(currentAccount?.accountRole?.name) {
                    AccountRoleType.Administrator.toString() -> {
                        AdministratorNavigationPillList()
                    }
                    else -> {
                        UserNavigationPillList(currentAccount = currentAccount)
                    }
                }

                if(currentAccount == null) {
                    HeaderAuth()
                } else {
                    HeaderAuthWithCredential(currentAccount = currentAccount)
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun UserNavigationPillList(
    currentAccount: Account?
) {
    val navigator = LocalNavigator.current
    FlowRow(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
        maxLines = 1
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
                pushWithLimitScreen(
                    navigator = navigator, UserCategoryView()
                )
            }
        )
        CustomButton(
            text = "Providers",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, UserProviderView()
                )
            }
        )
        CustomButton(
            text = "Orders",
            color = Themes.Light.navigationPill,
            onClick = {
                if (currentAccount == null) {
                    pushWithLimitScreen(
                        navigator = navigator, SignIn()
                    )
                } else {
                    pushWithLimitScreen(
                        navigator = navigator, OrderView()
                    )
                }
            }
        )
        CustomButton(
            text = "Transactions",
            color = Themes.Light.navigationPill,
            onClick = {
                if (currentAccount == null) {
                    pushWithLimitScreen(
                        navigator = navigator, SignIn()
                    )
                } else {
                    pushWithLimitScreen(
                        navigator = navigator, TransactionView()
                    )
                }
            }
        )
        CustomButton(
            text = "Vouchers",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, UserVoucherView()
                )
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
fun AdministratorNavigationPillList() {
    val navigator = LocalNavigator.current
    FlowRow(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
        maxLines = 1
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
                pushWithLimitScreen(
                    navigator = navigator, AdministratorCategoryView()
                )
            }
        )
        CustomButton(
            text = "Providers",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, AdministratorProviderView()
                )
            }
        )
        CustomButton(
            text = "Orders",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, OrderView()
                )
            }
        )
        CustomButton(
            text = "Transactions",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, TransactionView()
                )
            }
        )
        CustomButton(
            text = "Vouchers",
            color = Themes.Light.navigationPill,
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, AdministratorVoucherView()
                )
            }
        )
    }
}

@Composable
fun HeaderAuth(
    maxItemsInEachRow: Int = 2
) {
    val navigator = LocalNavigator.current
    Row (
        modifier = Modifier.wrapContentSize().widthIn(max = 240.dp),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
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
fun HeaderAuthWithCredential(
    currentAccount: Account?
) {
    val navigator = LocalNavigator.current
    Row(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        if(currentAccount?.accountRole?.name != AccountRoleType.Administrator.toString()) {
            IconButton(onClick = {
                if(currentAccount == null) {
                    pushWithLimitScreen(
                        navigator = navigator, SignIn()
                    )
                } else {
                    pushWithLimitScreen(
                        navigator = navigator, CartView()
                    )
                }
            }) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = null
                )
            }
        }

        IconButton(
            onClick = {
                pushWithLimitScreen(
                    navigator = navigator, AccountView()
                )
            }) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null
            )
        }
    }
}