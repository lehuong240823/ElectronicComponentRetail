package org.example.project.data.repository

import org.example.project.domain.model.VoucherRedemption
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.VoucherRedemptionApi

class VoucherRedemptionRepository(private val voucherRedemptionApi: VoucherRedemptionApi) {

    suspend fun getAllVoucherRedemptions(currentPage: Int): PaginatedResponse<VoucherRedemption>? {
        return try {
            voucherRedemptionApi.getAllVoucherRedemptions(currentPage)
        } catch (e: Exception) {
            println("Error fetching voucherRedemptions: ${e.message}")
            null
        }
    }
    
    suspend fun getVoucherRedemption(userId: Int): VoucherRedemption? {
        return try {
            voucherRedemptionApi.getVoucherRedemption(userId)
        } catch (e: Exception) {
            println("Error fetching voucherRedemption: ${e.message}")
            null
        }
    }

    suspend fun createVoucherRedemption(voucherRedemption: VoucherRedemption): VoucherRedemption? {
        return try {
            voucherRedemptionApi.createVoucherRedemption(voucherRedemption)
        } catch (e: Exception) {
            println("Error creating voucherRedemption: ${e.message}")
            null
        }
    }

    suspend fun updateVoucherRedemption(voucherRedemptionId: Int, voucherRedemption: VoucherRedemption): VoucherRedemption? {
        return try {
            voucherRedemptionApi.updateVoucherRedemption(voucherRedemptionId, voucherRedemption)
        } catch (e: Exception) {
            println("Error updating voucherRedemption: ${e.message}")
            null
        }
    }

    suspend fun deleteVoucherRedemption(voucherRedemptionId: Int): Boolean {
        return try {
            voucherRedemptionApi.deleteVoucherRedemption(voucherRedemptionId)
        } catch (e: Exception) {
            println("Error deleting voucherRedemption: ${e.message}")
            false
        }
    }

    suspend fun getVoucherRedemptionsByVoucherId(currentPage: Int, voucher: Int): PaginatedResponse<VoucherRedemption>? {
        return try {
            voucherRedemptionApi.getVoucherRedemptionsByVoucherId(currentPage, voucher)
        } catch (e: Exception) {
            println("Error fetching voucherRedemptions: ${e.message}")
            null
        }
    }

    suspend fun getVoucherRedemptionsByUserId(currentPage: Int, user: Int): PaginatedResponse<VoucherRedemption>? {
        return try {
            voucherRedemptionApi.getVoucherRedemptionsByUserId(currentPage, user)
        } catch (e: Exception) {
            println("Error fetching voucherRedemptions: ${e.message}")
            null
        }
    }
}