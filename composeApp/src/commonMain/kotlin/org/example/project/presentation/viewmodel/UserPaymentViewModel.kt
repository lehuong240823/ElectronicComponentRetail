package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.UserPaymentRepository
import org.example.project.domain.model.UserPayment

class UserPaymentViewModel(private val userPaymentRepository: UserPaymentRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdUserPayment = mutableStateOf<UserPayment?>(null)
    val createdUserPayment: State<UserPayment?> get() = _createdUserPayment
    
    private val _userPayment = mutableStateOf<UserPayment?>(null)
    val userPayment: State<UserPayment?> get() = _userPayment
    
    private val _updatedUserPayment = mutableStateOf<UserPayment?>(null)
    val updatedUserPayment: State<UserPayment?> get() = _updatedUserPayment

    private val _userPaymentsList = mutableStateOf<List<UserPayment>>(emptyList())
    val userPaymentsList: State<List<UserPayment>> get() = _userPaymentsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createUserPayment(userPayment: UserPayment) {
        val result = userPaymentRepository.createUserPayment(userPayment)
        if (result != null) {
            _createdUserPayment.value = result
            _operationStatus.value = "UserPayment Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create UserPayment"
        }
    }

    suspend fun getUserPayment(userPaymentId: Int) {
        val result = userPaymentRepository.getUserPayment(userPaymentId)
        if (result != null) {
            _userPayment.value = result
            _operationStatus.value = "UserPayment Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get UserPayment or UserPayment Not Found"
        }
    }

    suspend fun updateUserPayment(userPaymentId: Int, userPayment: UserPayment) {
        val result = userPaymentRepository.updateUserPayment(userPaymentId, userPayment)
        if (result != null) {
            _updatedUserPayment.value = result
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
        if (result != null) {
            _userPaymentsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "UserPayment Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All UserPayment"
        }
    }

    suspend fun getUserPaymentsByPaymentMethodId(currentPage: Int, paymentMethod: Byte) {
        val result = userPaymentRepository.getUserPaymentsByPaymentMethodId(currentPage, paymentMethod)
        if (result != null) {
            _userPaymentsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "UserPayment Get All Successfully"
        } else {
            _operationStatus.value = "UserPayment to Get All Account"
        }
    }

    suspend fun getUserPaymentsByUserId(currentPage: Int, user: Int) {
        val result = userPaymentRepository.getUserPaymentsByUserId(currentPage, user)
        if (result != null) {
            _userPaymentsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "UserPayment Get All Successfully"
        } else {
            _operationStatus.value = "UserPayment to Get All Account"
        }
    }
}