package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.OrderRepository
import org.example.project.domain.model.Order

class OrderViewModel(private val orderRepository: OrderRepository) {
    private val _order = mutableStateOf<Order?>(null)
    val order: State<Order?> get() = _order

    private val _ordersList = mutableStateOf<List<Order>>(emptyList())
    val ordersList: State<List<Order>> get() = _ordersList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createOrder(order: Order) {
        val result = orderRepository.createOrder(order)
        if (result != null) {
            _operationStatus.value = "Order Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Order"
        }
    }

    suspend fun getOrder(orderId: Int) {
        val result = orderRepository.getOrder(orderId)
        if (result != null) {
            _order.value = result
        } else {
            _operationStatus.value = "Order Not Found"
        }
    }

    suspend fun updateOrder(orderId: Int, order: Order) {
        val result = orderRepository.updateOrder(orderId, order)
        if (result != null) {
            _operationStatus.value = "Order Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Order"
        }
    }

    suspend fun deleteOrder(orderId: Int) {
        val result = orderRepository.deleteOrder(orderId)
        if (result) {
            _operationStatus.value = "Order Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Order"
        }
    }

    suspend fun getAllOrders() {
        val result = orderRepository.getAllOrders()
        _ordersList.value = result?.content ?: emptyList()
    }
}