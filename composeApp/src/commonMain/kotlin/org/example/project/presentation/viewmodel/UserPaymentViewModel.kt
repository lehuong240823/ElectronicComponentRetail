package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.UserPaymentRepository
import org.example.project.domain.model.UserPayment

class UserPaymentViewModel(private val userPaymentRepository: UserPaymentRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _userPayment = mutableStateOf<UserPayment?>(null)
    val userPayment: State<UserPayment?> get() = _userPayment

    private val _userPaymentsList = mutableStateOf<List<UserPayment>>(emptyList())
    val userPaymentsList: State<List<UserPayment>> get() = _userPaymentsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createUserPayment(userPayment: UserPayment) {
        val result = userPaymentRepository.createUserPayment(userPayment)
        if (result != null) {
            _operationStatus.value = "UserPayment Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create UserPayment"
        }
    }

    suspend fun getUserPayment(userPaymentId: Int) {
        val result = userPaymentRepository.getUserPayment(userPaymentId)
        if (result != null) {
            _userPayment.value = result
        } else {
            _operationStatus.value = "UserPayment Not Found"
        }
    }

    suspend fun updateUserPayment(userPaymentId: Int, userPayment: UserPayment) {
        val result = userPaymentRepository.updateUserPayment(userPaymentId, userPayment)
        if (result != null) {
            _operationStatus.value = "UserPayment Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update UserPayment"
        }
    }

    suspend fun deleteUserPayment(userPaymentId: Int) {
        val result = userPaymentRepository.deleteUserPayment(userPaymentId)
        if (result) {
            _operationStatus.value = "UserPayment Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete UserPayment"
        }
    }

    suspend fun getAllUserPayments(currentPage: Int) {
        val result = userPaymentRepository.getAllUserPayments(currentPage)
        _userPaymentsList.value = result?.content ?: emptyList()
        _totalPage.value = result?.totalPages ?: 0
    }
}