package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AccountStatusRepository
import org.example.project.domain.model.AccountStatus

class AccountStatusViewModel(private val accountStatusRepository: AccountStatusRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdAccountStatus = mutableStateOf<AccountStatus?>(null)
    val createdAccountStatus: State<AccountStatus?> get() = _createdAccountStatus
    
    private val _accountStatus = mutableStateOf<AccountStatus?>(null)
    val accountStatus: State<AccountStatus?> get() = _accountStatus
    
    private val _updatedAccountStatus = mutableStateOf<AccountStatus?>(null)
    val updatedAccountStatus: State<AccountStatus?> get() = _updatedAccountStatus

    private val _accountStatussList = mutableStateOf<List<AccountStatus>>(emptyList())
    val accountStatussList: State<List<AccountStatus>> get() = _accountStatussList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createAccountStatus(accountStatus: AccountStatus) {
        val result = accountStatusRepository.createAccountStatus(accountStatus)
        if (result != null) {
            _createdAccountStatus.value = result
            _operationStatus.value = "AccountStatus Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create AccountStatus"
        }
    }

    suspend fun getAccountStatus(accountStatusId: Int) {
        val result = accountStatusRepository.getAccountStatus(accountStatusId)
        if (result != null) {
            _accountStatus.value = result
            _operationStatus.value = "AccountStatus Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get AccountStatus or AccountStatus Not Found"
        }
    }

    suspend fun updateAccountStatus(accountStatusId: Int, accountStatus: AccountStatus) {
        val result = accountStatusRepository.updateAccountStatus(accountStatusId, accountStatus)
        if (result != null) {
            _updatedAccountStatus.value = result
            _operationStatus.value = "AccountStatus Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update AccountStatus"
        }
    }

    suspend fun deleteAccountStatus(accountStatusId: Int) {
        val result = accountStatusRepository.deleteAccountStatus(accountStatusId)
        if (result) {
            _operationStatus.value = "AccountStatus Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete AccountStatus"
        }
    }

    suspend fun getAllAccountStatuss(currentPage: Int) {
        val result = accountStatusRepository.getAllAccountStatuss(currentPage)
        if (result != null) {
            _accountStatussList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "AccountStatus Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All AccountStatus"
        }
    }
}