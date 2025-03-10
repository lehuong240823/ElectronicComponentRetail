package org.example.project.data.repository

import org.example.project.domain.model.Provider
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.ProviderApi

class ProviderRepository(private val providerApi: ProviderApi) {

    suspend fun getAllProviders(): PaginatedResponse<Provider>? {
        return try {
            providerApi.getAllProviders()
        } catch (e: Exception) {
            println("Error fetching providers: ${e.message}")
            null
        }
    }
    
    suspend fun getProvider(userId: Int): Provider? {
        return try {
            providerApi.getProvider(userId)
        } catch (e: Exception) {
            println("Error fetching provider: ${e.message}")
            null
        }
    }

    suspend fun createProvider(provider: Provider): Provider? {
        return try {
            providerApi.createProvider(provider)
        } catch (e: Exception) {
            println("Error creating provider: ${e.message}")
            null
        }
    }

    suspend fun updateProvider(providerId: Int, provider: Provider): Provider? {
        return try {
            providerApi.updateProvider(providerId, provider)
        } catch (e: Exception) {
            println("Error updating provider: ${e.message}")
            null
        }
    }

    suspend fun deleteProvider(providerId: Int): Boolean {
        return try {
            providerApi.deleteProvider(providerId)
        } catch (e: Exception) {
            println("Error deleting provider: ${e.message}")
            false
        }
    }
}
