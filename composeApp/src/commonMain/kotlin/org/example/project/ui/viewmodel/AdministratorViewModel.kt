package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AdministratorRepository
import org.example.project.domain.model.Administrator

class AdministratorViewModel(private val administratorRepository: AdministratorRepository) {
    private val _administrator = mutableStateOf<Administrator?>(null)
    val administrator: State<Administrator?> get() = _administrator

    private val _administratorsList = mutableStateOf<List<Administrator>>(emptyList())
    val administratorsList: State<List<Administrator>> get() = _administratorsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createAdministrator(administrator: Administrator) {
        val result = administratorRepository.createAdministrator(administrator)
        if (result != null) {
            _operationStatus.value = "Administrator Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Administrator"
        }
    }

    suspend fun getAdministrator(administratorId: Int) {
        val result = administratorRepository.getAdministrator(administratorId)
        if (result != null) {
            _administrator.value = result
        } else {
            _operationStatus.value = "Administrator Not Found"
        }
    }

    suspend fun updateAdministrator(administratorId: Int, administrator: Administrator) {
        val result = administratorRepository.updateAdministrator(administratorId, administrator)
        if (result != null) {
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

    suspend fun getAllAdministrators() {
        val result = administratorRepository.getAllAdministrators()
        _administratorsList.value = result?.content ?: emptyList()
    }
}