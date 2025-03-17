package org.example.project.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.Typography


class ForgotPassword : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        MaterialTheme(
            typography = Typography(
                defaultFontFamily = Typography.loadInterFontFamily(),
            )
        ) {
            var email by remember {
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
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CustomButton(
                            text = "Cancel",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navigator?.pop()
                            })
                        CustomButton(
                            text = "Reset Password",
                            modifier = Modifier.weight(2f),
                            onClick = {

                            })
                    }

                }
            }
        }
    }
}