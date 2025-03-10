package org.example.project.data.repository

import org.example.project.domain.model.AccountRole
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.AccountRoleApi

class AccountRoleRepository(private val accountRoleApi: AccountRoleApi) {

    suspend fun getAllAccountRoles(): PaginatedResponse<AccountRole>? {
        return try {
            accountRoleApi.getAllAccountRoles()
        } catch (e: Exception) {
            println("Error fetching accountRoles: ${e.message}")
            null
        }
    }
    
    suspend fun getAccountRole(userId: Int): AccountRole? {
        return try {
            accountRoleApi.getAccountRole(userId)
        } catch (e: Exception) {
            println("Error fetching accountRole: ${e.message}")
            null
        }
    }

    suspend fun createAccountRole(accountRole: AccountRole): AccountRole? {
        return try {
            accountRoleApi.createAccountRole(accountRole)
        } catch (e: Exception) {
            println("Error creating accountRole: ${e.message}")
            null
        }
    }

    suspend fun updateAccountRole(accountRoleId: Int, accountRole: AccountRole): AccountRole? {
        return try {
            accountRoleApi.updateAccountRole(accountRoleId, accountRole)
        } catch (e: Exception) {
            println("Error updating accountRole: ${e.message}")
            null
        }
    }

    suspend fun deleteAccountRole(accountRoleId: Int): Boolean {
        return try {
            accountRoleApi.deleteAccountRole(accountRoleId)
        } catch (e: Exception) {
            println("Error deleting accountRole: ${e.message}")
            false
        }
    }
}
