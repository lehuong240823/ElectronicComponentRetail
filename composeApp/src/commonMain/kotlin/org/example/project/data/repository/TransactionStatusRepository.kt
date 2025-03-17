package org.example.project.data.repository

import org.example.project.domain.model.TransactionStatus
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.TransactionStatusApi

class TransactionStatusRepository(private val transactionStatusApi: TransactionStatusApi) {

    suspend fun getAllTransactionStatuss(currentPage: Int): PaginatedResponse<TransactionStatus>? {
        return try {
            transactionStatusApi.getAllTransactionStatuss(currentPage)
        } catch (e: Exception) {
            println("Error fetching transactionStatuss: ${e.message}")
            null
        }
    }
    
    suspend fun getTransactionStatus(userId: Int): TransactionStatus? {
        return try {
            transactionStatusApi.getTransactionStatus(userId)
        } catch (e: Exception) {
            println("Error fetching transactionStatus: ${e.message}")
            null
        }
    }

    suspend fun createTransactionStatus(transactionStatus: TransactionStatus): TransactionStatus? {
        return try {
            transactionStatusApi.createTransactionStatus(transactionStatus)
        } catch (e: Exception) {
            println("Error creating transactionStatus: ${e.message}")
            null
        }
    }

    suspend fun updateTransactionStatus(transactionStatusId: Int, transactionStatus: TransactionStatus): TransactionStatus? {
        return try {
            transactionStatusApi.updateTransactionStatus(transactionStatusId, transactionStatus)
        } catch (e: Exception) {
            println("Error updating transactionStatus: ${e.message}")
            null
        }
    }

    suspend fun deleteTransactionStatus(transactionStatusId: Int): Boolean {
        return try {
            transactionStatusApi.deleteTransactionStatus(transactionStatusId)
        } catch (e: Exception) {
            println("Error deleting transactionStatus: ${e.message}")
            false
        }
    }
}