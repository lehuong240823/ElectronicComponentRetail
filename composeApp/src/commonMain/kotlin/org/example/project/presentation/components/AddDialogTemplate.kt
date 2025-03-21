package org.example.project.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageAddDialog(
    title: String,
    showImageAddDialog: MutableState<Boolean>,
    onUploadButtonClick: () -> Unit = {},
    onConfirmation: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    AlertDialog(
        title = title,
        message = null,
        showDialog = showImageAddDialog,
        content = {
            FlowRow(
                modifier = Modifier.wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S600),
                overflow = FlowRowOverflow.Visible
            ) {
                FlowRow (
                    modifier = Modifier.weight(1f).wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
                    maxItemsInEachRow = 1,
                    maxLines = 2,
                    overflow = FlowRowOverflow.Visible,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier.aspectRatio(1f).fillMaxSize(),
                        painter = painterResource(Res.drawable.Image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    CustomButton(
                        text = "Upload",
                        icon = Icons.Outlined.Upload,
                        isIconFirst = true,
                        color = Themes.Light.subtleButton.copy(border = Color.Black),
                        onClick = onUploadButtonClick
                    )
                }
                Column(
                    modifier = Modifier.weight(1.5f),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
                ) {
                    content()
                }
            }
        },
        onConfirmation = onConfirmation
    )
}

@Composable
fun AddDialog(
    title: String,
    showAddDialog: MutableState<Boolean>,
    onConfirmation: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    AlertDialog(
        title = title,
        message = null,
        showDialog = showAddDialog,
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                content()
            }
        },
        onConfirmation = onConfirmation
    )
}