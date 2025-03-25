package org.example.project.core.enums

enum class OrderStatusType(val ID: Byte, val value: String) {
    Pending(1, "Pending"),
    Processing(2, "Processing"),
    Shipped(3, "Shipped"),
    Delivered(4, "Delivered"),
    Cancelled(5, "Cancelled"),
    Failed(6, "Failed"),
    Returned(7, "Returned"),
    Refunded(8, "Refunded");

    override fun toString(): String {
        return value
    }

    fun getId(): Byte {
        return ID
    }
}