package org.example.project.presentation.components.table

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.core.enums.AlertType
import org.example.project.domain.model.Provider
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.screens.administrator.*
import org.example.project.presentation.viewmodel.ProviderViewModel
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ProviderTable(
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    providerViewModel: ProviderViewModel,
    providerList: MutableState<List<Provider>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    headers: List<String> = listOf("Provider ID", "Name", "Type", "Email", "Phone Number", "Address", "Action"),
    weights: List<Float> = listOf(1f, 1f, 1f, 2f, 2f, 2f, 1f),
    textAligns: List<TextAlign> = listOf(
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Center
    ),
) {
    val showEditProviderDialog = mutableStateOf(false)
    val updateProvider = mutableStateOf(Provider())

    AddEditProviderDialog(
        title = "Edit",
        showAddEditProviderDialog = showEditProviderDialog,
        provider = updateProvider,
        onConfirmation = {
            scope.launch {
                handlerEditProvider(
                    totalPage = totalPage,
                    currentPage = currentPage,
                    providerList = providerList,
                    provider = updateProvider,
                    providerViewModel = providerViewModel,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
            }
        }
    )
    Table(
        headers = headers,
        weights = weights,
        textAligns = textAligns,
        tableRowsContent = {
            providerList.value.forEach { provider ->
                ProviderRow(
                    weights = weights,
                    provider = provider,
                    onEdit = {
                        updateProvider.value = provider
                        showEditProviderDialog.value = true
                    },
                    onDelete = {
                        scope.launch {
                            handlerDeleteProvider(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                providerList = providerList,
                                provider = mutableStateOf(provider),
                                providerViewModel = providerViewModel,
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
fun ProviderRow(
    weights: List<Float>,
    provider: Provider,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyText(
            modifier = Modifier.weight(weights[0]),
            text = provider.id.toString(),
            textAlign = TextAlign.Center,
        )
        BodyText(
            modifier = Modifier.weight(weights[1]),
            text = provider.name ?: "",
        )
        BodyText(
            modifier = Modifier.weight(weights[2]),
            text = provider.type ?: "",
        )
        BodyText(
            modifier = Modifier.weight(weights[3]),
            text = provider.email ?: "",
        )
        BodyText(
            modifier = Modifier.weight(weights[4]),
            text = provider.phoneNumber ?: "",
        )
        BodyText(
            modifier = Modifier.weight(weights[5]),
            text = provider.address ?: "",
        )
        Box(Modifier.weight(weights[6]))
        {
            ExposedDropdownMenuButton(
                modifier = Modifier.align(Alignment.Center),
                icon = vectorResource(Res.drawable.ic_dots_vertical),
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}