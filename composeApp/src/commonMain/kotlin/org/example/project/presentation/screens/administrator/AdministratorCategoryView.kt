package org.example.project.presentation.screens.administrator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.CategoryApi
import org.example.project.data.repository.CategoryRepository
import org.example.project.domain.model.Category
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.ImageAddDialog
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.table.CategoryTable
import org.example.project.presentation.components.table.TopTableTemplate
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.CategoryViewModel

class AdministratorCategoryView: Screen {
    @Composable
    override fun Content() {
        val rootMaxWidth = remember { mutableStateOf(0) }
        val currentPage = remember { mutableStateOf(0) }
        val totalPage = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(true)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)
        val showAddNewCategoryDialog = mutableStateOf(false)
        val categoryViewModel = CategoryViewModel(CategoryRepository(CategoryApi()))
        val categoryList: MutableState<List<Category>> = mutableStateOf(emptyList())
        val newCategory = mutableStateOf(Category())

        scope.launch {
            handlerGetAllCategories(
                totalPage = totalPage,
                currentPage = currentPage,
                categoryViewModel = categoryViewModel,
                categoryList = categoryList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }

        AlertDialog(
            alertType = alertType,
            showDialog = showErrorDialog
        )

        AddEditCategoryDialog(
            title = "Add",
            showAddEditNewCategoryDialog = showAddNewCategoryDialog,
            onConfirmation = {
                if (newCategory.value.name != null) {
                    scope.launch {
                        handlerAddCategory(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            categoryViewModel = categoryViewModel,
                            categoryList = categoryList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType,
                            category = newCategory
                        )
                        handlerGetAllCategories(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            categoryViewModel = categoryViewModel,
                            categoryList = categoryList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType
                        )
                        newCategory.value = Category()
                    }
                } else {
                    alertType.value = AlertType.Null
                    showErrorDialog.value = true
                }
            },
            category = newCategory
        )

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopTableTemplate(
                    title = "Category",
                    showAddNewDialog = showAddNewCategoryDialog
                )
                CategoryTable(
                    scope = scope,
                    categoryViewModel = categoryViewModel,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    categoryList = categoryList,
                    totalPage = totalPage,
                    currentPage = currentPage,
                    alertType = alertType
                )

                if (totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {
                            scope.launch {
                                handlerGetAllCategories(
                                    totalPage = totalPage,
                                    currentPage = currentPage,
                                    categoryViewModel = categoryViewModel,
                                    categoryList = categoryList,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AddEditCategoryDialog(
    title: String,
    showAddEditNewCategoryDialog: MutableState<Boolean>,
    onConfirmation: () -> Unit,
    category: MutableState<Category> = mutableStateOf(Category()),
) {
    ImageAddDialog(
        title = "$title New Category",
        showImageAddDialog = showAddEditNewCategoryDialog,
        onUploadButtonClick = {},
        onConfirmation = onConfirmation,
        content = {
            InputField(
                placeHolder = "Name",
                value = category.value.name ?: "",
                onValueChange = {
                    category.value = category.value.copy(name = it)
                }
            )
        }
    )
}

suspend fun handlerGetAllCategories(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    categoryViewModel: CategoryViewModel,
    categoryList: MutableState<List<Category>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            categoryViewModel.getAllCategorys(currentPage.value)
            totalPage.value = categoryViewModel.totalPage.value ?: 0
            categoryList.value = categoryViewModel.categorysList.value
        }
    )

    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = categoryViewModel.operationStatus,
    )
}

suspend fun handlerAddCategory(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    categoryViewModel: CategoryViewModel,
    categoryList: MutableState<List<Category>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    category: MutableState<Category>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            categoryViewModel.createCategory(category.value)
            handlerGetAllCategories(
                totalPage = totalPage,
                currentPage = currentPage,
                categoryViewModel = categoryViewModel,
                categoryList = categoryList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = categoryViewModel.operationStatus,
        onSuccess = {
            if (categoryViewModel.createdCategory.value == null) {
                alertType.value = AlertType.Duplication
                showErrorDialog.value = true
            } else {
                alertType.value = AlertType.Success
                showErrorDialog.value = true
            }
        },
        onFailure = {
            alertType.value = AlertType.Default
        }
    )
}

suspend fun handlerEditCategory(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    categoryViewModel: CategoryViewModel,
    categoryList: MutableState<List<Category>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    category: MutableState<Category>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            categoryViewModel.updateCategory(category.value.id ?: 0, category.value)
            handlerGetAllCategories(
                totalPage = totalPage,
                currentPage = currentPage,
                categoryViewModel = categoryViewModel,
                categoryList = categoryList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = categoryViewModel.operationStatus,
    )
}

suspend fun handlerDeleteCategory(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    categoryViewModel: CategoryViewModel,
    categoryList: MutableState<List<Category>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    category: MutableState<Category>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            categoryViewModel.deleteCategory(category.value.id ?: 0)
            handlerGetAllCategories(
                totalPage = totalPage,
                currentPage = currentPage,
                categoryViewModel = categoryViewModel,
                categoryList = categoryList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = categoryViewModel.operationStatus,
    )
}