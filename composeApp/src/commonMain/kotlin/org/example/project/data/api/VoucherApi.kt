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
import org.example.project.core.BASE_URL
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.Voucher

class VoucherApi {
    val endPoint = "/api/vouchers"

    suspend fun getAllVouchers(currentPage: Int): PaginatedResponse<Voucher> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getVoucher(voucherId: Int): Voucher {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${voucherId}").body()
    }
    
    suspend fun createVoucher(voucher: Voucher): Voucher {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucher)
        }.body()
    }

    suspend fun updateVoucher(voucherId: Int, voucher: Voucher): Voucher {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${voucherId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucher)
        }.body()
    }

    suspend fun deleteVoucher(voucherId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${voucherId}")
            true
        } catch (e: Exception) {
            println("Error deleting voucher: ${e.message}")
            false
        }
    }
}