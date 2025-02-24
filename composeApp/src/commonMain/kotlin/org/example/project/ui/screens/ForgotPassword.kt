package org.example.project.ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.project.ui.components.Button
import org.example.project.ui.components.Form
import org.example.project.ui.components.InputField
import org.example.project.ui.theme.Typography


class ForgotPassword : Screen {
    @Composable
    @Preview
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
                    InputField("Email", email, onValueChange = { email = it })
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button("Cancel",
                            modifier = Modifier.weight(1f),
                            onClick = {
                                navigator?.pop()
                            })
                        Button("Reset Password",
                            modifier = Modifier.weight(2f),
                            onClick = {

                            })
                    }

                }
            }
        }
    }
}