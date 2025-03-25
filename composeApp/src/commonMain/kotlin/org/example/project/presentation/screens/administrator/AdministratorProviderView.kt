package org.example.project.presentation.screens.administrator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.ProviderApi
import org.example.project.data.repository.ProviderRepository
import org.example.project.domain.model.Provider
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.AddDialog
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.table.ProviderTable
import org.example.project.presentation.components.table.TopTableTemplate
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.ProviderViewModel

class AdministratorProviderView : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val currentPage = remember { mutableStateOf(0) }
        val totalPage = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(true)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)
        val showAddNewProviderDialog = mutableStateOf(false)
        val providerViewModel = ProviderViewModel(ProviderRepository(ProviderApi()))
        val providerList: MutableState<List<Provider>> = mutableStateOf(emptyList())
        val newProvider = mutableStateOf(Provider())

        AlertDialog(
            alertType = alertType,
            showDialog = showErrorDialog
        )

        AddEditProviderDialog(
            title = "Add",
            showAddEditProviderDialog = showAddNewProviderDialog,
            onConfirmation = {
                if (newProvider.value.name != null && newProvider.value.email != null) {
                    scope.launch {
                        handlerAddProvider(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            providerViewModel = providerViewModel,
                            providerList = providerList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType,
                            provider = newProvider
                        )
                        handlerGetAllProviders(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            providerViewModel = providerViewModel,
                            providerList = providerList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType
                        )
                        newProvider.value = Provider()
                    }
                } else {
                    alertType.value = AlertType.Null
                    showErrorDialog.value = true
                }
            },
            provider = newProvider
        )

        scope.launch {
            handlerGetAllProviders(
                totalPage = totalPage,
                currentPage = currentPage,
                providerViewModel = providerViewModel,
                providerList = providerList,
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
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopTableTemplate(
                    title = "Provider",
                    showAddNewDialog = showAddNewProviderDialog
                )
                ProviderTable(
                    scope = scope,
                    providerViewModel = providerViewModel,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    providerList = providerList,
                    totalPage = totalPage,
                    currentPage = currentPage,
                    alertType = alertType
                )
                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {
                            scope.launch {
                                handlerGetAllProviders(
                                    totalPage = totalPage,
                                    currentPage = currentPage,
                                    providerViewModel = providerViewModel,
                                    providerList = providerList,
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
fun AddEditProviderDialog(
    title: String,
    showAddEditProviderDialog: MutableState<Boolean>,
    onConfirmation: () -> Unit,
    provider: MutableState<Provider>,
) {
    AddDialog(
        title = "$title New Provider",
        showAddDialog = showAddEditProviderDialog,
        onConfirmation = onConfirmation,
        content = {
            InputField(
                label = "Name",
                placeHolder = "Name",
                value = provider.value.name ?: "",
                onValueChange = {
                    provider.value = provider.value.copy(name = it)
                }
            )
            InputField(
                label = "Type",
                placeHolder = "Type",
                value = provider.value.type ?: "",
                onValueChange = {
                    provider.value = provider.value.copy(type = it)
                }
            )
            InputField(
                label = "Email",
                placeHolder = "Email",
                value = provider.value.email ?: "",
                onValueChange = {
                    provider.value = provider.value.copy(email = it)
                }
            )
            InputField(
                label = "Phone Number",
                placeHolder = "Phone Number",
                value = provider.value.phoneNumber ?: "",
                onValueChange = {
                    provider.value = provider.value.copy(phoneNumber = it)
                }
            )
            InputField(
                label = "Address",
                placeHolder = "Address",
                value = provider.value.address ?: "",
                onValueChange = {
                    provider.value = provider.value.copy(address = it)
                }
            )
        }
    )
}

suspend fun handlerGetAllProviders(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    providerViewModel: ProviderViewModel,
    providerList: MutableState<List<Provider>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            providerViewModel.getAllProviders(currentPage.value)
            totalPage.value = providerViewModel.totalPage.value ?: 0
            providerList.value = providerViewModel.providersList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = providerViewModel.operationStatus,
    )
}

suspend fun handlerAddProvider(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    providerViewModel: ProviderViewModel,
    providerList: MutableState<List<Provider>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    provider: MutableState<Provider>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            providerViewModel.createProvider(provider.value)
            handlerGetAllProviders(
                totalPage = totalPage,
                currentPage = currentPage,
                providerViewModel = providerViewModel,
                providerList = providerList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = providerViewModel.operationStatus,
        onSuccess = {
            if (providerViewModel.createdProvider.value == null) {
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

suspend fun handlerEditProvider(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    providerViewModel: ProviderViewModel,
    providerList: MutableState<List<Provider>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    provider: MutableState<Provider>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            providerViewModel.updateProvider(provider.value.id ?: 0, provider.value)
            handlerGetAllProviders(
                totalPage = totalPage,
                currentPage = currentPage,
                providerViewModel = providerViewModel,
                providerList = providerList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = providerViewModel.operationStatus,
    )
}

suspend fun handlerDeleteProvider(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    providerViewModel: ProviderViewModel,
    providerList: MutableState<List<Provider>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    provider: MutableState<Provider>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            providerViewModel.deleteProvider(provider.value.id ?: 0)
            handlerGetAllProviders(
                totalPage = totalPage,
                currentPage = currentPage,
                providerViewModel = providerViewModel,
                providerList = providerList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = providerViewModel.operationStatus,
    )
}