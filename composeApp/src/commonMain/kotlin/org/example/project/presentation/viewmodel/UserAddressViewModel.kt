package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.UserAddressRepository
import org.example.project.domain.model.UserAddress

class UserAddressViewModel(private val userAddressRepository: UserAddressRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdUserAddress = mutableStateOf<UserAddress?>(null)
    val createdUserAddress: State<UserAddress?> get() = _createdUserAddress
    
    private val _userAddress = mutableStateOf<UserAddress?>(null)
    val userAddress: State<UserAddress?> get() = _userAddress
    
    private val _updatedUserAddress = mutableStateOf<UserAddress?>(null)
    val updatedUserAddress: State<UserAddress?> get() = _updatedUserAddress

    private val _userAddresssList = mutableStateOf<List<UserAddress>>(emptyList())
    val userAddresssList: State<List<UserAddress>> get() = _userAddresssList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createUserAddress(userAddress: UserAddress) {
        val result = userAddressRepository.createUserAddress(userAddress)
        if (result != null) {
            _createdUserAddress.value = result
            _operationStatus.value = "UserAddress Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create UserAddress"
        }
    }

    suspend fun getUserAddress(userAddressId: Int) {
        val result = userAddressRepository.getUserAddress(userAddressId)
        if (result != null) {
            _userAddress.value = result
            _operationStatus.value = "UserAddress Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get UserAddress or UserAddress Not Found"
        }
    }

    suspend fun updateUserAddress(userAddressId: Int, userAddress: UserAddress) {
        val result = userAddressRepository.updateUserAddress(userAddressId, userAddress)
        if (result != null) {
            _updatedUserAddress.value = result
            _operationStatus.value = "UserAddress Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update UserAddress"
        }
    }

    suspend fun deleteUserAddress(userAddressId: Int) {
        val result = userAddressRepository.deleteUserAddress(userAddressId)
        if (result) {
            _operationStatus.value = "UserAddress Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete UserAddress"
        }
    }

    suspend fun getAllUserAddresss(currentPage: Int) {
        val result = userAddressRepository.getAllUserAddresss(currentPage)
        if (result != null) {
            _userAddresssList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "UserAddress Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All UserAddress"
        }
    }

    suspend fun getUserAddresssByUserId(currentPage: Int, user: Int) {
        val result = userAddressRepository.getUserAddresssByUserId(currentPage, user)
        if (result != null) {
            _userAddresssList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "UserAddress Get All Successfully"
        } else {
            _operationStatus.value = "UserAddress to Get All Account"
        }
    }
}