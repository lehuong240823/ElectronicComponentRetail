package org.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.project.ui.components.*
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Typography

class Profile  : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        ColumnBackground {
            item {
                Form(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    BodyText(
                        text = "Profile",
                        textStyle = Typography.Style.Heading5
                    )
                    InputField(
                        label = "Username",
                        value = "abcd",
                        enabled = false,
                        onValueChange = {},
                    )
                    InputField(
                        label = "Email",
                        value = "example@email.com",
                        onValueChange = {},
                    )
                    InputField(
                        label = "Phone number",
                        value = "095332575",
                        onValueChange = {},
                    )
                    TextButton(
                        text = "Save",
                        onClick = {}
                    )
                }
            }
        }
    }
}