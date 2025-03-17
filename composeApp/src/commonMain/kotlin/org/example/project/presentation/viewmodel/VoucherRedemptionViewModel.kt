package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.VoucherRedemptionRepository
import org.example.project.domain.model.VoucherRedemption

class VoucherRedemptionViewModel(private val voucherRedemptionRepository: VoucherRedemptionRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _voucherRedemption = mutableStateOf<VoucherRedemption?>(null)
    val voucherRedemption: State<VoucherRedemption?> get() = _voucherRedemption

    private val _voucherRedemptionsList = mutableStateOf<List<VoucherRedemption>>(emptyList())
    val voucherRedemptionsList: State<List<VoucherRedemption>> get() = _voucherRedemptionsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createVoucherRedemption(voucherRedemption: VoucherRedemption) {
        val result = voucherRedemptionRepository.createVoucherRedemption(voucherRedemption)
        if (result != null) {
            _operationStatus.value = "VoucherRedemption Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create VoucherRedemption"
        }
    }

    suspend fun getVoucherRedemption(voucherRedemptionId: Int) {
        val result = voucherRedemptionRepository.getVoucherRedemption(voucherRedemptionId)
        if (result != null) {
            _voucherRedemption.value = result
        } else {
            _operationStatus.value = "VoucherRedemption Not Found"
        }
    }

    suspend fun updateVoucherRedemption(voucherRedemptionId: Int, voucherRedemption: VoucherRedemption) {
        val result = voucherRedemptionRepository.updateVoucherRedemption(voucherRedemptionId, voucherRedemption)
        if (result != null) {
            _operationStatus.value = "VoucherRedemption Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update VoucherRedemption"
        }
    }

    suspend fun deleteVoucherRedemption(voucherRedemptionId: Int) {
        val result = voucherRedemptionRepository.deleteVoucherRedemption(voucherRedemptionId)
        if (result) {
            _operationStatus.value = "VoucherRedemption Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete VoucherRedemption"
        }
    }

    suspend fun getAllVoucherRedemptions(currentPage: Int) {
        val result = voucherRedemptionRepository.getAllVoucherRedemptions(currentPage)
        _voucherRedemptionsList.value = result?.content ?: emptyList()
        _totalPage.value = result?.totalPages ?: 0
    }
}