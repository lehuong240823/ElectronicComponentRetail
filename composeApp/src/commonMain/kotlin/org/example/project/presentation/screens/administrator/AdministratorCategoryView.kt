package org.example.project.presentation.screens.administrator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import org.example.project.core.enums.AccountRoleType
import org.example.project.presentation.components.table.CategoryTable
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.ImageAddDialog
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.painterResource

class AdministratorCategoryView: Screen {
    @Composable
    override fun Content() {
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val showAddNewCategoryDialog = mutableStateOf(false)

        AddNewCategoryDialog(
            showAddNewCategoryDialog = showAddNewCategoryDialog
        )

        ColumnBackground {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Form(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        BodyText(
                            text = "Category",
                            style = Typography.Style.Heading4
                        )
                        CustomButton(
                            modifier = Modifier.defaultMinSize(minWidth = 80.dp),
                            icon = Icons.Outlined.Add,
                            isIconFirst = true,
                            text = "Add Category",
                            onClick = { showAddNewCategoryDialog.value = true }
                        )
                    }
                }
                CategoryTable()
                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {}
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddNewCategoryDialog(
    showAddNewCategoryDialog: MutableState<Boolean>,
) {
    ImageAddDialog(
        title = "Add New Category",
        showImageAddDialog = showAddNewCategoryDialog,
        onUploadButtonClick = {},
        onConfirmation = {},
        content = {
            InputField(
                placeHolder = "Name",
                value = "",
                onValueChange = {}
            )
        }
    )
}
