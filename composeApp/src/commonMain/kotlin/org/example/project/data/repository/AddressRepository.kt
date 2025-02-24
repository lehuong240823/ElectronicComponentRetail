package org.example.project.data.repository

import org.example.project.data.api.AddressApi
import org.example.project.domain.model.Address

class AddressRepository(private val addressApi: AddressApi) {

    suspend fun getAddress(userId: Int): Address? {
        return try {
            addressApi.getAddress(userId)
        } catch (e: Exception) {
            println("Error fetching address: \${e.message}")
            null
        }
    }

    suspend fun getAllAddresss(): List<Address>? {
        return try {
            addressApi.getAllAddresss()
        } catch (e: Exception) {
            println("Error fetching addresss: \${e.message}")
            null
        }
    }

    suspend fun createAddress(address: Address): Address? {
        return try {
            addressApi.createAddress(address)
        } catch (e: Exception) {
            println("Error creating address: \${e.message}")
            null
        }
    }

    suspend fun updateAddress(addressId: Int, address: Address): Address? {
        return try {
            addressApi.updateAddress(addressId, address)
        } catch (e: Exception) {
            println("Error updating address: \${e.message}")
            null
        }
    }

    suspend fun deleteAddress(addressId: Int): Boolean {
        return try {
            addressApi.deleteAddress(addressId)
        } catch (e: Exception) {
            println("Error deleting address: \${e.message}")
            false
        }
    }
}
