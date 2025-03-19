package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.TransactionStatusRepository
import org.example.project.domain.model.TransactionStatus

class TransactionStatusViewModel(private val transactionStatusRepository: TransactionStatusRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdTransactionStatus = mutableStateOf<TransactionStatus?>(null)
    val createdTransactionStatus: State<TransactionStatus?> get() = _createdTransactionStatus
    
    private val _transactionStatus = mutableStateOf<TransactionStatus?>(null)
    val transactionStatus: State<TransactionStatus?> get() = _transactionStatus
    
    private val _updatedTransactionStatus = mutableStateOf<TransactionStatus?>(null)
    val updatedTransactionStatus: State<TransactionStatus?> get() = _updatedTransactionStatus

    private val _transactionStatussList = mutableStateOf<List<TransactionStatus>>(emptyList())
    val transactionStatussList: State<List<TransactionStatus>> get() = _transactionStatussList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createTransactionStatus(transactionStatus: TransactionStatus) {
        val result = transactionStatusRepository.createTransactionStatus(transactionStatus)
        if (result != null) {
            _createdTransactionStatus.value = result
            _operationStatus.value = "TransactionStatus Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create TransactionStatus"
        }
    }

    suspend fun getTransactionStatus(transactionStatusId: Int) {
        val result = transactionStatusRepository.getTransactionStatus(transactionStatusId)
        if (result != null) {
            _transactionStatus.value = result
            _operationStatus.value = "TransactionStatus Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get TransactionStatus or TransactionStatus Not Found"
        }
    }

    suspend fun updateTransactionStatus(transactionStatusId: Int, transactionStatus: TransactionStatus) {
        val result = transactionStatusRepository.updateTransactionStatus(transactionStatusId, transactionStatus)
        if (result != null) {
            _updatedTransactionStatus.value = result
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
        if (result != null) {
            _transactionStatussList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "TransactionStatus Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All TransactionStatus"
        }
    }
}