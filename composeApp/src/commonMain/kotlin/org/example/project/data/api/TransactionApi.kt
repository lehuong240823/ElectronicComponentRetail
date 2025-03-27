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
import org.example.project.domain.model.Order
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.Transaction

class TransactionApi {
    val endPoint = "/api/transactions"

    suspend fun getAllTransactions(currentPage: Int): PaginatedResponse<Transaction> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getTransaction(transactionId: Int): Transaction {
        return HttpClient.client.get("$BASE_URL${endPoint}/${transactionId}").body()
    }
    
    suspend fun createTransaction(transaction: Transaction): Transaction {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transaction)
        }.body()
    }

    suspend fun updateTransaction(transactionId: Int, transaction: Transaction): Transaction {
        return HttpClient.client.put("$BASE_URL${endPoint}/${transactionId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(transaction)
        }.body()
    }

    suspend fun deleteTransaction(transactionId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${transactionId}")
            true
        } catch (e: Exception) {
            println("Error deleting transaction: ${e.message}")
            false
        }
    }

    suspend fun getTransactionsByPaymentMethodId(currentPage: Int, paymentMethod: Byte): PaginatedResponse<Transaction> {
        return HttpClient.client.get("$BASE_URL${endPoint}/payment-method/id/${paymentMethod}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getTransactionsByTransactionStatusId(currentPage: Int, transactionStatus: Byte): PaginatedResponse<Transaction> {
        return HttpClient.client.get("$BASE_URL${endPoint}/transaction-status/id/${transactionStatus}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getTransactionsByOrderId(currentPage: Int, order: Int): PaginatedResponse<Transaction> {
        return HttpClient.client.get("$BASE_URL${endPoint}/order/id/${order}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getTransactionsByTransactionStatusIdAndUserId(
        currentPage: Int,
        user: Int,
        transactionStatus: Byte
    ): PaginatedResponse<Transaction> {
        return HttpClient.client.get("$BASE_URL${endPoint}/user?userId=${user}&transactionStatusId=${transactionStatus}&size=${getPageSize()}&page=${currentPage}")
            .body()
    }

    suspend fun getTransactionsByUserId(currentPage: Int, user: Int): PaginatedResponse<Transaction> {
        return HttpClient.client.get("$BASE_URL${endPoint}/user/id/${user}?size=${getPageSize()}&page=${currentPage}").body()
    }
}