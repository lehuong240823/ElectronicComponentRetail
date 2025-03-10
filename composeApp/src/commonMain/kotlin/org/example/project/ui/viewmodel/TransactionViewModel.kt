package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.TransactionRepository
import org.example.project.domain.model.Transaction

class TransactionViewModel(private val transactionRepository: TransactionRepository) {
    private val _transaction = mutableStateOf<Transaction?>(null)
    val transaction: State<Transaction?> get() = _transaction

    private val _transactionsList = mutableStateOf<List<Transaction>>(emptyList())
    val transactionsList: State<List<Transaction>> get() = _transactionsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createTransaction(transaction: Transaction) {
        val result = transactionRepository.createTransaction(transaction)
        if (result != null) {
            _operationStatus.value = "Transaction Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Transaction"
        }
    }

    suspend fun getTransaction(transactionId: Int) {
        val result = transactionRepository.getTransaction(transactionId)
        if (result != null) {
            _transaction.value = result
        } else {
            _operationStatus.value = "Transaction Not Found"
        }
    }

    suspend fun updateTransaction(transactionId: Int, transaction: Transaction) {
        val result = transactionRepository.updateTransaction(transactionId, transaction)
        if (result != null) {
            _operationStatus.value = "Transaction Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Transaction"
        }
    }

    suspend fun deleteTransaction(transactionId: Int) {
        val result = transactionRepository.deleteTransaction(transactionId)
        if (result) {
            _operationStatus.value = "Transaction Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Transaction"
        }
    }

    suspend fun getAllTransactions() {
        val result = transactionRepository.getAllTransactions()
        _transactionsList.value = result?.content ?: emptyList()
    }
}