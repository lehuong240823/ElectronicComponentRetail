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
import org.example.project.domain.model.VoucherRedemption

class VoucherRedemptionApi {
    val endPoint = "/api/voucher-redemptions"

    suspend fun getAllVoucherRedemptions(currentPage: Int): PaginatedResponse<VoucherRedemption> {
        return HttpClient.client.get("${BASE_URL}${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getVoucherRedemption(voucherRedemptionId: Int): VoucherRedemption {
        return HttpClient.client.get("${BASE_URL}${endPoint}/${voucherRedemptionId}").body()
    }
    
    suspend fun createVoucherRedemption(voucherRedemption: VoucherRedemption): VoucherRedemption {
        return HttpClient.client.post("${BASE_URL}${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherRedemption)
        }.body()
    }

    suspend fun updateVoucherRedemption(voucherRedemptionId: Int, voucherRedemption: VoucherRedemption): VoucherRedemption {
        return HttpClient.client.put("${BASE_URL}${endPoint}/${voucherRedemptionId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherRedemption)
        }.body()
    }

    suspend fun deleteVoucherRedemption(voucherRedemptionId: Int): Boolean {
        return try {
            HttpClient.client.delete("${BASE_URL}${endPoint}/${voucherRedemptionId}")
            true
        } catch (e: Exception) {
            println("Error deleting voucherRedemption: ${e.message}")
            false
        }
    }
}