package org.example.project.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.core.FileKit
import io.github.vinceglb.filekit.core.PickerType
import io.github.vinceglb.filekit.core.pickFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalLayoutApi::class, ExperimentalResourceApi::class)
@Composable
fun ImageAddDialog(
    scope: CoroutineScope,
    title: String,
    showImageAddDialog: MutableState<Boolean>,
    imageByteArray: MutableState<ByteArray>,
    onConfirmation: () -> Unit = {},
    url:MutableState<String> =
        mutableStateOf("https://res.cloudinary.com/dvsr9ihcv/image/upload/v1742870878/samples/landscapes/girl-urban-view.jpg"),
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
                        modifier = Modifier.aspectRatio(1f).widthIn(300.dp),
                        model = url.value,
                        contentDescription = null,
                    )
                    CustomButton(
                        text = "Upload",
                        icon = Icons.Outlined.Upload,
                        isIconFirst = true,
                        color = Themes.Light.subtleButton.copy(border = Color.Black),
                        onClick = {
                            scope.launch {
                                val localImage = FileKit.pickFile(type = PickerType.Image)
                                url.value = localImage?.path?:""
                                if (localImage != null) {
                                    imageByteArray.value = localImage.readBytes()
                                }
                            }
                        }
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