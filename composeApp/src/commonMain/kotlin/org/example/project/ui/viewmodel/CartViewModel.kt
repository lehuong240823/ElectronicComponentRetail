package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.CartRepository
import org.example.project.domain.model.Cart

class CartViewModel(private val cartRepository: CartRepository) {
    private val _cart = mutableStateOf<Cart?>(null)
    val cart: State<Cart?> get() = _cart

    private val _cartsList = mutableStateOf<List<Cart>>(emptyList())
    val cartsList: State<List<Cart>> get() = _cartsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createCart(cart: Cart) {
        val result = cartRepository.createCart(cart)
        if (result != null) {
            _operationStatus.value = "Cart Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Cart"
        }
    }

    suspend fun getCart(cartId: Int) {
        val result = cartRepository.getCart(cartId)
        if (result != null) {
            _cart.value = result
        } else {
            _operationStatus.value = "Cart Not Found"
        }
    }

    suspend fun updateCart(cartId: Int, cart: Cart) {
        val result = cartRepository.updateCart(cartId, cart)
        if (result != null) {
            _operationStatus.value = "Cart Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Cart"
        }
    }

    suspend fun deleteCart(cartId: Int) {
        val result = cartRepository.deleteCart(cartId)
        if (result) {
            _operationStatus.value = "Cart Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Cart"
        }
    }

    suspend fun getAllCarts() {
        val result = cartRepository.getAllCarts()
        _cartsList.value = result?.content ?: emptyList()
    }
}