package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.UserRepository
import org.example.project.domain.model.User

class UserViewModel(private val userRepository: UserRepository) {
    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> get() = _user

    private val _usersList = mutableStateOf<List<User>>(emptyList())
    val usersList: State<List<User>> get() = _usersList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createUser(user: User) {
        val result = userRepository.createUser(user)
        if (result != null) {
            _operationStatus.value = "User Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create User"
        }
    }

    suspend fun getUser(userId: Int) {
        val result = userRepository.getUser(userId)
        if (result != null) {
            _user.value = result
        } else {
            _operationStatus.value = "User Not Found"
        }
    }

    suspend fun updateUser(userId: Int, user: User) {
        val result = userRepository.updateUser(userId, user)
        if (result != null) {
            _operationStatus.value = "User Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update User"
        }
    }

    suspend fun deleteUser(userId: Int) {
        val result = userRepository.deleteUser(userId)
        if (result) {
            _operationStatus.value = "User Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete User"
        }
    }

    suspend fun getAllUsers() {
        val result = userRepository.getAllUsers()
        _usersList.value = result?.content ?: emptyList()
    }
}