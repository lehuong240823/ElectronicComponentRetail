package org.example.project.data.api

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import org.example.project.getPageSize
import org.example.project.core.HttpClient
import org.example.project.BASE_URL
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.TransactionStatus

class TransactionStatusApi {
    val endPoint = "/api/transaction-statuses"

    suspend fun getAllTransactionStatuss(currentPage: Int): PaginatedResponse<TransactionStatus> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getTransactionStatus(transactionStatusId: Int): TransactionStatus {
        return HttpClient.client.get("$BASE_URL${endPoint}/${transactionStatusId}").body()
    }
    
    suspend fun createTransactionStatus(transactionStatus: TransactionStatus): TransactionStatus {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transactionStatus)
        }.body()
    }

    suspend fun updateTransactionStatus(transactionStatusId: Int, transactionStatus: TransactionStatus): TransactionStatus {
        return HttpClient.client.put("$BASE_URL${endPoint}/${transactionStatusId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transactionStatus)
        }.body()
    }

    suspend fun deleteTransactionStatus(transactionStatusId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${transactionStatusId}")
            true
        } catch (e: Exception) {
            println("Error deleting transactionStatus: ${e.message}")
            false
        }
    }
}