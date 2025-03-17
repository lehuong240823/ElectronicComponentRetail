package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
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
import org.example.project.data.api.FirebaseEmailAuthApi
import org.example.project.data.repository.AccountRepository
import org.example.project.data.repository.FirebaseEmailAuthRepository
import org.example.project.domain.model.FirebaseEmailAuthRequest
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.RoundIconButton
import org.example.project.presentation.components.common.Hyperlink
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.AccountViewModel
import org.example.project.presentation.viewmodel.FirebaseEmailAuthViewModel
import org.example.project.pushWithLimitScreen
import org.example.project.signInWithGoogle


class SignIn() : Screen {

    val viewModel: AccountViewModel = AccountViewModel(accountRepository = AccountRepository(AccountApi()))
    val firebaseEmailAuthViewModel = FirebaseEmailAuthViewModel(FirebaseEmailAuthRepository(FirebaseEmailAuthApi()))
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var scope = remember { CoroutineScope(Dispatchers.Default) }
        var email by remember { mutableStateOf(value = "") }
        var password by remember { mutableStateOf(value = "") }
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


                    CustomButton(
                        text = "Sign In",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            scope.launch {
                                //viewModel.getAccountByEmail("someemail")
                                viewModel.getAccount(1)
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
                        signInWithGoogle()
                    })
                }
            }
        }
    }

    suspend fun signInButtonAction(email: String, password: String) {
        //CoroutineScope(Dispatchers.Default).launch {
        //email = email.trim()
        //password = password.trim()
            if(!email.isNullOrEmpty() && !password.isNullOrEmpty() && password.length >= 6) {
                var request = FirebaseEmailAuthRequest(email = email, password = password)
                firebaseEmailAuthViewModel.signIn(request)

                var response = firebaseEmailAuthViewModel.response.value
                if (response != null && response.registered) {
                    //pushWithLimitScreen(navigator, CreateOrder())
                }
            }
        //}
    }
}

