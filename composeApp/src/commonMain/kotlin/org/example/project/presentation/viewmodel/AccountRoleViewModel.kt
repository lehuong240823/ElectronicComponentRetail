package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AccountRoleRepository
import org.example.project.domain.model.AccountRole

class AccountRoleViewModel(private val accountRoleRepository: AccountRoleRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdAccountRole = mutableStateOf<AccountRole?>(null)
    val createdAccountRole: State<AccountRole?> get() = _createdAccountRole
    
    private val _accountRole = mutableStateOf<AccountRole?>(null)
    val accountRole: State<AccountRole?> get() = _accountRole
    
    private val _updatedAccountRole = mutableStateOf<AccountRole?>(null)
    val updatedAccountRole: State<AccountRole?> get() = _updatedAccountRole

    private val _accountRolesList = mutableStateOf<List<AccountRole>>(emptyList())
    val accountRolesList: State<List<AccountRole>> get() = _accountRolesList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createAccountRole(accountRole: AccountRole) {
        val result = accountRoleRepository.createAccountRole(accountRole)
        if (result != null) {
            _createdAccountRole.value = result
            _operationStatus.value = "AccountRole Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create AccountRole"
        }
    }

    suspend fun getAccountRole(accountRoleId: Int) {
        val result = accountRoleRepository.getAccountRole(accountRoleId)
        if (result != null) {
            _accountRole.value = result
            _operationStatus.value = "AccountRole Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get AccountRole or AccountRole Not Found"
        }
    }

    suspend fun updateAccountRole(accountRoleId: Int, accountRole: AccountRole) {
        val result = accountRoleRepository.updateAccountRole(accountRoleId, accountRole)
        if (result != null) {
            _updatedAccountRole.value = result
            _operationStatus.value = "AccountRole Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update AccountRole"
        }
    }

    suspend fun deleteAccountRole(accountRoleId: Int) {
        val result = accountRoleRepository.deleteAccountRole(accountRoleId)
        if (result) {
            _operationStatus.value = "AccountRole Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete AccountRole"
        }
    }

    suspend fun getAllAccountRoles(currentPage: Int) {
        val result = accountRoleRepository.getAllAccountRoles(currentPage)
        if (result != null) {
            _accountRolesList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "AccountRole Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All AccountRole"
        }
    }
}