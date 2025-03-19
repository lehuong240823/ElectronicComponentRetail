package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AccessLevelRepository
import org.example.project.domain.model.AccessLevel

class AccessLevelViewModel(private val accessLevelRepository: AccessLevelRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdAccessLevel = mutableStateOf<AccessLevel?>(null)
    val createdAccessLevel: State<AccessLevel?> get() = _createdAccessLevel
    
    private val _accessLevel = mutableStateOf<AccessLevel?>(null)
    val accessLevel: State<AccessLevel?> get() = _accessLevel
    
    private val _updatedAccessLevel = mutableStateOf<AccessLevel?>(null)
    val updatedAccessLevel: State<AccessLevel?> get() = _updatedAccessLevel

    private val _accessLevelsList = mutableStateOf<List<AccessLevel>>(emptyList())
    val accessLevelsList: State<List<AccessLevel>> get() = _accessLevelsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createAccessLevel(accessLevel: AccessLevel) {
        val result = accessLevelRepository.createAccessLevel(accessLevel)
        if (result != null) {
            _createdAccessLevel.value = result
            _operationStatus.value = "AccessLevel Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create AccessLevel"
        }
    }

    suspend fun getAccessLevel(accessLevelId: Int) {
        val result = accessLevelRepository.getAccessLevel(accessLevelId)
        if (result != null) {
            _accessLevel.value = result
            _operationStatus.value = "AccessLevel Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get AccessLevel or AccessLevel Not Found"
        }
    }

    suspend fun updateAccessLevel(accessLevelId: Int, accessLevel: AccessLevel) {
        val result = accessLevelRepository.updateAccessLevel(accessLevelId, accessLevel)
        if (result != null) {
            _updatedAccessLevel.value = result
            _operationStatus.value = "AccessLevel Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update AccessLevel"
        }
    }

    suspend fun deleteAccessLevel(accessLevelId: Int) {
        val result = accessLevelRepository.deleteAccessLevel(accessLevelId)
        if (result) {
            _operationStatus.value = "AccessLevel Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete AccessLevel"
        }
    }

    suspend fun getAllAccessLevels(currentPage: Int) {
        val result = accessLevelRepository.getAllAccessLevels(currentPage)
        if (result != null) {
            _accessLevelsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "AccessLevel Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All AccessLevel"
        }
    }
}