package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.CartRepository
import org.example.project.domain.model.Cart

class CartViewModel(private val cartRepository: CartRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdCart = mutableStateOf<Cart?>(null)
    val createdCart: State<Cart?> get() = _createdCart
    
    private val _cart = mutableStateOf<Cart?>(null)
    val cart: State<Cart?> get() = _cart
    
    private val _updatedCart = mutableStateOf<Cart?>(null)
    val updatedCart: State<Cart?> get() = _updatedCart

    private val _cartsList = mutableStateOf<List<Cart>>(emptyList())
    val cartsList: State<List<Cart>> get() = _cartsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createCart(cart: Cart) {
        val result = cartRepository.createCart(cart)
        if (result != null) {
            _createdCart.value = result
            _operationStatus.value = "Cart Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Cart"
        }
    }

    suspend fun getCart(cartId: Int) {
        val result = cartRepository.getCart(cartId)
        if (result != null) {
            _cart.value = result
            _operationStatus.value = "Cart Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Cart or Cart Not Found"
        }
    }

    suspend fun updateCart(cartId: Int, cart: Cart) {
        val result = cartRepository.updateCart(cartId, cart)
        if (result != null) {
            _updatedCart.value = result
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

    suspend fun getAllCarts(currentPage: Int) {
        val result = cartRepository.getAllCarts(currentPage)
        if (result != null) {
            _cartsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Cart Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Cart"
        }
    }

    suspend fun getCartsByUserId(currentPage: Int, user: Int) {
        val result = cartRepository.getCartsByUserId(currentPage, user)
        if (result != null) {
            _cartsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Cart Get All Successfully"
        } else {
            _operationStatus.value = "Cart to Get All Account"
        }
    }

    suspend fun getCartsByProductId(currentPage: Int, product: Int) {
        val result = cartRepository.getCartsByProductId(currentPage, product)
        if (result != null) {
            _cartsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Cart Get All Successfully"
        } else {
            _operationStatus.value = "Cart to Get All Account"
        }
    }
}