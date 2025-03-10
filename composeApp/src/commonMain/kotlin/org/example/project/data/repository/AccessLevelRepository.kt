package org.example.project.data.repository

import org.example.project.domain.model.AccessLevel
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.AccessLevelApi

class AccessLevelRepository(private val accessLevelApi: AccessLevelApi) {

    suspend fun getAllAccessLevels(): PaginatedResponse<AccessLevel>? {
        return try {
            accessLevelApi.getAllAccessLevels()
        } catch (e: Exception) {
            println("Error fetching accessLevels: ${e.message}")
            null
        }
    }
    
    suspend fun getAccessLevel(userId: Int): AccessLevel? {
        return try {
            accessLevelApi.getAccessLevel(userId)
        } catch (e: Exception) {
            println("Error fetching accessLevel: ${e.message}")
            null
        }
    }

    suspend fun createAccessLevel(accessLevel: AccessLevel): AccessLevel? {
        return try {
            accessLevelApi.createAccessLevel(accessLevel)
        } catch (e: Exception) {
            println("Error creating accessLevel: ${e.message}")
            null
        }
    }

    suspend fun updateAccessLevel(accessLevelId: Int, accessLevel: AccessLevel): AccessLevel? {
        return try {
            accessLevelApi.updateAccessLevel(accessLevelId, accessLevel)
        } catch (e: Exception) {
            println("Error updating accessLevel: ${e.message}")
            null
        }
    }

    suspend fun deleteAccessLevel(accessLevelId: Int): Boolean {
        return try {
            accessLevelApi.deleteAccessLevel(accessLevelId)
        } catch (e: Exception) {
            println("Error deleting accessLevel: ${e.message}")
            false
        }
    }
}
