package org.example.project.data.repository

import org.example.project.domain.model.AccountStatus
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.AccountStatusApi

class AccountStatusRepository(private val accountStatusApi: AccountStatusApi) {

    suspend fun getAllAccountStatuss(): PaginatedResponse<AccountStatus>? {
        return try {
            accountStatusApi.getAllAccountStatuss()
        } catch (e: Exception) {
            println("Error fetching accountStatuss: ${e.message}")
            null
        }
    }
    
    suspend fun getAccountStatus(userId: Int): AccountStatus? {
        return try {
            accountStatusApi.getAccountStatus(userId)
        } catch (e: Exception) {
            println("Error fetching accountStatus: ${e.message}")
            null
        }
    }

    suspend fun createAccountStatus(accountStatus: AccountStatus): AccountStatus? {
        return try {
            accountStatusApi.createAccountStatus(accountStatus)
        } catch (e: Exception) {
            println("Error creating accountStatus: ${e.message}")
            null
        }
    }

    suspend fun updateAccountStatus(accountStatusId: Int, accountStatus: AccountStatus): AccountStatus? {
        return try {
            accountStatusApi.updateAccountStatus(accountStatusId, accountStatus)
        } catch (e: Exception) {
            println("Error updating accountStatus: ${e.message}")
            null
        }
    }

    suspend fun deleteAccountStatus(accountStatusId: Int): Boolean {
        return try {
            accountStatusApi.deleteAccountStatus(accountStatusId)
        } catch (e: Exception) {
            println("Error deleting accountStatus: ${e.message}")
            false
        }
    }
}
