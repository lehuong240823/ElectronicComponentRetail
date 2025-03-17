package org.example.project.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.Typography

class Profile  : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        ColumnBackground {
            //item {
                Form(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    BodyText(
                        text = "Profile",
                        style = Typography.Style.Heading5
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
                    CustomButton(
                        text = "Save",
                        onClick = {}
                    )
                }
            //}
        }
    }
}