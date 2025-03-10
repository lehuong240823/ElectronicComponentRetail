package org.example.project.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@Preview
@Composable
fun AlertDialog(
    title: String,
    message: String? = null,
    showDialog: Boolean = false,
    usePlatformDefaultWidth: Boolean = true,
    maxWidth: Dp = Dp.Unspecified,
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {

    if (showDialog) {
        Dialog(onDismissRequest = { onDismissRequest() },
            properties = DialogProperties(usePlatformDefaultWidth = usePlatformDefaultWidth)
        ) {
            Form(
                horizontalArrangement = Alignment.Start,
                maxWidth = maxWidth
            ) {
                Text(
                    text = title,
                    style = Typography.Style.Heading6
                )
                if (message != null) { BodyText(text = message) }
                content.invoke()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S400)
                ) {
                    Spacer(Modifier.weight(1f))
                    TextButton(
                        text = "Cancel",
                        color = Themes.Light.neutralButton,
                        onClick = { onDismissRequest() })
                    TextButton(
                        text = "Confirm",
                        onClick = { onConfirmation() })
                }
            }
        }
    }
}