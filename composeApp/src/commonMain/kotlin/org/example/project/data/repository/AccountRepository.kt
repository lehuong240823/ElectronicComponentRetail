package org.example.project.data.repository

import org.example.project.domain.model.Account
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.AccountApi

class AccountRepository(private val accountApi: AccountApi) {

    suspend fun getAllAccounts(currentPage: Int): PaginatedResponse<Account>? {
        return try {
            accountApi.getAllAccounts(currentPage)
        } catch (e: Exception) {
            println("Error fetching accounts: ${e.message}")
            null
        }
    }
    
    suspend fun getAccount(userId: Int): Account? {
        return try {
            accountApi.getAccount(userId)
        } catch (e: Exception) {
            println("Error fetching account: ${e.message}")
            null
        }
    }

    suspend fun createAccount(account: Account): Account? {
        return try {
            accountApi.createAccount(account)
        } catch (e: Exception) {
            println("Error creating account: ${e.message}")
            null
        }
    }

    suspend fun updateAccount(accountId: Int, account: Account): Account? {
        return try {
            accountApi.updateAccount(accountId, account)
        } catch (e: Exception) {
            println("Error updating account: ${e.message}")
            null
        }
    }

    suspend fun deleteAccount(accountId: Int): Boolean {
        return try {
            accountApi.deleteAccount(accountId)
        } catch (e: Exception) {
            println("Error deleting account: ${e.message}")
            false
        }
    }

    suspend fun getAccountsByEmail(currentPage: Int, email: String): PaginatedResponse<Account>? {
        return try {
            accountApi.getAccountsByEmail(currentPage, email)
        } catch (e: Exception) {
            println("Error fetching account by email: ${e.message}")
            null
        }
    }

    suspend fun getAccountsByAccountRoleId(currentPage: Int, accountRole: Byte): PaginatedResponse<Account>? {
        return try {
            accountApi.getAccountsByAccountRoleId(currentPage, accountRole)
        } catch (e: Exception) {
            println("Error fetching accounts: ${e.message}")
            null
        }
    }

    suspend fun getAccountsByAccountStatusId(currentPage: Int, accountStatus: Byte): PaginatedResponse<Account>? {
        return try {
            accountApi.getAccountsByAccountStatusId(currentPage, accountStatus)
        } catch (e: Exception) {
            println("Error fetching accounts: ${e.message}")
            null
        }
    }
}