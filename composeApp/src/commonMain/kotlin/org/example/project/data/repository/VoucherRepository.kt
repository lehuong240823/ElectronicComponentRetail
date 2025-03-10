package org.example.project.data.repository

import org.example.project.domain.model.Voucher
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.VoucherApi

class VoucherRepository(private val voucherApi: VoucherApi) {

    suspend fun getAllVouchers(): PaginatedResponse<Voucher>? {
        return try {
            voucherApi.getAllVouchers()
        } catch (e: Exception) {
            println("Error fetching vouchers: ${e.message}")
            null
        }
    }
    
    suspend fun getVoucher(userId: Int): Voucher? {
        return try {
            voucherApi.getVoucher(userId)
        } catch (e: Exception) {
            println("Error fetching voucher: ${e.message}")
            null
        }
    }

    suspend fun createVoucher(voucher: Voucher): Voucher? {
        return try {
            voucherApi.createVoucher(voucher)
        } catch (e: Exception) {
            println("Error creating voucher: ${e.message}")
            null
        }
    }

    suspend fun updateVoucher(voucherId: Int, voucher: Voucher): Voucher? {
        return try {
            voucherApi.updateVoucher(voucherId, voucher)
        } catch (e: Exception) {
            println("Error updating voucher: ${e.message}")
            null
        }
    }

    suspend fun deleteVoucher(voucherId: Int): Boolean {
        return try {
            voucherApi.deleteVoucher(voucherId)
        } catch (e: Exception) {
            println("Error deleting voucher: ${e.message}")
            false
        }
    }
}
