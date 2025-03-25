package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AdministratorRepository
import org.example.project.domain.model.Administrator

class AdministratorViewModel(private val administratorRepository: AdministratorRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdAdministrator = mutableStateOf<Administrator?>(null)
    val createdAdministrator: State<Administrator?> get() = _createdAdministrator
    
    private val _administrator = mutableStateOf<Administrator?>(null)
    val administrator: State<Administrator?> get() = _administrator
    
    private val _updatedAdministrator = mutableStateOf<Administrator?>(null)
    val updatedAdministrator: State<Administrator?> get() = _updatedAdministrator

    private val _administratorsList = mutableStateOf<List<Administrator>>(emptyList())
    val administratorsList: State<List<Administrator>> get() = _administratorsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createAdministrator(administrator: Administrator) {
        val result = administratorRepository.createAdministrator(administrator)
        if (result != null) {
            _createdAdministrator.value = result
            _operationStatus.value = "Administrator Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Administrator"
        }
    }

    suspend fun getAdministrator(administratorId: Int) {
        val result = administratorRepository.getAdministrator(administratorId)
        if (result != null) {
            _administrator.value = result
            _operationStatus.value = "Administrator Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Administrator or Administrator Not Found"
        }
    }

    suspend fun updateAdministrator(administratorId: Int, administrator: Administrator) {
        val result = administratorRepository.updateAdministrator(administratorId, administrator)
        if (result != null) {
            _updatedAdministrator.value = result
            _operationStatus.value = "Administrator Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Administrator"
        }
    }

    suspend fun deleteAdministrator(administratorId: Int) {
        val result = administratorRepository.deleteAdministrator(administratorId)
        if (result) {
            _operationStatus.value = "Administrator Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Administrator"
        }
    }

    suspend fun getAllAdministrators(currentPage: Int) {
        val result = administratorRepository.getAllAdministrators(currentPage)
        if (result != null) {
            _administratorsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Administrator Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Administrator"
        }
    }

    suspend fun getAdministratorsByJobPositionId(currentPage: Int, jobPosition: Byte) {
        val result = administratorRepository.getAdministratorsByJobPositionId(currentPage, jobPosition)
        if (result != null) {
            _administratorsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Administrator Get All Successfully"
        } else {
            _operationStatus.value = "Failed"
        }
    }

    suspend fun getAdministratorsByAccessLevelId(currentPage: Int, accessLevel: Byte) {
        val result = administratorRepository.getAdministratorsByAccessLevelId(currentPage, accessLevel)
        if (result != null) {
            _administratorsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Administrator Get All Successfully"
        } else {
            _operationStatus.value = "Failed"
        }
    }

    suspend fun getAdministratorByAccountId(account: Int) {
        val result = administratorRepository.getAdministratorByAccountId(account)
        if (result != null) {
            _administrator.value = result
            _operationStatus.value = "Successfully"
        } else {
            _operationStatus.value = "Failed"
        }
    }
}