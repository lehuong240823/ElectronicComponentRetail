package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_google
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AccountStatusType
import org.example.project.data.api.AccountApi
import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.data.repository.AccountRepository
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.domain.model.Account
import org.example.project.domain.model.AccountRole
import org.example.project.domain.model.AccountStatus
import org.example.project.domain.model.FirebaseEmailAuthRequest
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.common.Hyperlink
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.AccountViewModel
import org.example.project.presentation.viewmodel.FirebaseEmailAuthViewModel
import org.example.project.pushWithLimitScreen
import org.jetbrains.compose.resources.vectorResource

class Register : Screen {
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
        val showActiveStatusAlertDialog = remember { mutableStateOf(false) }
        val showWarningEmptyFieldAlertDialog = remember { mutableStateOf(false) }
        val showDeletedStatusAlertDialog = remember { mutableStateOf(false) }
        val showSuspendedStatusAlertDialog = remember { mutableStateOf(false) }
        val viewModel = AccountViewModel(accountRepository = AccountRepository(AccountApi()))
        val email = remember { mutableStateOf(value = "") }
        val password = remember { mutableStateOf(value = "") }

        AlertDialog(
            showDialog = showErrorDialog,
            rootMaxWidth = mutableStateOf(0)
        )

        CreateAccountSuccessAlertDialog(
            showCreateAccountSuccessAlertDialog = showCreateAccountSuccessAlertDialog
        )

        WarningEmptyFieldAlertDialog(
            showWarningEmptyFieldAlertDialog = showWarningEmptyFieldAlertDialog
        )

        ActiveStatusAlertDialog(
            showActiveStatusAlertDialog = showActiveStatusAlertDialog
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
            //showHeaderAndFooter = false
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
                    Hyperlink(text = "Already got an account?",
                        onClick = {
                            pushWithLimitScreen(navigator = navigator, screen = SignIn())
                        })
                    CustomButton(
                        text = "Register",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            email.value = email.value.trim().trimIndent().trimMargin()
                            password.value = password.value.trim().trimIndent().trimMargin()
                            if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                                scope.launch {
                                    handlerRegister(
                                        scope = scope,
                                        totalPage = totalPage,
                                        currentPage = currentPage,
                                        showLoadingOverlay = showLoadingOverlay,
                                        showErrorDialog = showErrorDialog,
                                        showCreateAccountSuccessAlertDialog = showCreateAccountSuccessAlertDialog,
                                        showActiveStatusAlertDialog = showActiveStatusAlertDialog,
                                        showDeletedStatusAlertDialog = showDeletedStatusAlertDialog,
                                        showSuspendedStatusAlertDialog = showSuspendedStatusAlertDialog,
                                        viewModel = viewModel,
                                        email = email,
                                        password = password,
                                    )
                                }
                            } else {
                                showWarningEmptyFieldAlertDialog.value = true
                            }
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        //Divider()
                        BodyText(
                            text = "Or sign up with"
                        )
                        //Divider()
                    }
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_google),
                            contentDescription = "google"
                        )
                    }
                }
            }
        }
    }
}

suspend fun handlerRegister(
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    showActiveStatusAlertDialog: MutableState<Boolean>,
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
        operationStatus = viewModel.operationStatus,
        onSuccess = {
            totalPage.value = viewModel.totalPage.value ?: 0
            if (viewModel.totalElements.value!! > 0) {
                handlerForExistEmail(
                    showActiveStatusAlertDialog = showActiveStatusAlertDialog,
                    showDeletedStatusAlertDialog = showDeletedStatusAlertDialog,
                    showSuspendedStatusAlertDialog = showSuspendedStatusAlertDialog,
                    viewModel = viewModel,
                )
            } else {
                scope.launch {
                    handlerForNonExistEmail(
                        scope = scope,
                        email = email,
                        password = password,
                        viewModel = viewModel,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        showCreateAccountSuccessAlertDialog = showCreateAccountSuccessAlertDialog,
                        showActiveStatusAlertDialog = showActiveStatusAlertDialog
                    )
                }
            }
        }
    )
}


suspend fun handlerCreateAccount(
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    showCreateAccountSuccessAlertDialog: MutableState<Boolean>,
    viewModel: AccountViewModel,
    accountRole: AccountRoleType,
    email: MutableState<String>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            viewModel.createAccount(
                Account(
                    email = email.value,
                    username = email.value,
                    accountRole = AccountRole(
                        id = accountRole.ID,
                        name = accountRole.name
                    ),
                    accountStatus = AccountStatus(
                        id = AccountStatusType.Active.ID,
                        name = AccountStatusType.Active.name
                    ),
                    createTime = Clock.System.now()
                )
            )
        }
    )

    checkError(
        showErrorDialog = showErrorDialog,
        operationStatus = viewModel.operationStatus,
        onSuccess = {
            showCreateAccountSuccessAlertDialog.value = true
        }
    )
}

suspend fun handlerForNonExistEmail(
    scope: CoroutineScope,
    email: MutableState<String>,
    password: MutableState<String>,
    viewModel: AccountViewModel,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    showCreateAccountSuccessAlertDialog: MutableState<Boolean>,
    showActiveStatusAlertDialog: MutableState<Boolean>,
) {
    val authViewModel = FirebaseEmailAuthViewModel(FirebaseEmailAuthRepository(FirebaseEmailAuthApi()))
    val request = FirebaseEmailAuthRequest(email = email.value, password = password.value)
    authViewModel.signUp(request)

    checkError(
        showErrorDialog = showErrorDialog,
        operationStatus = authViewModel.operationStatus,
        onSuccess = {
            val response = authViewModel.firebaseEmailAuthResponse.value
            SessionData.setToken(response?.refreshToken)
            SessionData.setTokenExpire(response?.expiresIn)

            scope.launch {
                handlerCreateAccount(
                    showErrorDialog = showErrorDialog,
                    showLoadingOverlay = showLoadingOverlay,
                    showCreateAccountSuccessAlertDialog = showCreateAccountSuccessAlertDialog,
                    viewModel = viewModel,
                    accountRole = AccountRoleType.User,
                    email = email,
                )
            }
        },
    )
}

fun handlerForExistEmail(
    showActiveStatusAlertDialog: MutableState<Boolean>,
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
                    showActiveStatusAlertDialog.value = true
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

@Composable
fun CreateAccountSuccessAlertDialog(
    showCreateAccountSuccessAlertDialog: MutableState<Boolean>,
) {
    val navigator = LocalNavigator.current
    AlertDialog(
        title = "Your account has been created",
        message = "Please sign in.",
        showDialog = showCreateAccountSuccessAlertDialog,
        onConfirmation = {
            pushWithLimitScreen(navigator = navigator, screen = SignIn())
        }
    )
}

@Composable
fun WarningEmptyFieldAlertDialog(
    showWarningEmptyFieldAlertDialog: MutableState<Boolean>,
) {
    AlertDialog(
        title = "Warning",
        message = "Your email or password not valid. Your email or password is not valid. Please check your credentials and try again.",
        showDialog = showWarningEmptyFieldAlertDialog,
    )
}

@Composable
fun DeletedStatusAlertDialog(
    showDeletedStatusAlertDialog: MutableState<Boolean>,
) {
    AlertDialog(
        title = "Account Deleted",
        message = "We found a record associated with this account. Would you like to recover it instead?.",
        showDialog = showDeletedStatusAlertDialog,
        onConfirmation = {

        }
    )
}

@Composable
fun ActiveStatusAlertDialog(
    showActiveStatusAlertDialog: MutableState<Boolean>,
) {
    val navigator = LocalNavigator.current
    AlertDialog(
        title = "Your account already registered",
        message = "Please sign in.",
        showDialog = showActiveStatusAlertDialog,
        onConfirmation = {
            pushWithLimitScreen(navigator = navigator, screen = SignIn())
        }
    )
}

@Composable
fun SuspendedStatusAlertDialog(
    showSuspendedStatusAlertDialog: MutableState<Boolean>,
) {
    AlertDialog(
        title = "Account suspended",
        message = "Your account has been suspended from our app.",
        showDialog = showSuspendedStatusAlertDialog,
    )
}