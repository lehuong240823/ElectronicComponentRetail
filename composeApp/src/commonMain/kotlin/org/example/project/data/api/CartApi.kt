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
import org.example.project.domain.model.Cart

class CartApi {
    val endPoint = "/api/carts"

    suspend fun getAllCarts(currentPage: Int): PaginatedResponse<Cart> {
        return HttpClient.client.get("$BASE_URL${endPoint}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getCart(cartId: Int): Cart {
        return HttpClient.client.get("$BASE_URL${endPoint}/${cartId}").body()
    }
    
    suspend fun createCart(cart: Cart): Cart {
        return HttpClient.client.post("$BASE_URL${endPoint}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(cart)
        }.body()
    }

    suspend fun updateCart(cartId: Int, cart: Cart): Cart {
        return HttpClient.client.put("$BASE_URL${endPoint}/${cartId}") {
            contentType(io.ktor.http.ContentType.Application.Json)
            setBody(cart)
        }.body()
    }

    suspend fun deleteCart(cartId: Int): Boolean {
        return try {
            HttpClient.client.delete("$BASE_URL${endPoint}/${cartId}")
            true
        } catch (e: Exception) {
            println("Error deleting cart: ${e.message}")
            false
        }
    }

    suspend fun getCartsByUserId(currentPage: Int, user: Int): PaginatedResponse<Cart> {
        return HttpClient.client.get("$BASE_URL${endPoint}/user/id/${user}?size=${getPageSize()}&page=${currentPage}").body()
    }

    suspend fun getCartsByProductId(currentPage: Int, product: Int): PaginatedResponse<Cart> {
        return HttpClient.client.get("$BASE_URL${endPoint}/product/id/${product}?size=${getPageSize()}&page=${currentPage}").body()
    }
}