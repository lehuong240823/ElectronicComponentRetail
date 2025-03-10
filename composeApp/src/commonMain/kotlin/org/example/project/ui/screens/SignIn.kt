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
import org.example.project.*
import org.example.project.data.api.AccountApi
import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.data.repository.AccountRepository
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.domain.model.FirebaseEmailAuthRequest
import org.example.project.ui.components.TextButton
import org.example.project.ui.components.Form
import org.example.project.ui.components.Hyperlink
import org.example.project.ui.components.RoundIconButton
import org.example.project.ui.components.InputField
import org.example.project.ui.theme.Size
import org.example.project.ui.viewmodel.AccountViewModel
import org.example.project.ui.viewmodel.FirebaseEmailAuthViewModel


class SignIn() : Screen {

    val viewModel: AccountViewModel = AccountViewModel(accountRepository = AccountRepository(AccountApi()))
    val firebaseEmailAuthViewModel = FirebaseEmailAuthViewModel(FirebaseEmailAuthRepository(FirebaseEmailAuthApi()))
    lateinit var email: String
    lateinit var password: String
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        email = remember { mutableStateOf(value = "").value }
        password = remember { mutableStateOf(value = "").value }
        MaterialTheme {
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(Size.Padding.Sm),
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
                    Hyperlink(
                        text = "Forgot password?",
                        onClick = {
                            pushWithLimitScreen(
                                navigator = navigator,
                                ForgotPassword()
                            )
                        },
                    )


                    TextButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            CoroutineScope(Dispatchers.Default).launch {
                                viewModel.getAccountByEmail("someemail")
                            }
                            pushWithLimitScreen(navigator, CreateOrder())
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
                        signInwithGoogle()
                        //WebView()
                    })
                }
            }
        }
    }

    fun signInButtonAction() {
        CoroutineScope(Dispatchers.Default).launch {
            email = email.trim()
            password = password.trim()
            if(!email.isNullOrEmpty() && !password.isNullOrEmpty() && password.length >= 6) {
                var request = FirebaseEmailAuthRequest(email = email, password = password)
                firebaseEmailAuthViewModel.signIn(request)

                var response = firebaseEmailAuthViewModel.response.value
                if (response != null && response.registered) {
                    //pushWithLimitScreen(navigator, CreateOrder())
                }
            }
        }
    }
}

