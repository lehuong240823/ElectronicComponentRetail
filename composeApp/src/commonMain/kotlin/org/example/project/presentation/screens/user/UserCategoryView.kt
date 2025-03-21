package org.example.project.presentation.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.core.enums.AlertType
import org.example.project.data.api.CategoryApi
import org.example.project.data.repository.CategoryRepository
import org.example.project.domain.model.Category
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.CategoryCard
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.screens.administrator.handlerGetAllCategories
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.viewmodel.CategoryViewModel

class UserCategoryView: Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(false)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val categoryViewModel = CategoryViewModel(CategoryRepository(CategoryApi()))
        val categoryList: MutableState<List<Category>> = mutableStateOf(emptyList())

        AlertDialog(
            alertType = alertType,
            showDialog = showErrorDialog
        )

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

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Themes.Light.primaryLayout.defaultBackground!!)
                    .padding(Size.Space.S800),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S1600),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = spacedBy(Size.Space.S300),
                    horizontalArrangement = spacedBy(Size.Space.S300, alignment = Alignment.CenterHorizontally),
                    maxItemsInEachRow = 10,
                    overflow = FlowRowOverflow.Visible
                ) {
                    categoryList.value.forEach { category ->
                        CategoryCard(
                            category = category
                        )
                    }
                }
                if(totalPage.value > 0) {
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