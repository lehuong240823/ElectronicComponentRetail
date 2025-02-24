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
import org.example.project.domain.model.Address

class AddressApi {
    val endPoint = "/api/addresss"

    suspend fun createAddress(address: Address): Address {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(address)
        }.body()
    }

    suspend fun getAllAddresss(): List<Address> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<List<Address>>()
    }

    suspend fun getAddress(addressId: Int): Address {
        return Json.decodeFromString<Address>(HttpClient.client.get(urlString = getUrl("\$endPt/$addressId")).body())
    }

    suspend fun updateAddress(addressId: Int, address: Address): Address {
        return HttpClient.client.put(getUrl("\$endPt/$addressId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(address)
        }.body()
    }

    suspend fun deleteAddress(addressId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("\$endPt/$addressId"))
            true
        } catch (e: Exception) {
            println("Error deleting address: errorMessage")
            false
        }
    }
}