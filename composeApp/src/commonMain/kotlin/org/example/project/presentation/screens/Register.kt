package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
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
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.RoundIconButton
import org.example.project.presentation.components.common.Hyperlink
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.viewmodel.AccountViewModel
import org.example.project.pushWithLimitScreen

class Register : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: AccountViewModel = AccountViewModel(accountRepository = AccountRepository(AccountApi()))
        val operationStatus by viewModel.operationStatus
        MaterialTheme(
            typography = Typography(
                //defaultFontFamily = Typography.loadInterFontFamily(),
            )
        ) {
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
                    Hyperlink(text = "Already got an account?",
                        onClick = {
                            pushWithLimitScreen(navigator = navigator, screen = SignIn())
                        })
                    CustomButton(
                        text = "Register",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (!email.isNullOrBlank() && !password.isNullOrBlank()) {
                                CoroutineScope(Dispatchers.Default).launch {
                                    //if (email.isNullOrBlank() && password.isNullOrBlank()) {
                                    //viewModel.createAccount(Account(email = email, role = "User"))

                                    //}
                                }
                            }
                        }
                    )
                    Text("Or sign up with")
                    RoundIconButton(res = Res.drawable.ic_google)
                }
            }
        }
    }
}