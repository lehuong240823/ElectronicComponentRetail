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
import org.example.project.domain.model.VoucherType

class VoucherTypeApi {
    val endPoint = "/api/voucher-types"

    suspend fun getAllVoucherTypes(currentPage: Int): PaginatedResponse<VoucherType> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getVoucherType(voucherTypeId: Int): VoucherType {
        return HttpClient.client.get("$BASE_URL${endPoint}/${voucherTypeId}").body()
    }
    
    suspend fun createVoucherType(voucherType: VoucherType): VoucherType {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherType)
        }.body()
    }

    suspend fun updateVoucherType(voucherTypeId: Int, voucherType: VoucherType): VoucherType {
        return HttpClient.client.put("$BASE_URL${endPoint}/${voucherTypeId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherType)
        }.body()
    }

    suspend fun deleteVoucherType(voucherTypeId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${voucherTypeId}")
            true
        } catch (e: Exception) {
            println("Error deleting voucherType: ${e.message}")
            false
        }
    }
}