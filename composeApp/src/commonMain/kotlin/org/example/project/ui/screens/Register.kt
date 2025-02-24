package org.example.project.ui.screens

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
import org.example.project.domain.model.Account
import org.example.project.pushWithLimitScreen
import org.example.project.ui.components.Button
import org.example.project.ui.components.Form
import org.example.project.ui.components.Hyperlink
import org.example.project.ui.components.IconButton
import org.example.project.ui.components.InputField
import org.example.project.ui.viewmodel.AccountViewModel

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
                    InputField("Email", email, onValueChange = { email = it })
                    InputField("Password", password, onValueChange = { password = it })
                    Hyperlink(text = "Already got an account?",
                        onClick = {
                            pushWithLimitScreen(navigator = navigator, screen = SignIn())
                        })
                    Button(text = "Register",
                        onClick = {
                            if (!email.isNullOrBlank() && !password.isNullOrBlank()) {
                                CoroutineScope(Dispatchers.Default).launch {
                                    //if (email.isNullOrBlank() && password.isNullOrBlank()) {
                                    viewModel.createAccount(Account(email = email, role = "User"))
                                    print(operationStatus)
                                    //}
                                }
                            }
                        }
                    )
                    Text("Or sign up with")
                    IconButton(res = Res.drawable.ic_google)
                }
            }
        }
    }
}