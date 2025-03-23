package org.example.project.model

data class CartItem(
    val product: Product,
    val quantity: Int,
    val appliedVoucher: Voucher? = null
) {
    val totalPrice: Double
        get() {
            val discountMultiplier = appliedVoucher?.value ?: 0.0
            return product.price * quantity * (1.0 - discountMultiplier)
        }
}
