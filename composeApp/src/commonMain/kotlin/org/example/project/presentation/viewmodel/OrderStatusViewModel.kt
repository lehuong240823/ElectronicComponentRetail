package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.OrderStatusRepository
import org.example.project.domain.model.OrderStatus

class OrderStatusViewModel(private val orderStatusRepository: OrderStatusRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdOrderStatus = mutableStateOf<OrderStatus?>(null)
    val createdOrderStatus: State<OrderStatus?> get() = _createdOrderStatus
    
    private val _orderStatus = mutableStateOf<OrderStatus?>(null)
    val orderStatus: State<OrderStatus?> get() = _orderStatus
    
    private val _updatedOrderStatus = mutableStateOf<OrderStatus?>(null)
    val updatedOrderStatus: State<OrderStatus?> get() = _updatedOrderStatus

    private val _orderStatussList = mutableStateOf<List<OrderStatus>>(emptyList())
    val orderStatussList: State<List<OrderStatus>> get() = _orderStatussList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createOrderStatus(orderStatus: OrderStatus) {
        val result = orderStatusRepository.createOrderStatus(orderStatus)
        if (result != null) {
            _createdOrderStatus.value = result
            _operationStatus.value = "OrderStatus Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create OrderStatus"
        }
    }

    suspend fun getOrderStatus(orderStatusId: Int) {
        val result = orderStatusRepository.getOrderStatus(orderStatusId)
        if (result != null) {
            _orderStatus.value = result
            _operationStatus.value = "OrderStatus Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get OrderStatus or OrderStatus Not Found"
        }
    }

    suspend fun updateOrderStatus(orderStatusId: Int, orderStatus: OrderStatus) {
        val result = orderStatusRepository.updateOrderStatus(orderStatusId, orderStatus)
        if (result != null) {
            _updatedOrderStatus.value = result
            _operationStatus.value = "OrderStatus Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update OrderStatus"
        }
    }

    suspend fun deleteOrderStatus(orderStatusId: Int) {
        val result = orderStatusRepository.deleteOrderStatus(orderStatusId)
        if (result) {
            _operationStatus.value = "OrderStatus Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete OrderStatus"
        }
    }

    suspend fun getAllOrderStatuss(currentPage: Int) {
        val result = orderStatusRepository.getAllOrderStatuss(currentPage)
        if (result != null) {
            _orderStatussList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "OrderStatus Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All OrderStatus"
        }
    }
}