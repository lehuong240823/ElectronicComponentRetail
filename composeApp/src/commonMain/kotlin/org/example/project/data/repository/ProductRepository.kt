package org.example.project.data.repository

import org.example.project.domain.model.Product
import org.example.project.domain.model.PaginatedResponse
import org.example.project.data.api.ProductApi

class ProductRepository(private val productApi: ProductApi) {

    suspend fun getAllProducts(currentPage: Int): PaginatedResponse<Product>? {
        return try {
            productApi.getAllProducts(currentPage)
        } catch (e: Exception) {
            println("Error fetching products: ${e.message}")
            null
        }
    }
    
    suspend fun getProduct(userId: Int): Product? {
        return try {
            productApi.getProduct(userId)
        } catch (e: Exception) {
            println("Error fetching product: ${e.message}")
            null
        }
    }

    suspend fun createProduct(product: Product): Product? {
        return try {
            productApi.createProduct(product)
        } catch (e: Exception) {
            println("Error creating product: ${e.message}")
            null
        }
    }

    suspend fun updateProduct(productId: Int, product: Product): Product? {
        return try {
            productApi.updateProduct(productId, product)
        } catch (e: Exception) {
            println("Error updating product: ${e.message}")
            null
        }
    }

    suspend fun deleteProduct(productId: Int): Boolean {
        return try {
            productApi.deleteProduct(productId)
        } catch (e: Exception) {
            println("Error deleting product: ${e.message}")
            false
        }
    }

    suspend fun getProductsByProductStatusId(currentPage: Int, productStatus: Byte): PaginatedResponse<Product>? {
        return try {
            productApi.getProductsByProductStatusId(currentPage, productStatus)
        } catch (e: Exception) {
            println("Error fetching products: ${e.message}")
            null
        }
    }

    suspend fun getProductsByCategoryId(currentPage: Int, category: Int): PaginatedResponse<Product>? {
        return try {
            productApi.getProductsByCategoryId(currentPage, category)
        } catch (e: Exception) {
            println("Error fetching products: ${e.message}")
            null
        }
    }

    suspend fun getProductsByProviderId(currentPage: Int, provider: Int): PaginatedResponse<Product>? {
        return try {
            productApi.getProductsByProviderId(currentPage, provider)
        } catch (e: Exception) {
            println("Error fetching products: ${e.message}")
            null
        }
    }

    suspend fun findProductsByNameContainingIgnoreCase(currentPage: Int, name: String): PaginatedResponse<Product>? {
        return try {
            productApi.findProductsByNameContainingIgnoreCase(currentPage, name)
        } catch (e: Exception) {
            println("Error fetching products: ${e.message}")
            null
        }
    }
}