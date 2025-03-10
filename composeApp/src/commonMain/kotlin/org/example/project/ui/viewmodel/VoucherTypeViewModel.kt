package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.VoucherTypeRepository
import org.example.project.domain.model.VoucherType

class VoucherTypeViewModel(private val voucherTypeRepository: VoucherTypeRepository) {
    private val _voucherType = mutableStateOf<VoucherType?>(null)
    val voucherType: State<VoucherType?> get() = _voucherType

    private val _voucherTypesList = mutableStateOf<List<VoucherType>>(emptyList())
    val voucherTypesList: State<List<VoucherType>> get() = _voucherTypesList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createVoucherType(voucherType: VoucherType) {
        val result = voucherTypeRepository.createVoucherType(voucherType)
        if (result != null) {
            _operationStatus.value = "VoucherType Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create VoucherType"
        }
    }

    suspend fun getVoucherType(voucherTypeId: Int) {
        val result = voucherTypeRepository.getVoucherType(voucherTypeId)
        if (result != null) {
            _voucherType.value = result
        } else {
            _operationStatus.value = "VoucherType Not Found"
        }
    }

    suspend fun updateVoucherType(voucherTypeId: Int, voucherType: VoucherType) {
        val result = voucherTypeRepository.updateVoucherType(voucherTypeId, voucherType)
        if (result != null) {
            _operationStatus.value = "VoucherType Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update VoucherType"
        }
    }

    suspend fun deleteVoucherType(voucherTypeId: Int) {
        val result = voucherTypeRepository.deleteVoucherType(voucherTypeId)
        if (result) {
            _operationStatus.value = "VoucherType Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete VoucherType"
        }
    }

    suspend fun getAllVoucherTypes() {
        val result = voucherTypeRepository.getAllVoucherTypes()
        _voucherTypesList.value = result?.content ?: emptyList()
    }
}