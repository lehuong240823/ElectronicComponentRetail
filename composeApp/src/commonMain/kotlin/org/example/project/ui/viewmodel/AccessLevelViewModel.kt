package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AccessLevelRepository
import org.example.project.domain.model.AccessLevel

class AccessLevelViewModel(private val accessLevelRepository: AccessLevelRepository) {
    private val _accessLevel = mutableStateOf<AccessLevel?>(null)
    val accessLevel: State<AccessLevel?> get() = _accessLevel

    private val _accessLevelsList = mutableStateOf<List<AccessLevel>>(emptyList())
    val accessLevelsList: State<List<AccessLevel>> get() = _accessLevelsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createAccessLevel(accessLevel: AccessLevel) {
        val result = accessLevelRepository.createAccessLevel(accessLevel)
        if (result != null) {
            _operationStatus.value = "AccessLevel Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create AccessLevel"
        }
    }

    suspend fun getAccessLevel(accessLevelId: Int) {
        val result = accessLevelRepository.getAccessLevel(accessLevelId)
        if (result != null) {
            _accessLevel.value = result
        } else {
            _operationStatus.value = "AccessLevel Not Found"
        }
    }

    suspend fun updateAccessLevel(accessLevelId: Int, accessLevel: AccessLevel) {
        val result = accessLevelRepository.updateAccessLevel(accessLevelId, accessLevel)
        if (result != null) {
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

    suspend fun getAllAccessLevels() {
        val result = accessLevelRepository.getAllAccessLevels()
        _accessLevelsList.value = result?.content ?: emptyList()
    }
}