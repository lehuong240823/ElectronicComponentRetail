package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.TransactionStatusRepository
import org.example.project.domain.model.TransactionStatus

class TransactionStatusViewModel(private val transactionStatusRepository: TransactionStatusRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _transactionStatus = mutableStateOf<TransactionStatus?>(null)
    val transactionStatus: State<TransactionStatus?> get() = _transactionStatus

    private val _transactionStatussList = mutableStateOf<List<TransactionStatus>>(emptyList())
    val transactionStatussList: State<List<TransactionStatus>> get() = _transactionStatussList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createTransactionStatus(transactionStatus: TransactionStatus) {
        val result = transactionStatusRepository.createTransactionStatus(transactionStatus)
        if (result != null) {
            _operationStatus.value = "TransactionStatus Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create TransactionStatus"
        }
    }

    suspend fun getTransactionStatus(transactionStatusId: Int) {
        val result = transactionStatusRepository.getTransactionStatus(transactionStatusId)
        if (result != null) {
            _transactionStatus.value = result
        } else {
            _operationStatus.value = "TransactionStatus Not Found"
        }
    }

    suspend fun updateTransactionStatus(transactionStatusId: Int, transactionStatus: TransactionStatus) {
        val result = transactionStatusRepository.updateTransactionStatus(transactionStatusId, transactionStatus)
        if (result != null) {
            _operationStatus.value = "TransactionStatus Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update TransactionStatus"
        }
    }

    suspend fun deleteTransactionStatus(transactionStatusId: Int) {
        val result = transactionStatusRepository.deleteTransactionStatus(transactionStatusId)
        if (result) {
            _operationStatus.value = "TransactionStatus Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete TransactionStatus"
        }
    }

    suspend fun getAllTransactionStatuss(currentPage: Int) {
        val result = transactionStatusRepository.getAllTransactionStatuss(currentPage)
        _transactionStatussList.value = result?.content ?: emptyList()
        _totalPage.value = result?.totalPages ?: 0
    }
}