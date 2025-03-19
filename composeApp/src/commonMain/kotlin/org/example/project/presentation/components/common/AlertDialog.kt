package org.example.project.presentation.components.common

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.project.presentation.components.Form
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Preview
@Composable
fun AlertDialog(
    title: String = "Error Loading Content",
    message: String? = "We encountered an issue while trying to fetch the required information. Please check your internet connection and try again.",
    rootMaxWidth: MutableState<Int> = mutableStateOf(0),
    showDialog: MutableState<Boolean> = mutableStateOf(false),
    usePlatformDefaultWidth: Boolean = true,
    maxWidth: Dp = Dp.Unspecified,
    content: @Composable () -> Unit = {},
    onDismissRequest: () -> Unit = { showDialog.value = false },
    onConfirmation: () -> Unit = { showDialog.value = false },
) {

    if (showDialog.value) {
        Dialog(onDismissRequest = { onDismissRequest() },
            properties = DialogProperties(usePlatformDefaultWidth = usePlatformDefaultWidth)
        ) {
            LazyColumn {
                item {
                    Form(
                        modifier = Modifier.wrapContentHeight(unbounded = true),
                        horizontalArrangement = Alignment.Start,
                        maxWidth = maxWidth
                    ) {
                        Text(
                            text = title,
                            style = Typography.Style.Heading6
                        )
                        if (message != null) { BodyText(text = message) }
                        content()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(space = Size.Space.S400, alignment = Alignment.End),
                        ) {
                            CustomButton(
                                modifier = Modifier.defaultMinSize(minWidth = 70.dp),
                                text = "Cancel",
                                color = Themes.Light.neutralButton,
                                onClick = { onDismissRequest() })
                            CustomButton(
                                modifier = Modifier.defaultMinSize(minWidth = 70.dp),
                                text = "Confirm",
                                onClick = { onConfirmation() })
                        }
                    }
                }
            }
        }
    }
}