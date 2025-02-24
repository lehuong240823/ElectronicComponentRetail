package org.example.project.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import org.example.project.data.api.AccountApi
import org.example.project.data.repository.AccountRepository
import org.example.project.pushWithLimitScreen
import org.example.project.ui.components.Button
import org.example.project.ui.components.Form
import org.example.project.ui.components.Hyperlink
import org.example.project.ui.components.IconButton
import org.example.project.ui.components.InputField
import org.example.project.ui.viewmodel.AccountViewModel


class SignIn() : Screen {
    @Composable
    @Preview
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: AccountViewModel = AccountViewModel(accountRepository = AccountRepository(AccountApi()))


        MaterialTheme {
            var email by remember {
                mutableStateOf(value = "")
            }

            var password by remember {
                mutableStateOf(value = "")
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Form {
                    InputField("Email", email, onValueChange = { email = it })
                    InputField("Password", password, onValueChange = { password = it })
                    Hyperlink(
                        text = "Forgot password?",
                        onClick = {
                            pushWithLimitScreen(
                                navigator = navigator,
                                ForgotPassword()
                            )
                        },
                    )


                    Button(
                        text = "Sign In",
                        onClick = {
                            CoroutineScope(Dispatchers.Default).launch {
                                email = email.trim()
                                //viewModel.findAccountByEmail(email)
                                //if (viewModel.account.value?.email==email) {
                                pushWithLimitScreen(navigator, CreateOrder())

                                //}
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
                    IconButton(res = Res.drawable.ic_google)
                }
            }
        }
    }
}