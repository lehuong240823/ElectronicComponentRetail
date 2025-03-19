package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.VoucherRepository
import org.example.project.domain.model.Voucher

class VoucherViewModel(private val voucherRepository: VoucherRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdVoucher = mutableStateOf<Voucher?>(null)
    val createdVoucher: State<Voucher?> get() = _createdVoucher
    
    private val _voucher = mutableStateOf<Voucher?>(null)
    val voucher: State<Voucher?> get() = _voucher
    
    private val _updatedVoucher = mutableStateOf<Voucher?>(null)
    val updatedVoucher: State<Voucher?> get() = _updatedVoucher

    private val _vouchersList = mutableStateOf<List<Voucher>>(emptyList())
    val vouchersList: State<List<Voucher>> get() = _vouchersList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createVoucher(voucher: Voucher) {
        val result = voucherRepository.createVoucher(voucher)
        if (result != null) {
            _createdVoucher.value = result
            _operationStatus.value = "Voucher Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Voucher"
        }
    }

    suspend fun getVoucher(voucherId: Int) {
        val result = voucherRepository.getVoucher(voucherId)
        if (result != null) {
            _voucher.value = result
            _operationStatus.value = "Voucher Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Voucher or Voucher Not Found"
        }
    }

    suspend fun updateVoucher(voucherId: Int, voucher: Voucher) {
        val result = voucherRepository.updateVoucher(voucherId, voucher)
        if (result != null) {
            _updatedVoucher.value = result
            _operationStatus.value = "Voucher Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Voucher"
        }
    }

    suspend fun deleteVoucher(voucherId: Int) {
        val result = voucherRepository.deleteVoucher(voucherId)
        if (result) {
            _operationStatus.value = "Voucher Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Voucher"
        }
    }

    suspend fun getAllVouchers(currentPage: Int) {
        val result = voucherRepository.getAllVouchers(currentPage)
        if (result != null) {
            _vouchersList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Voucher Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Voucher"
        }
    }

    suspend fun getVouchersByVoucherTypeId(currentPage: Int, voucherType: Byte) {
        val result = voucherRepository.getVouchersByVoucherTypeId(currentPage, voucherType)
        if (result != null) {
            _vouchersList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Voucher Get All Successfully"
        } else {
            _operationStatus.value = "Voucher to Get All Account"
        }
    }
}