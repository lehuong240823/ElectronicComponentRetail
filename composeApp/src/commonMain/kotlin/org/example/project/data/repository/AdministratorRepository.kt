package org.example.project.data.repository

import org.example.project.data.api.AdministratorApi
import org.example.project.domain.model.Administrator
import org.example.project.domain.model.PaginatedResponse

class AdministratorRepository(private val administratorApi: AdministratorApi) {

    suspend fun getAllAdministrators(currentPage: Int): PaginatedResponse<Administrator>? {
        return try {
            administratorApi.getAllAdministrators(currentPage)
        } catch (e: Exception) {
            println("Error fetching administrators: ${e.message}")
            null
        }
    }
    
    suspend fun getAdministrator(userId: Int): Administrator? {
        return try {
            administratorApi.getAdministrator(userId)
        } catch (e: Exception) {
            println("Error fetching administrator: ${e.message}")
            null
        }
    }

    suspend fun createAdministrator(administrator: Administrator): Administrator? {
        return try {
            administratorApi.createAdministrator(administrator)
        } catch (e: Exception) {
            println("Error creating administrator: ${e.message}")
            null
        }
    }

    suspend fun updateAdministrator(administratorId: Int, administrator: Administrator): Administrator? {
        return try {
            administratorApi.updateAdministrator(administratorId, administrator)
        } catch (e: Exception) {
            println("Error updating administrator: ${e.message}")
            null
        }
    }

    suspend fun deleteAdministrator(administratorId: Int): Boolean {
        return try {
            administratorApi.deleteAdministrator(administratorId)
        } catch (e: Exception) {
            println("Error deleting administrator: ${e.message}")
            false
        }
    }

    suspend fun getAdministratorsByJobPositionId(currentPage: Int, jobPosition: Byte): PaginatedResponse<Administrator>? {
        return try {
            administratorApi.getAdministratorsByJobPositionId(currentPage, jobPosition)
        } catch (e: Exception) {
            println("Error fetching administrators: ${e.message}")
            null
        }
    }

    suspend fun getAdministratorsByAccessLevelId(currentPage: Int, accessLevel: Byte): PaginatedResponse<Administrator>? {
        return try {
            administratorApi.getAdministratorsByAccessLevelId(currentPage, accessLevel)
        } catch (e: Exception) {
            println("Error fetching administrators: ${e.message}")
            null
        }
    }

    suspend fun getAdministratorByAccountId(account: Int): Administrator? {
        return try {
            administratorApi.getAdministratorByAccountId(account)
        } catch (e: Exception) {
            println("Error fetching administrators: ${e.message}")
            null
        }
    }
}