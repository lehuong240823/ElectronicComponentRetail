package org.example.project.data.repository

import org.example.project.domain.model.Transaction
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.TransactionApi

class TransactionRepository(private val transactionApi: TransactionApi) {

    suspend fun getAllTransactions(currentPage: Int): PaginatedResponse<Transaction>? {
        return try {
            transactionApi.getAllTransactions(currentPage)
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            null
        }
    }
    
    suspend fun getTransaction(userId: Int): Transaction? {
        return try {
            transactionApi.getTransaction(userId)
        } catch (e: Exception) {
            println("Error fetching transaction: ${e.message}")
            null
        }
    }

    suspend fun createTransaction(transaction: Transaction): Transaction? {
        return try {
            transactionApi.createTransaction(transaction)
        } catch (e: Exception) {
            println("Error creating transaction: ${e.message}")
            null
        }
    }

    suspend fun updateTransaction(transactionId: Int, transaction: Transaction): Transaction? {
        return try {
            transactionApi.updateTransaction(transactionId, transaction)
        } catch (e: Exception) {
            println("Error updating transaction: ${e.message}")
            null
        }
    }

    suspend fun deleteTransaction(transactionId: Int): Boolean {
        return try {
            transactionApi.deleteTransaction(transactionId)
        } catch (e: Exception) {
            println("Error deleting transaction: ${e.message}")
            false
        }
    }

    suspend fun getTransactionsByPaymentMethodId(currentPage: Int, paymentMethod: Byte): PaginatedResponse<Transaction>? {
        return try {
            transactionApi.getTransactionsByPaymentMethodId(currentPage, paymentMethod)
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            null
        }
    }

    suspend fun getTransactionsByTransactionStatusId(currentPage: Int, transactionStatus: Byte): PaginatedResponse<Transaction>? {
        return try {
            transactionApi.getTransactionsByTransactionStatusId(currentPage, transactionStatus)
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            null
        }
    }

    suspend fun getTransactionsByOrderId(currentPage: Int, order: Int): PaginatedResponse<Transaction>? {
        return try {
            transactionApi.getTransactionsByOrderId(currentPage, order)
        } catch (e: Exception) {
            println("Error fetching transactions: ${e.message}")
            null
        }
    }
}