package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.OrderItemRepository
import org.example.project.domain.model.OrderItem

class OrderItemViewModel(private val orderItemRepository: OrderItemRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdOrderItem = mutableStateOf<OrderItem?>(null)
    val createdOrderItem: State<OrderItem?> get() = _createdOrderItem
    
    private val _orderItem = mutableStateOf<OrderItem?>(null)
    val orderItem: State<OrderItem?> get() = _orderItem
    
    private val _updatedOrderItem = mutableStateOf<OrderItem?>(null)
    val updatedOrderItem: State<OrderItem?> get() = _updatedOrderItem

    private val _orderItemsList = mutableStateOf<List<OrderItem>>(emptyList())
    val orderItemsList: State<List<OrderItem>> get() = _orderItemsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createOrderItem(orderItem: OrderItem) {
        val result = orderItemRepository.createOrderItem(orderItem)
        if (result != null) {
            _createdOrderItem.value = result
            _operationStatus.value = "OrderItem Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create OrderItem"
        }
    }

    suspend fun getOrderItem(orderItemId: Int) {
        val result = orderItemRepository.getOrderItem(orderItemId)
        if (result != null) {
            _orderItem.value = result
            _operationStatus.value = "OrderItem Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get OrderItem or OrderItem Not Found"
        }
    }

    suspend fun updateOrderItem(orderItemId: Int, orderItem: OrderItem) {
        val result = orderItemRepository.updateOrderItem(orderItemId, orderItem)
        if (result != null) {
            _updatedOrderItem.value = result
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
        if (result != null) {
            _orderItemsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "OrderItem Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All OrderItem"
        }
    }

    suspend fun getOrderItemsByOrderId(currentPage: Int, order: Int) {
        val result = orderItemRepository.getOrderItemsByOrderId(currentPage, order)
        if (result != null) {
            _orderItemsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "OrderItem Get All Successfully"
        } else {
            _operationStatus.value = "OrderItem to Get All Account"
        }
    }

    suspend fun getOrderItemsByProductId(currentPage: Int, product: Int) {
        val result = orderItemRepository.getOrderItemsByProductId(currentPage, product)
        if (result != null) {
            _orderItemsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "OrderItem Get All Successfully"
        } else {
            _operationStatus.value = "OrderItem to Get All Account"
        }
    }
}