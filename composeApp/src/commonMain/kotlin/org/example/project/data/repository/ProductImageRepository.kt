package org.example.project.data.repository

import org.example.project.domain.model.ProductImage
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.ProductImageApi

class ProductImageRepository(private val productImageApi: ProductImageApi) {

    suspend fun getAllProductImages(): PaginatedResponse<ProductImage>? {
        return try {
            productImageApi.getAllProductImages()
        } catch (e: Exception) {
            println("Error fetching productImages: ${e.message}")
            null
        }
    }
    
    suspend fun getProductImage(userId: Int): ProductImage? {
        return try {
            productImageApi.getProductImage(userId)
        } catch (e: Exception) {
            println("Error fetching productImage: ${e.message}")
            null
        }
    }

    suspend fun createProductImage(productImage: ProductImage): ProductImage? {
        return try {
            productImageApi.createProductImage(productImage)
        } catch (e: Exception) {
            println("Error creating productImage: ${e.message}")
            null
        }
    }

    suspend fun updateProductImage(productImageId: Int, productImage: ProductImage): ProductImage? {
        return try {
            productImageApi.updateProductImage(productImageId, productImage)
        } catch (e: Exception) {
            println("Error updating productImage: ${e.message}")
            null
        }
    }

    suspend fun deleteProductImage(productImageId: Int): Boolean {
        return try {
            productImageApi.deleteProductImage(productImageId)
        } catch (e: Exception) {
            println("Error deleting productImage: ${e.message}")
            false
        }
    }
}
