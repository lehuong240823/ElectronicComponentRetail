package org.example.project.data.repository

import org.example.project.domain.model.ProductStatus
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.ProductStatusApi

class ProductStatusRepository(private val productStatusApi: ProductStatusApi) {

    suspend fun getAllProductStatuss(currentPage: Int): PaginatedResponse<ProductStatus>? {
        return try {
            productStatusApi.getAllProductStatuss(currentPage)
        } catch (e: Exception) {
            println("Error fetching productStatuss: ${e.message}")
            null
        }
    }
    
    suspend fun getProductStatus(userId: Int): ProductStatus? {
        return try {
            productStatusApi.getProductStatus(userId)
        } catch (e: Exception) {
            println("Error fetching productStatus: ${e.message}")
            null
        }
    }

    suspend fun createProductStatus(productStatus: ProductStatus): ProductStatus? {
        return try {
            productStatusApi.createProductStatus(productStatus)
        } catch (e: Exception) {
            println("Error creating productStatus: ${e.message}")
            null
        }
    }

    suspend fun updateProductStatus(productStatusId: Int, productStatus: ProductStatus): ProductStatus? {
        return try {
            productStatusApi.updateProductStatus(productStatusId, productStatus)
        } catch (e: Exception) {
            println("Error updating productStatus: ${e.message}")
            null
        }
    }

    suspend fun deleteProductStatus(productStatusId: Int): Boolean {
        return try {
            productStatusApi.deleteProductStatus(productStatusId)
        } catch (e: Exception) {
            println("Error deleting productStatus: ${e.message}")
            false
        }
    }
}