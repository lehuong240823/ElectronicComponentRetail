package org.example.project.presentation.components.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.project.SessionData
import org.example.project.core.enums.AlertType
import org.example.project.presentation.components.Form
import org.example.project.presentation.screens.SignIn
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.pushWithLimitScreen

@Preview
@Composable
fun AlertDialog(
    alertType: MutableState<AlertType> = mutableStateOf(AlertType.Default),
    title: String = "Error Loading Content",
    message: String? = "We encountered an issue while trying to fetch the required information. Please check your internet connection and try again.",
    rootMaxWidth: MutableState<Int> = mutableStateOf(0),
    showDialog: MutableState<Boolean> = mutableStateOf(false),
    usePlatformDefaultWidth: Boolean = true,
    maxWidth: Dp = Dp.Unspecified,
    content: @Composable () -> Unit = {},
    onDismissRequest: () -> Unit = { showDialog.value = false },
    onConfirmation: () -> Unit = {  },
) {
    val _title = mutableStateOf(title)
    val _message = mutableStateOf(message)
    val navigator = LocalNavigator.current

    when(alertType.value) {
        AlertType.Default -> {
            _title.value = title
            _message.value = message
        }
        AlertType.Duplication -> {
            _title.value = "Duplicate Name"
            _message.value = "This name already exists. Please choose another name."
        }
        AlertType.Null -> {
            _title.value = "Require Field Not Valid"
            _message.value = "Require field value should not be blank or null. Please try again."
        }
        AlertType.SendEmailSuccess -> {
            _title.value = "Send Email Success"
            _message.value = "Follow the link in your email to reset password."
        }
        AlertType.Success -> {
            _title.value = "Change Success"
            _message.value = "Your data have been updated."
        }
        AlertType.EmailOrPasswordNull -> {
            _title.value = "Input Not Valid"
            _message.value = "Email and password are required."
        }
        AlertType.TokenExpired -> {
            _title.value = "Session expired"
            _message.value = "Please login again."
        }
        AlertType.EmailNotValid -> {
            _title.value = "Invalid Email"
            _message.value = "Please enter a valid email address."
        }

        AlertType.PasswordNotValid -> {
            _title.value = "Invalid Password"
            _message.value = "The password you entered is incorrect. Please try again."
        }

        AlertType.AccountInactive -> {
            _title.value = "Inactive Account"
            _message.value = "Please contact the super admin to activate your admin account."
        }

        AlertType.ProductNotFound -> {
            _title.value = "Product Not Available"
            _message.value =
                "This product is not currently available. Please contact the super admin for further assistance."
        }

        AlertType.UpdateOrderSuccess -> {
            _title.value = "Order Updated Successfully"
            _message.value = "Your order has been updated successfully."
        }
    }

    if (showDialog.value) {
        Dialog(onDismissRequest = { onDismissRequest() },
            properties = DialogProperties(usePlatformDefaultWidth = usePlatformDefaultWidth)
        ) {
            LazyColumn {
                item {
                    Form(
                        horizontalArrangement = Alignment.Start,
                        maxWidth = maxWidth
                    ) {
                        Text(
                            text = _title.value,
                            style = Typography.Style.Heading6
                        )
                        if (_message.value != null) { BodyText(text = _message.value!!) }
                        content()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(space = Size.Space.S400, alignment = Alignment.End),
                        ) {
                            CustomButton(
                                modifier = Modifier.defaultMinSize(minWidth = 70.dp),
                                text = "Cancel",
                                color = Themes.Light.neutralButton,
                                onClick = {
                                    when (alertType.value) {
                                        AlertType.TokenExpired -> {
                                            SessionData.setCurrentAccount(null)
                                            SessionData.setToken(null)
                                            SessionData.setTokenExpire(null)
                                            pushWithLimitScreen(navigator = navigator, screen = SignIn())
                                        }

                                        else -> {
                                            onDismissRequest()
                                        }
                                    }
                                })
                            CustomButton(
                                modifier = Modifier.defaultMinSize(minWidth = 70.dp),
                                text = "Confirm",
                                onClick = {
                                    showDialog.value = false
                                    when (alertType.value) {
                                        AlertType.TokenExpired -> {
                                            SessionData.setCurrentAccount(null)
                                            SessionData.setToken(null)
                                            SessionData.setTokenExpire(null)
                                            pushWithLimitScreen(navigator = navigator, screen = SignIn())
                                        }

                                        else -> {
                                            onConfirmation()
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}