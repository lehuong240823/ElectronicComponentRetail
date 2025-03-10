package org.example.project.data.repository

import org.example.project.domain.model.Cart
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.CartApi

class CartRepository(private val cartApi: CartApi) {

    suspend fun getAllCarts(): PaginatedResponse<Cart>? {
        return try {
            cartApi.getAllCarts()
        } catch (e: Exception) {
            println("Error fetching carts: ${e.message}")
            null
        }
    }
    
    suspend fun getCart(userId: Int): Cart? {
        return try {
            cartApi.getCart(userId)
        } catch (e: Exception) {
            println("Error fetching cart: ${e.message}")
            null
        }
    }

    suspend fun createCart(cart: Cart): Cart? {
        return try {
            cartApi.createCart(cart)
        } catch (e: Exception) {
            println("Error creating cart: ${e.message}")
            null
        }
    }

    suspend fun updateCart(cartId: Int, cart: Cart): Cart? {
        return try {
            cartApi.updateCart(cartId, cart)
        } catch (e: Exception) {
            println("Error updating cart: ${e.message}")
            null
        }
    }

    suspend fun deleteCart(cartId: Int): Boolean {
        return try {
            cartApi.deleteCart(cartId)
        } catch (e: Exception) {
            println("Error deleting cart: ${e.message}")
            false
        }
    }
}
