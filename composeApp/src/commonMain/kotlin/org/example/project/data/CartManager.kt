package org.example.project.data

import androidx.compose.runtime.mutableStateListOf
import org.example.project.model.CartItem
import org.example.project.model.Product
import org.example.project.model.Voucher

object CartManager {
    private val _items = mutableStateListOf<CartItem>()
    val items: List<CartItem> get() = _items

    val totalItems: Int get() = _items.sumOf { it.quantity }
    
    val subtotal: Double get() = _items.sumOf { it.product.price * it.quantity }
    
    val discount: Double get() = _items.sumOf { 
        val voucher = it.appliedVoucher
        if (voucher != null) it.product.price * it.quantity * voucher.value else 0.0 
    }
    
    val total: Double get() = subtotal - discount

    fun addItem(product: Product) {
        val existingItem = _items.find { it.product.id == product.id }
        if (existingItem != null) {
            val index = _items.indexOf(existingItem)
            _items[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            _items.add(CartItem(product, 1))
        }
    }

    fun removeItem(productId: String) {
        _items.removeAll { it.product.id == productId }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeItem(productId)
            return
        }
        
        val existingItem = _items.find { it.product.id == productId } ?: return
        val index = _items.indexOf(existingItem)
        _items[index] = existingItem.copy(quantity = quantity)
    }

    fun applyVoucher(productId: String, voucher: Voucher?) {
        val existingItem = _items.find { it.product.id == productId } ?: return
        val index = _items.indexOf(existingItem)
        _items[index] = existingItem.copy(appliedVoucher = voucher)
    }

    fun clearCart() {
        _items.clear()
    }
}
