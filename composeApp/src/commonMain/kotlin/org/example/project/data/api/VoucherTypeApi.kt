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
import org.example.project.domain.model.VoucherType

class VoucherTypeApi {
    val endPoint = "/api/voucherTypes"

    suspend fun getAllVoucherTypes(): PaginatedResponse<VoucherType> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<VoucherType>>()
    }

    suspend fun getVoucherType(voucherTypeId: Int): VoucherType {
        return Json.decodeFromString<VoucherType>(HttpClient.client.get(urlString = getUrl("${endPoint}/$voucherTypeId")).body())
    }
    
    suspend fun createVoucherType(voucherType: VoucherType): VoucherType {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherType)
        }.body()
    }

    suspend fun updateVoucherType(voucherTypeId: Int, voucherType: VoucherType): VoucherType {
        return HttpClient.client.put(getUrl("${endPoint}/$voucherTypeId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(voucherType)
        }.body()
    }

    suspend fun deleteVoucherType(voucherTypeId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$voucherTypeId"))
            true
        } catch (e: Exception) {
            println("Error deleting voucherType: ${e.message}")
            false
        }
    }
}