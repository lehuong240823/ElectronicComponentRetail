package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.VoucherTypeRepository
import org.example.project.domain.model.VoucherType

class VoucherTypeViewModel(private val voucherTypeRepository: VoucherTypeRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdVoucherType = mutableStateOf<VoucherType?>(null)
    val createdVoucherType: State<VoucherType?> get() = _createdVoucherType
    
    private val _voucherType = mutableStateOf<VoucherType?>(null)
    val voucherType: State<VoucherType?> get() = _voucherType
    
    private val _updatedVoucherType = mutableStateOf<VoucherType?>(null)
    val updatedVoucherType: State<VoucherType?> get() = _updatedVoucherType

    private val _voucherTypesList = mutableStateOf<List<VoucherType>>(emptyList())
    val voucherTypesList: State<List<VoucherType>> get() = _voucherTypesList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createVoucherType(voucherType: VoucherType) {
        val result = voucherTypeRepository.createVoucherType(voucherType)
        if (result != null) {
            _createdVoucherType.value = result
            _operationStatus.value = "VoucherType Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create VoucherType"
        }
    }

    suspend fun getVoucherType(voucherTypeId: Int) {
        val result = voucherTypeRepository.getVoucherType(voucherTypeId)
        if (result != null) {
            _voucherType.value = result
            _operationStatus.value = "VoucherType Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get VoucherType or VoucherType Not Found"
        }
    }

    suspend fun updateVoucherType(voucherTypeId: Int, voucherType: VoucherType) {
        val result = voucherTypeRepository.updateVoucherType(voucherTypeId, voucherType)
        if (result != null) {
            _updatedVoucherType.value = result
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

    suspend fun getAllVoucherTypes(currentPage: Int) {
        val result = voucherTypeRepository.getAllVoucherTypes(currentPage)
        if (result != null) {
            _voucherTypesList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "VoucherType Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All VoucherType"
        }
    }
}