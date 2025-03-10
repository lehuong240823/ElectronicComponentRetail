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
import org.example.project.domain.model.Cart

class CartApi {
    val endPoint = "/api/carts"

    suspend fun getAllCarts(): PaginatedResponse<Cart> {
        return HttpClient.client.get(urlString = getUrl(endPoint)).body<PaginatedResponse<Cart>>()
    }

    suspend fun getCart(cartId: Int): Cart {
        return Json.decodeFromString<Cart>(HttpClient.client.get(urlString = getUrl("${endPoint}/$cartId")).body())
    }
    
    suspend fun createCart(cart: Cart): Cart {
        return HttpClient.client.post(getUrl(endPoint)) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(cart)
        }.body()
    }

    suspend fun updateCart(cartId: Int, cart: Cart): Cart {
        return HttpClient.client.put(getUrl("${endPoint}/$cartId")) {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(cart)
        }.body()
    }

    suspend fun deleteCart(cartId: Int): Boolean {
        return try {
            HttpClient.client.delete(urlString = getUrl("${endPoint}/$cartId"))
            true
        } catch (e: Exception) {
            println("Error deleting cart: ${e.message}")
            false
        }
    }
}