package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_google
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.*
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AccountStatusType
import org.example.project.core.enums.AlertType
import org.example.project.data.api.AccountApi
import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.data.repository.AccountRepository
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.domain.model.FirebaseEmailAuthRequest
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.common.Hyperlink
import org.example.project.presentation.components.common.RoundIconButton
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.AccountViewModel
import org.example.project.presentation.viewmodel.FirebaseEmailAuthViewModel


class SignIn() : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(false)
        val totalPage = mutableStateOf(1)
        val currentPage = mutableStateOf(0)
        val showErrorDialog = remember { mutableStateOf(false) }
        val showCreateAccountSuccessAlertDialog = remember { mutableStateOf(false) }
        val showWarningEmptyFieldAlertDialog = remember { mutableStateOf(false) }
        val showDeletedStatusAlertDialog = remember { mutableStateOf(false) }
        val showSuspendedStatusAlertDialog = remember { mutableStateOf(false) }
        val viewModel = AccountViewModel(accountRepository = AccountRepository(AccountApi()))
        val firebaseEmailAuthViewModel = FirebaseEmailAuthViewModel(FirebaseEmailAuthRepository(FirebaseEmailAuthApi()))
        val alertType = mutableStateOf(AlertType.Default)

        val email = remember { mutableStateOf(value = "") }
        val password = remember { mutableStateOf(value = "") }

        AlertDialog(
            showDialog = showErrorDialog,
            alertType = alertType,
            rootMaxWidth = mutableStateOf(0)
        )

        CreateAccountSuccessAlertDialog(
            showCreateAccountSuccessAlertDialog = showCreateAccountSuccessAlertDialog
        )

        WarningEmptyFieldAlertDialog(
            showWarningEmptyFieldAlertDialog = showWarningEmptyFieldAlertDialog
        )


        DeletedStatusAlertDialog(
            showDeletedStatusAlertDialog = showDeletedStatusAlertDialog
        )

        SuspendedStatusAlertDialog(
            showSuspendedStatusAlertDialog = showSuspendedStatusAlertDialog
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
                    InputField(
                        label = "Password",
                        value = password.value,
                        onValueChange = { password.value = it }
                    )
                    Hyperlink(
                        text = "Forgot password?",
                        onClick = {
                            pushWithLimitScreen(
                                navigator = navigator,
                                ForgotPassword()
                            )
                        },
                    )


                    CustomButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            email.value = email.value.trim().trimIndent()
                            password.value = password.value.trim().trimIndent()
                            if(email.value.isNotEmpty() && password.value.isNotEmpty()) {
                                scope.launch {
                                    handlerSignIn(
                                        navigator = navigator,
                                        scope = scope,
                                        alertType = alertType,
                                        totalPage = totalPage,
                                        currentPage = currentPage,
                                        showLoadingOverlay = showLoadingOverlay,
                                        showErrorDialog = showErrorDialog,
                                        showDeletedStatusAlertDialog = showDeletedStatusAlertDialog,
                                        showSuspendedStatusAlertDialog = showSuspendedStatusAlertDialog,
                                        showCreateAccountSuccessAlertDialog = showCreateAccountSuccessAlertDialog,
                                        viewModel = viewModel,
                                        email = email,
                                        password = password
                                    )
                                }
                            } else {
                                alertType.value = AlertType.EmailOrPasswordNull
                                showErrorDialog.value = true
                            }

                        }
                    )

                    Hyperlink(
                        text = "Don't have an account?",
                        onClick = {
                            pushWithLimitScreen(
                                navigator = navigator,
                                Register()
                            )
                        },
                    )
                    Text("Or sign in with")
                    RoundIconButton(res = Res.drawable.ic_google, onClick = {
                        signInWithGoogle()
                    })
                }
            }
        }
    }
}

suspend fun handlerSignIn(
    navigator: Navigator?,
    scope: CoroutineScope,
    alertType: MutableState<AlertType>,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    showDeletedStatusAlertDialog: MutableState<Boolean>,
    showSuspendedStatusAlertDialog: MutableState<Boolean>,
    showCreateAccountSuccessAlertDialog: MutableState<Boolean>,
    viewModel: AccountViewModel,
    email: MutableState<String>,
    password: MutableState<String>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = { viewModel.getAccountsByEmail(currentPage.value, email.value) }
    )
    checkError(
        showErrorDialog = showErrorDialog,
        alertType = alertType,
        operationStatus = viewModel.operationStatus,
        onSuccess = {
            totalPage.value = viewModel.totalPage.value ?: 0
            if (viewModel.totalElements.value!! > 0) {
                scope.launch {
                    handlerForExistEmailSignIn(
                        navigator = navigator,
                        alertType = alertType,
                        email = email,
                        password = password,
                        showDeletedStatusAlertDialog = showDeletedStatusAlertDialog,
                        showSuspendedStatusAlertDialog = showSuspendedStatusAlertDialog,
                        viewModel = viewModel,
                        showErrorDialog = showErrorDialog,
                        showLoadingOverlay = showLoadingOverlay,
                    )
                }
            }
        }
    )
}

suspend fun handlerForExistEmailSignIn(
    navigator: Navigator?,
    alertType: MutableState<AlertType>,
    email: MutableState<String>,
    password: MutableState<String>,
    showErrorDialog: MutableState<Boolean>,
    showLoadingOverlay: MutableState<Boolean>,
    showDeletedStatusAlertDialog: MutableState<Boolean>,
    showSuspendedStatusAlertDialog: MutableState<Boolean>,
    viewModel: AccountViewModel,
) {
    val accounts = viewModel.accountsList.value.sortedByDescending { it.createTime }
    val latestAccount = accounts.first()
    when (latestAccount.accountRole?.name) {
        AccountRoleType.User.name -> {
            when (latestAccount.accountStatus?.name) {
                AccountStatusType.Active.name -> {
                    handlerAuthSignIn(
                        navigator = navigator,
                        alertType = alertType,
                        email = email,
                        password = password,
                        showErrorDialog = showErrorDialog,
                        showLoadingOverlay = showLoadingOverlay
                    )
                    SessionData.setCurrentAccount(latestAccount)
                }

                AccountStatusType.Inactive.name -> {
                    println("Email inactive and existed")
                }

                AccountStatusType.Deleted.name -> {
                    showDeletedStatusAlertDialog.value = true
                }

                AccountStatusType.Suspended.name -> {
                    showSuspendedStatusAlertDialog.value = true
                }
            }
        }
    }
}


suspend fun handlerAuthSignIn(
    navigator: Navigator?,
    alertType: MutableState<AlertType>,
    //scope: CoroutineScope,
    email: MutableState<String>,
    password: MutableState<String>,
    showErrorDialog: MutableState<Boolean>,
    showLoadingOverlay: MutableState<Boolean>,
) {
    val authViewModel = FirebaseEmailAuthViewModel(FirebaseEmailAuthRepository(FirebaseEmailAuthApi()))
    val request = FirebaseEmailAuthRequest(email = email.value, password = password.value)

    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            authViewModel.signIn(request)
        }
    )

    checkError(
        showErrorDialog = showErrorDialog,
        alertType = alertType,
        operationStatus = authViewModel.operationStatus,
        onSuccess = {
            val response = authViewModel.firebaseEmailAuthResponse.value
            SessionData.setToken(response?.refreshToken)
            SessionData.setTokenExpire(response?.expiresIn)

            pushWithLimitScreen(
                navigator = navigator,
                ProductList()
            )
        }
    )
}