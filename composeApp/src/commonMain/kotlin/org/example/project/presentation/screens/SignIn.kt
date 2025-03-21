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
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_google
import kotlinx.coroutines.Dispatchers
import org.example.project.data.api.AccountApi
import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.data.repository.AccountRepository
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.common.Hyperlink
import org.example.project.presentation.components.common.RoundIconButton
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.AccountViewModel
import org.example.project.presentation.viewmodel.FirebaseEmailAuthViewModel
import org.example.project.pushWithLimitScreen
import org.example.project.signInWithGoogle


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
        val viewModel: AccountViewModel = AccountViewModel(accountRepository = AccountRepository(AccountApi()))
        val firebaseEmailAuthViewModel = FirebaseEmailAuthViewModel(FirebaseEmailAuthRepository(FirebaseEmailAuthApi()))

        var email by remember { mutableStateOf(value = "") }
        var password by remember { mutableStateOf(value = "") }
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
                        value = email,
                        onValueChange = { email = it }
                    )
                    InputField(
                        label = "Password",
                        value = password,
                        onValueChange = { password = it }
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

                            //pushWithLimitScreen(navigator, CreateOrder())
                            //signInButtonAction()
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

