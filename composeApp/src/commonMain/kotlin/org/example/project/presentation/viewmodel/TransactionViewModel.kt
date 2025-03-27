package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.TransactionRepository
import org.example.project.domain.model.Transaction

class TransactionViewModel(private val transactionRepository: TransactionRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdTransaction = mutableStateOf<Transaction?>(null)
    val createdTransaction: State<Transaction?> get() = _createdTransaction
    
    private val _transaction = mutableStateOf<Transaction?>(null)
    val transaction: State<Transaction?> get() = _transaction
    
    private val _updatedTransaction = mutableStateOf<Transaction?>(null)
    val updatedTransaction: State<Transaction?> get() = _updatedTransaction

    private val _transactionsList = mutableStateOf<List<Transaction>>(emptyList())
    val transactionsList: State<List<Transaction>> get() = _transactionsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createTransaction(transaction: Transaction) {
        val result = transactionRepository.createTransaction(transaction)
        if (result != null) {
            _createdTransaction.value = result
            _operationStatus.value = "Transaction Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Transaction"
        }
    }

    suspend fun getTransaction(transactionId: Int) {
        val result = transactionRepository.getTransaction(transactionId)
        if (result != null) {
            _transaction.value = result
            _operationStatus.value = "Transaction Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Transaction or Transaction Not Found"
        }
    }

    suspend fun updateTransaction(transactionId: Int, transaction: Transaction) {
        val result = transactionRepository.updateTransaction(transactionId, transaction)
        if (result != null) {
            _updatedTransaction.value = result
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

    suspend fun getAllTransactions(currentPage: Int) {
        val result = transactionRepository.getAllTransactions(currentPage)
        if (result != null) {
            _transactionsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Transaction Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Transaction"
        }
    }

    suspend fun getTransactionsByPaymentMethodId(currentPage: Int, paymentMethod: Byte) {
        val result = transactionRepository.getTransactionsByPaymentMethodId(currentPage, paymentMethod)
        if (result != null) {
            _transactionsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Transaction Get All Successfully"
        } else {
            _operationStatus.value = "Transaction to Get All Account"
        }
    }

    suspend fun getTransactionsByTransactionStatusId(currentPage: Int, transactionStatus: Byte) {
        val result = transactionRepository.getTransactionsByTransactionStatusId(currentPage, transactionStatus)
        if (result != null) {
            _transactionsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Transaction Get All Successfully"
        } else {
            _operationStatus.value = "Transaction to Get All Account"
        }
    }

    suspend fun getTransactionsByTransactionStatusIdAndUserId(currentPage: Int, user: Int, transactionStatus: Byte) {
        val result = transactionRepository.getTransactionsByTransactionStatusIdAndUserId(currentPage, user, transactionStatus)
        if (result != null) {
            _transactionsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Successfully"
        } else {
            _operationStatus.value = "Failed"
        }
    }

    suspend fun getTransactionsByUserId(currentPage: Int, user: Int) {
        val result = transactionRepository.getTransactionsByUserId(currentPage, user)
        if (result != null) {
            _transactionsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Transaction Get All Successfully"
        } else {
            _operationStatus.value = "Failed"
        }
    }


    suspend fun getTransactionsByOrderId(currentPage: Int, order: Int) {
        val result = transactionRepository.getTransactionsByOrderId(currentPage, order)
        if (result != null) {
            _transactionsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Transaction Get All Successfully"
        } else {
            _operationStatus.value = "Transaction to Get All Account"
        }
    }
}