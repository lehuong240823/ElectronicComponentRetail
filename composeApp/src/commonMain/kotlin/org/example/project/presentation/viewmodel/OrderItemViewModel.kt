package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.OrderItemRepository
import org.example.project.domain.model.OrderItem

class OrderItemViewModel(private val orderItemRepository: OrderItemRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _orderItem = mutableStateOf<OrderItem?>(null)
    val orderItem: State<OrderItem?> get() = _orderItem

    private val _orderItemsList = mutableStateOf<List<OrderItem>>(emptyList())
    val orderItemsList: State<List<OrderItem>> get() = _orderItemsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createOrderItem(orderItem: OrderItem) {
        val result = orderItemRepository.createOrderItem(orderItem)
        if (result != null) {
            _operationStatus.value = "OrderItem Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create OrderItem"
        }
    }

    suspend fun getOrderItem(orderItemId: Int) {
        val result = orderItemRepository.getOrderItem(orderItemId)
        if (result != null) {
            _orderItem.value = result
        } else {
            _operationStatus.value = "OrderItem Not Found"
        }
    }

    suspend fun updateOrderItem(orderItemId: Int, orderItem: OrderItem) {
        val result = orderItemRepository.updateOrderItem(orderItemId, orderItem)
        if (result != null) {
            _operationStatus.value = "OrderItem Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update OrderItem"
        }
    }

    suspend fun deleteOrderItem(orderItemId: Int) {
        val result = orderItemRepository.deleteOrderItem(orderItemId)
        if (result) {
            _operationStatus.value = "OrderItem Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete OrderItem"
        }
    }

    suspend fun getAllOrderItems(currentPage: Int) {
        val result = orderItemRepository.getAllOrderItems(currentPage)
        _orderItemsList.value = result?.content ?: emptyList()
        _totalPage.value = result?.totalPages ?: 0
    }
}