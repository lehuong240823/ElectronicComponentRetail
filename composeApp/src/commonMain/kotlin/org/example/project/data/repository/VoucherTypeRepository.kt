package org.example.project.data.repository

import org.example.project.domain.model.VoucherType
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.VoucherTypeApi

class VoucherTypeRepository(private val voucherTypeApi: VoucherTypeApi) {

    suspend fun getAllVoucherTypes(currentPage: Int): PaginatedResponse<VoucherType>? {
        return try {
            voucherTypeApi.getAllVoucherTypes(currentPage)
        } catch (e: Exception) {
            println("Error fetching voucherTypes: ${e.message}")
            null
        }
    }
    
    suspend fun getVoucherType(userId: Int): VoucherType? {
        return try {
            voucherTypeApi.getVoucherType(userId)
        } catch (e: Exception) {
            println("Error fetching voucherType: ${e.message}")
            null
        }
    }

    suspend fun createVoucherType(voucherType: VoucherType): VoucherType? {
        return try {
            voucherTypeApi.createVoucherType(voucherType)
        } catch (e: Exception) {
            println("Error creating voucherType: ${e.message}")
            null
        }
    }

    suspend fun updateVoucherType(voucherTypeId: Int, voucherType: VoucherType): VoucherType? {
        return try {
            voucherTypeApi.updateVoucherType(voucherTypeId, voucherType)
        } catch (e: Exception) {
            println("Error updating voucherType: ${e.message}")
            null
        }
    }

    suspend fun deleteVoucherType(voucherTypeId: Int): Boolean {
        return try {
            voucherTypeApi.deleteVoucherType(voucherTypeId)
        } catch (e: Exception) {
            println("Error deleting voucherType: ${e.message}")
            false
        }
    }
}