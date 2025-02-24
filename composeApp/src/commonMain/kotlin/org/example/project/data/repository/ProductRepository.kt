package org.example.project.data.repository

import org.example.project.data.api.ProductApi
import org.example.project.domain.model.Product

class ProductRepository(private val productApi: ProductApi) {

    suspend fun getProduct(userId: Int): Product? {
        return try {
            productApi.getProduct(userId)
        } catch (e: Exception) {
            println("Error fetching product: \${e.message}")
            null
        }
    }

    suspend fun getAllProducts(): List<Product>? {
        return try {
            productApi.getAllProducts()
        } catch (e: Exception) {
            println("Error fetching products: \${e.message}")
            null
        }
    }

    suspend fun createProduct(product: Product): Product? {
        return try {
            productApi.createProduct(product)
        } catch (e: Exception) {
            println("Error creating product: \${e.message}")
            null
        }
    }

    suspend fun updateProduct(productId: Int, product: Product): Product? {
        return try {
            productApi.updateProduct(productId, product)
        } catch (e: Exception) {
            println("Error updating product: \${e.message}")
            null
        }
    }

    suspend fun deleteProduct(productId: Int): Boolean {
        return try {
            productApi.deleteProduct(productId)
        } catch (e: Exception) {
            println("Error deleting product: \${e.message}")
            false
        }
    }
}
