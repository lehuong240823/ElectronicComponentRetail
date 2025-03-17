package org.example.project.data.repository

import org.example.project.domain.model.UserAddress
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.UserAddressApi

class UserAddressRepository(private val userAddressApi: UserAddressApi) {

    suspend fun getAllUserAddresss(currentPage: Int): PaginatedResponse<UserAddress>? {
        return try {
            userAddressApi.getAllUserAddresss(currentPage)
        } catch (e: Exception) {
            println("Error fetching userAddresss: ${e.message}")
            null
        }
    }
    
    suspend fun getUserAddress(userId: Int): UserAddress? {
        return try {
            userAddressApi.getUserAddress(userId)
        } catch (e: Exception) {
            println("Error fetching userAddress: ${e.message}")
            null
        }
    }

    suspend fun createUserAddress(userAddress: UserAddress): UserAddress? {
        return try {
            userAddressApi.createUserAddress(userAddress)
        } catch (e: Exception) {
            println("Error creating userAddress: ${e.message}")
            null
        }
    }

    suspend fun updateUserAddress(userAddressId: Int, userAddress: UserAddress): UserAddress? {
        return try {
            userAddressApi.updateUserAddress(userAddressId, userAddress)
        } catch (e: Exception) {
            println("Error updating userAddress: ${e.message}")
            null
        }
    }

    suspend fun deleteUserAddress(userAddressId: Int): Boolean {
        return try {
            userAddressApi.deleteUserAddress(userAddressId)
        } catch (e: Exception) {
            println("Error deleting userAddress: ${e.message}")
            false
        }
    }
}