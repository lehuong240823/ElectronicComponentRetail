package org.example.project.presentation.components.table

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.example.project.core.enums.AlertType
import org.example.project.domain.model.Category
import org.example.project.domain.model.Provider
import org.example.project.domain.model.Voucher
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Checkbox
import org.example.project.presentation.components.dropdown.DefaultMenuItems
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.screens.administrator.*
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.ProviderViewModel
import org.example.project.presentation.viewmodel.VoucherTypeViewModel
import org.example.project.presentation.viewmodel.VoucherViewModel
import org.jetbrains.compose.resources.vectorResource

@Composable
fun VoucherTable(
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    voucherViewModel: VoucherViewModel,
    voucherList: MutableState<List<Voucher>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    headers: List<String> = listOf("Voucher ID", "Code", "Type", "Discount Value",
        "Min Order", "Max Discount", "Active", "Action"),
    //"Used Count","Max Uses", "Valid From", "Valid Until"
    weights: List<Float> = listOf(1f, 2f, 2f, 2f, 1f, 1f, 1f, 1f),
    textAligns: List<TextAlign> = listOf(
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Center,
        TextAlign.Center,
    ),
) {
    val showEditNewVoucherDialog = mutableStateOf(false)
    val updateVoucher = mutableStateOf(Voucher())

    AddEditVoucherDialog(
        title = "Edit",
        scope = scope,
        showLoadingOverlay = showLoadingOverlay,
        showAddEditVoucherDialog = showEditNewVoucherDialog,
        showErrorDialog = showErrorDialog,
        alertType = alertType,
        voucher = updateVoucher,
        onConfirmation = {
            scope.launch {
                handlerEditVoucher(
                    scope = scope,
                    totalPage = totalPage,
                    currentPage = currentPage,
                    voucherList = voucherList,
                    voucher = updateVoucher,
                    voucherViewModel = voucherViewModel,
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
            voucherList.value.forEach { voucher ->
                VoucherRow(
                    weights = weights,
                    voucher = voucher,
                    onEdit = {
                        updateVoucher.value = voucher
                        showEditNewVoucherDialog.value = true
                    },
                    onDelete = {
                        scope.launch {
                            handlerDeleteVoucher(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                voucherList = voucherList,
                                voucher = mutableStateOf(voucher),
                                voucherViewModel = voucherViewModel,
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
fun VoucherRow(
    weights: List<Float>,
    voucher: Voucher,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyText(
            modifier = Modifier.weight(weights[0]),
            text = voucher.id.toString() ?: "",
        )
        BodyText(
            modifier = Modifier.weight(weights[1]),
            text = voucher.code ?: "",
        )
        BodyText(
            modifier = Modifier.weight(weights[2]),
            text = voucher.voucherType?.name ?: "",
        )
        BodyText(
            modifier = Modifier.weight(weights[3]),
            text = voucher.discountValue?.doubleValue().toString(),
        )
        BodyText(
            modifier = Modifier.weight(weights[4]),
            text = voucher.minOrder?.doubleValue().toString(),
        )
        BodyText(
            modifier = Modifier.weight(weights[5]),
            text = voucher.maxDiscount?.doubleValue().toString(),
        )
        Box(
            modifier = Modifier.weight(weights[6]),
        ) {
            Checkbox(
                modifier = Modifier.align(Alignment.Center),
                enabled = false,
                checked = mutableStateOf(voucher.isActive ?: false)
            )
        }
        Box(Modifier.weight(weights[7]))
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