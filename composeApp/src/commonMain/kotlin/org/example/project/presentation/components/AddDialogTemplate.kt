package org.example.project.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
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
                    /*Image(
                        modifier = Modifier.aspectRatio(1f).fillMaxSize(),
                        //painter = painterResource(Res.drawable.Image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )*/
                    /*AsyncImage(
                        model = Res.getUri("drawable/Image.png"),
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )*/

                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.aspectRatio(1f),
                        model = "https://i0.wp.com/spacenews.com/wp-content/uploads/2025/01/Thuraya-4-scaled.jpg?resize=2000%2C1241&quality=89&ssl=1",
                        contentDescription = null,
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