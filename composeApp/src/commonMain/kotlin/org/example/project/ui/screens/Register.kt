package org.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.example.project.ui.theme.Colors
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.ThemeColors
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun Register(navController: NavController) {
    MaterialTheme(
        colors = lightColors().copy(background = Color.Black),
        typography = Typography(defaultFontFamily = Typography.loadInterFontFamily(),
            button = Typography.Style.BodyBase
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
            Column(
                modifier = Modifier.wrapContentSize()
                    .width(320.dp)
                    .padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                InputField("Email", email, onValueChange = { email = it })
                InputField("Password", password, onValueChange = { password = it })

                ButtonGroup(
                    colors = Themes.Light.brand,
                    onClick = {
                        navController.navigate("manageSuppliers")
                    },
                    text = "Register"
                )
            }
        }
    }
}

@Composable
fun AppTheme(
    colors: ThemeColors = Themes.Light.default,
    typography: androidx.compose.material.Typography = Typography(defaultFontFamily = Typography.loadInterFontFamily()),
    content: @Composable () -> Unit
) {

    content()
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    MaterialTheme {

    }
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S200),

        ) {
        Text(text = label,
            )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = Typography.Style.SingleLineBodyBase.copy()
        )
    }
}

@Composable
fun ButtonGroup(
    colors: ThemeColors,
    text: String,
    onClick: () -> Unit,
) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Colors.Default.Light.Background.BrandDefault,
            contentColor = Colors.Default.Light.Text.BrandOnBrand),
        shape = RoundedCornerShape(Size.Radius.R200)
    ) {
        Text(text = text, color = colors.text!!.onBrand!!)
    }
}