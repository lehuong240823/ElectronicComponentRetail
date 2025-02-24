package org.example.project.data.repository

import org.example.project.data.api.CategoryApi
import org.example.project.domain.model.Category

class CategoryRepository(private val categoryApi: CategoryApi) {

    suspend fun getCategory(userId: Int): Category? {
        return try {
            categoryApi.getCategory(userId)
        } catch (e: Exception) {
            println("Error fetching category: \${e.message}")
            null
        }
    }

    suspend fun getAllCategorys(): List<Category>? {
        return try {
            categoryApi.getAllCategorys()
        } catch (e: Exception) {
            println("Error fetching categorys: \${e.message}")
            null
        }
    }

    suspend fun createCategory(category: Category): Category? {
        return try {
            categoryApi.createCategory(category)
        } catch (e: Exception) {
            println("Error creating category: \${e.message}")
            null
        }
    }

    suspend fun updateCategory(categoryId: Int, category: Category): Category? {
        return try {
            categoryApi.updateCategory(categoryId, category)
        } catch (e: Exception) {
            println("Error updating category: \${e.message}")
            null
        }
    }

    suspend fun deleteCategory(categoryId: Int): Boolean {
        return try {
            categoryApi.deleteCategory(categoryId)
        } catch (e: Exception) {
            println("Error deleting category: \${e.message}")
            false
        }
    }
}
