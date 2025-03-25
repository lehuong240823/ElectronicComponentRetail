package org.example.project.presentation.components.table

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.core.enums.AlertType
import org.example.project.domain.model.Category
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.screens.administrator.AddEditCategoryDialog
import org.example.project.presentation.screens.administrator.handlerDeleteCategory
import org.example.project.presentation.screens.administrator.handlerEditCategory
import org.example.project.presentation.viewmodel.CategoryViewModel
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CategoryTable(
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    categoryViewModel: CategoryViewModel,
    categoryList: MutableState<List<Category>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    headers: List<String> = listOf("Category ID", "Image", "Name", "Action"),
    weights: List<Float> = listOf(2f, 2f, 5f, 1f),
    ) {
    val showEditNewCategoryDialog = mutableStateOf(false)
    val updateCategory = mutableStateOf(Category())
    val imageByteArray = mutableStateOf(byteArrayOf())

    AddEditCategoryDialog(
        title = "Edit",
        showAddEditNewCategoryDialog = showEditNewCategoryDialog,
        category = updateCategory,
        onConfirmation = {
            scope.launch {
                handlerEditCategory(
                    totalPage = totalPage,
                    currentPage = currentPage,
                    categoryList = categoryList,
                    category = updateCategory,
                    categoryViewModel = categoryViewModel,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
            }
        },
        scope = scope,
        imageByteArray = TODO()
    )

    Table(
        headers = headers,
        weights = weights,
        tableRowsContent = {
            categoryList.value.forEach { category ->
                CategoryRow(
                    weights = weights,
                    category = category,
                    onEdit = {
                        updateCategory.value = category
                        showEditNewCategoryDialog.value = true
                    },
                    onDelete = {
                        scope.launch {
                            handlerDeleteCategory(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                categoryList = categoryList,
                                category = mutableStateOf(category),
                                categoryViewModel = categoryViewModel,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType
                            )
                        }
                    }
                )
            }
        }
    )
}

@Composable
fun CategoryRow(
    weights: List<Float>,
    category: Category,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        BodyText(
            modifier = Modifier.weight(weights[0]),
            text = category.id.toString(),
            textAlign = TextAlign.Center,
            )
        Box(
            Modifier.weight(weights[1])
        ) {
            /*AsyncImage(
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data("https://lh3.googleusercontent.com/-GpvTVtKwke4/AAAAAAAAAAI/AAAAAAAAAAA/ALKGfknFDiF9fzcMrW_tv-VAFob9V6JUXQ/photo.jpg?sz=46")
                    //.transformations(CircleCropTransformation())
                    .build(),
                contentDescription = "Circular cropped image"
            )*/

        }
        BodyText(
            modifier = Modifier.weight(weights[2]),
            text = category.name.toString()
        )
        ExposedDropdownMenuButton(
            modifier = Modifier.weight(weights[3]),
            icon = vectorResource(Res.drawable.ic_dots_vertical),
            onEdit = onEdit,
            onDelete = onDelete
        )
    }
}

