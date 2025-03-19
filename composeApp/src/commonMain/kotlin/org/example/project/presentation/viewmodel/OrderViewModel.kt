package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.OrderRepository
import org.example.project.domain.model.Order

class OrderViewModel(private val orderRepository: OrderRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdOrder = mutableStateOf<Order?>(null)
    val createdOrder: State<Order?> get() = _createdOrder
    
    private val _order = mutableStateOf<Order?>(null)
    val order: State<Order?> get() = _order
    
    private val _updatedOrder = mutableStateOf<Order?>(null)
    val updatedOrder: State<Order?> get() = _updatedOrder

    private val _ordersList = mutableStateOf<List<Order>>(emptyList())
    val ordersList: State<List<Order>> get() = _ordersList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createOrder(order: Order) {
        val result = orderRepository.createOrder(order)
        if (result != null) {
            _createdOrder.value = result
            _operationStatus.value = "Order Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Order"
        }
    }

    suspend fun getOrder(orderId: Int) {
        val result = orderRepository.getOrder(orderId)
        if (result != null) {
            _order.value = result
            _operationStatus.value = "Order Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Order or Order Not Found"
        }
    }

    suspend fun updateOrder(orderId: Int, order: Order) {
        val result = orderRepository.updateOrder(orderId, order)
        if (result != null) {
            _updatedOrder.value = result
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

    suspend fun getAllOrders(currentPage: Int) {
        val result = orderRepository.getAllOrders(currentPage)
        if (result != null) {
            _ordersList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Order Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Order"
        }
    }

    suspend fun getOrdersByOrderStatusId(currentPage: Int, orderStatus: Byte) {
        val result = orderRepository.getOrdersByOrderStatusId(currentPage, orderStatus)
        if (result != null) {
            _ordersList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Order Get All Successfully"
        } else {
            _operationStatus.value = "Order to Get All Account"
        }
    }

    suspend fun getOrdersByUserId(currentPage: Int, user: Int) {
        val result = orderRepository.getOrdersByUserId(currentPage, user)
        if (result != null) {
            _ordersList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Order Get All Successfully"
        } else {
            _operationStatus.value = "Order to Get All Account"
        }
    }

    suspend fun getOrdersByVoucherId(currentPage: Int, voucher: Int) {
        val result = orderRepository.getOrdersByVoucherId(currentPage, voucher)
        if (result != null) {
            _ordersList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Order Get All Successfully"
        } else {
            _operationStatus.value = "Order to Get All Account"
        }
    }
}