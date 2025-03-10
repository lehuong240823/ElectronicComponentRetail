package org.example.project.data.api

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.example.project.core.HttpClient
import org.example.project.core.getUrl
import org.example.project.domain.model.PaginatedResponse
import org.example.project.domain.model.VoucherRedemption

class VoucherRedemptionApi {
    val endPoint = "/api/voucherRedemptions"

    suspend fun getAllVoucherRedemptions(): PaginatedResponse<VoucherRedemption> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<VoucherRedemption>>()
    }

    suspend fun getVoucherRedemption(voucherRedemptionId: Int): VoucherRedemption {
        return Json.decodeFromString<VoucherRedemption>(HttpClient.client.get(urlString = getUrl("${endPoint}/$voucherRedemptionId")).body())
    }
    
    suspend fun createVoucherRedemption(voucherRedemption: VoucherRedemption): VoucherRedemption {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherRedemption)
        }.body()
    }

    suspend fun updateVoucherRedemption(voucherRedemptionId: Int, voucherRedemption: VoucherRedemption): VoucherRedemption {
        return HttpClient.client.put(getUrl("${endPoint}/$voucherRedemptionId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherRedemption)
        }.body()
    }

    suspend fun deleteVoucherRedemption(voucherRedemptionId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$voucherRedemptionId"))
            true
        } catch (e: Exception) {
            println("Error deleting voucherRedemption: ${e.message}")
            false
        }
    }
}