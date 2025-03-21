package org.example.project.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.domain.model.FirebaseResetPasswordLinkRequest
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.viewmodel.FirebaseEmailAuthViewModel


class ForgotPassword : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val rootMaxWidth = remember { mutableStateOf(0) }
        val showLoadingOverlay = mutableStateOf(false)
        val email = remember { mutableStateOf("") }
        val showErrorDialog = remember { mutableStateOf(false) }
        val alertType = remember { mutableStateOf(AlertType.Default) }
        val authViewModel = FirebaseEmailAuthViewModel(FirebaseEmailAuthRepository(FirebaseEmailAuthApi()))

        AlertDialog(
            alertType = alertType,
            showDialog = showErrorDialog,
        )

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay,
            showHeaderAndFooter = false
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(Size.Space.S800),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Form {
                    InputField(
                        label = "Email",
                        value = email.value,
                        onValueChange = { email.value = it }
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CustomButton(
                            text = "Cancel",
                            modifier = Modifier.weight(1f),
                            color = Themes.Light.neutralButton,
                            onClick = {
                                navigator?.pop()
                            }
                        )
                        CustomButton(
                            text = "Reset Password",
                            modifier = Modifier.weight(2f),
                            onClick = {
                                email.value = email.value.trim().trimIndent()
                                if (email.value.isNotEmpty() && email.value.contains("@")) {
                                    scope.launch {
                                        sendResetPasswordLink(
                                            authViewModel = authViewModel,
                                            showLoadingOverlay = showLoadingOverlay,
                                            showErrorDialog = showErrorDialog,
                                            alertType = alertType,
                                            email = email
                                        )
                                    }
                                } else {
                                    alertType.value = AlertType.Null
                                    showErrorDialog.value = true
                                }
                            }
                        )
                    }

                }
            }
        }

    }
}

suspend fun sendResetPasswordLink(
    authViewModel: FirebaseEmailAuthViewModel,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    email: MutableState<String>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            authViewModel.sendResetPasswordLinkToEmail(
                FirebaseResetPasswordLinkRequest(
                    email = email.value,
                ),
            )
        }
    )
    checkError(
        showErrorDialog = showErrorDialog,
        operationStatus = authViewModel.operationStatus,
        alertType = alertType,
        onSuccess = {
            alertType.value = AlertType.SendEmailSuccess
            showErrorDialog.value = true
        },
    )
}