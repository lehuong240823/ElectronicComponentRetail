package org.example.project.presentation.screens.administrator

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.checkError
import org.example.project.core.enums.AlertType
import org.example.project.data.api.VoucherApi
import org.example.project.data.api.VoucherTypeApi
import org.example.project.data.repository.VoucherRepository
import org.example.project.data.repository.VoucherTypeRepository
import org.example.project.domain.model.Voucher
import org.example.project.domain.model.VoucherType
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.AddDialog
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Checkbox
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.table.TopTableTemplate
import org.example.project.presentation.components.table.VoucherTable
import org.example.project.presentation.theme.Size
import org.example.project.presentation.viewmodel.VoucherTypeViewModel
import org.example.project.presentation.viewmodel.VoucherViewModel

class AdministratorVoucherView: Screen {
    @Composable
    override fun Content() {
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope{ Dispatchers.Default}
        val showLoadingOverlay = mutableStateOf(true)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)
        val showAddEditVoucherDialog = mutableStateOf(false)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val voucherViewModel = VoucherViewModel(VoucherRepository(VoucherApi()))
        val voucherList = mutableStateOf(emptyList<Voucher>())
        val newVoucher = mutableStateOf(Voucher())

        scope.launch {
            handlerGetAllVouchers(
                totalPage = totalPage,
                currentPage = currentPage,
                voucherViewModel = voucherViewModel,
                voucherList = voucherList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType,
            )
        }

        AlertDialog(
            alertType = alertType,
            showDialog = showErrorDialog
        )

        AddEditVoucherDialog(
            title = "Add",
            showAddEditVoucherDialog = showAddEditVoucherDialog,
            scope = scope,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType,
            onConfirmation = {
                if (newVoucher.value.code != null && newVoucher.value.discountValue != null
                    && newVoucher.value.maxUses != null && newVoucher.value.maxUses != null
                    && newVoucher.value.isActive != null
                ) {
                    scope.launch {
                        handlerAddVoucher(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            voucherViewModel = voucherViewModel,
                            voucherList = voucherList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType,
                            voucher = newVoucher
                        )
                        handlerGetAllVouchers(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            voucherViewModel = voucherViewModel,
                            voucherList = voucherList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType
                        )
                        newVoucher.value = Voucher()
                    }
                } else {
                    alertType.value = AlertType.Null
                    showErrorDialog.value = true
                }
            },
            voucher = newVoucher,
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
                    title = "Voucher",
                    showAddNewDialog = showAddEditVoucherDialog
                )
                VoucherTable(
                    scope = scope,
                    totalPage = totalPage,
                    currentPage = currentPage,
                    voucherViewModel = voucherViewModel,
                    voucherList = voucherList,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType,
                )
                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {
                            scope.launch {
                                handlerGetAllVouchers(
                                    totalPage = totalPage,
                                    currentPage = currentPage,
                                    voucherViewModel = voucherViewModel,
                                    voucherList = voucherList,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType,
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEditVoucherDialog(
    scope: CoroutineScope,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    title: String,
    showAddEditVoucherDialog: MutableState<Boolean>,
    onConfirmation: () -> Unit = {},
    voucher: MutableState<Voucher> = mutableStateOf(Voucher()),
) {
    val voucherTypeList = mutableStateOf(emptyList<VoucherType>())
    scope.launch {
        handlerGetAllVoucherTypes(
            voucherTypeList = voucherTypeList,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType
        )
    }
    AddDialog(
        title = "$title New Voucher",
        showAddDialog = showAddEditVoucherDialog,
        onConfirmation = onConfirmation,
        content = {
            val voucherTypeName = mutableStateOf(voucher.value.voucherType?.name?:"")
            val isActive = mutableStateOf(voucher.value.isActive?:false)

            FlowRow(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                InputField(
                    modifier = Modifier.weight(1.5f),
                    placeHolder = "Code",
                    value = voucher.value.code ?: "",
                    onValueChange = {
                        voucher.value = voucher.value.copy(code = it)
                    }
                )
                Checkbox(
                    modifier = Modifier.weight(1f),
                    text = "Active",
                    checked = mutableStateOf(voucher.value.isActive?:false),
                    onCheckedChange = {
                        voucher.value = voucher.value.copy(isActive = it)
                        println(voucher.value.isActive)
                        println(voucher.value.voucherType?.id)
                    }
                )
            }
            ExposedDropdownInputField(
                placeholder = "Type",
                options = voucherTypeList.value.map { it.name ?: "" },
                textFieldValue = mutableStateOf(voucher.value.voucherType?.name?:""),
                onValueChange = {
                    voucher.value = voucher.value.copy(
                        voucherType = voucherTypeList.value.find { type -> type.name == it }
                    )
                }
            )

            InputField(
                placeHolder = "Discount Value",
                value = if (voucher.value.discountValue == null) "" else voucher.value.discountValue?.toPlainString()
                    .toString(),
                onValueChange = { it ->
                    voucher.value =
                        voucher.value.copy(discountValue = it.filter { c -> c.isDigit() || c == '.' }.toBigDecimal())
                }
            )
            InputField(
                placeHolder = "Max Uses",
                value = if (voucher.value.maxUses == null) "" else voucher.value.maxUses.toString(),
                onValueChange = { it ->
                    voucher.value = voucher.value.copy(maxUses = it.filter { it.isDigit() }.toInt())
                }
            )
            FlowRow(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                InputField(
                    modifier = Modifier.weight(1f),
                    placeHolder = "Min Order",
                    value = if (voucher.value.minOrder == null) "" else voucher.value.minOrder?.toPlainString()
                        .toString(),
                    onValueChange = { it ->
                        voucher.value =
                            voucher.value.copy(minOrder = it.filter { c -> c.isDigit() || c == '.' }.toBigDecimal())
                    }
                )
                InputField(
                    modifier = Modifier.weight(1f),
                    placeHolder = "Max Discount",
                    value = if (voucher.value.maxDiscount == null) "" else voucher.value.maxDiscount?.toPlainString()
                        .toString(),
                    onValueChange = {
                        voucher.value =
                            voucher.value.copy(maxDiscount = it.filter { c -> c.isDigit() || c == '.' }.toBigDecimal())
                    }
                )
            }
            FlowRow(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
                overflow = FlowRowOverflow.Visible
            ) {
                InputField(
                    modifier = Modifier.weight(1f),
                    placeHolder = "Valid From",
                    value = if (voucher.value.validFrom == null) "" else voucher.value.validFrom.toString(),
                    onValueChange = {}
                )
                InputField(
                    modifier = Modifier.weight(1f),
                    placeHolder = "Valid Until",
                    value = if (voucher.value.validUntil == null) "" else voucher.value.validUntil.toString(),
                    onValueChange = {}
                )
            }
        }
    )
}

suspend fun handlerGetAllVouchers(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int> = mutableStateOf(0),
    voucherViewModel: VoucherViewModel,
    voucherList: MutableState<List<Voucher>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            voucherViewModel.getAllVouchers(currentPage.value)
            voucherList.value = voucherViewModel.vouchersList.value
            totalPage.value = voucherViewModel.totalPage.value ?: 0
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = voucherViewModel.operationStatus,
    )
}

suspend fun handlerAddVoucher(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    voucherViewModel: VoucherViewModel,
    voucherList: MutableState<List<Voucher>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    voucher: MutableState<Voucher>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            voucherViewModel.createVoucher(voucher.value.copy(usedCount = 0))
            handlerGetAllVouchers(
                totalPage = totalPage,
                currentPage = currentPage,
                voucherViewModel = voucherViewModel,
                voucherList = voucherList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = voucherViewModel.operationStatus,
        onSuccess = {
            if (voucherViewModel.createdVoucher.value == null) {
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

suspend fun handlerEditVoucher(
    scope: CoroutineScope,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    voucherViewModel: VoucherViewModel,
    voucherList: MutableState<List<Voucher>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    voucher: MutableState<Voucher>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            voucherViewModel.updateVoucher(voucher.value.id ?: 0, voucher.value)
            handlerGetAllVouchers(
                totalPage = totalPage,
                currentPage = currentPage,
                voucherViewModel = voucherViewModel,
                voucherList = voucherList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = voucherViewModel.operationStatus,
    )
}

suspend fun handlerDeleteVoucher(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    voucherViewModel: VoucherViewModel,
    voucherList: MutableState<List<Voucher>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    voucher: MutableState<Voucher>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            voucherViewModel.deleteVoucher(voucher.value.id ?: 0)
            handlerGetAllVouchers(
                totalPage = totalPage,
                currentPage = currentPage,
                voucherViewModel = voucherViewModel,
                voucherList = voucherList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = voucherViewModel.operationStatus,
    )
}

suspend fun handlerGetAllVoucherTypes(
    voucherTypeList: MutableState<List<VoucherType>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    val voucherTypeViewModel = VoucherTypeViewModel(VoucherTypeRepository(VoucherTypeApi()))
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            voucherTypeViewModel.getAllVoucherTypes(0)
            voucherTypeList.value = voucherTypeViewModel.voucherTypesList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = voucherTypeViewModel.operationStatus,
    )
}