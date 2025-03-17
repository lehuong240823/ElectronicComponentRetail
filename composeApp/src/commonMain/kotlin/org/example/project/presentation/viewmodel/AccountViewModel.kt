package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.AccountRepository
import org.example.project.domain.model.Account

class AccountViewModel(private val accountRepository: AccountRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _account = mutableStateOf<Account?>(null)
    val account: State<Account?> get() = _account

    private val _accountsList = mutableStateOf<List<Account>>(emptyList())
    val accountsList: State<List<Account>> get() = _accountsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createAccount(account: Account) {
        val result = accountRepository.createAccount(account)
        if (result != null) {
            _operationStatus.value = "Account Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Account"
        }
    }

    suspend fun getAccount(accountId: Int) {
        val result = accountRepository.getAccount(accountId)
        if (result != null) {
            _account.value = result
        } else {
            _operationStatus.value = "Account Not Found"
        }
    }

    suspend fun updateAccount(accountId: Int, account: Account) {
        val result = accountRepository.updateAccount(accountId, account)
        if (result != null) {
            _operationStatus.value = "Account Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Account"
        }
    }

    suspend fun deleteAccount(accountId: Int) {
        val result = accountRepository.deleteAccount(accountId)
        if (result) {
            _operationStatus.value = "Account Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Account"
        }
    }

    suspend fun getAllAccounts(currentPage: Int) {
        val result = accountRepository.getAllAccounts(currentPage)
        _accountsList.value = result?.content ?: emptyList()
        _totalPage.value = result?.totalPages ?: 0
    }

    suspend fun getAccountByEmail(email: String) {
        val result = accountRepository.getAccountByEmail(email)
        if (result != null) {
            _account.value = result
        } else {
            _operationStatus.value = "Account Not Found"
        }
    }
}