package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.CategoryRepository
import org.example.project.domain.model.Category

class CategoryViewModel(private val categoryRepository: CategoryRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdCategory = mutableStateOf<Category?>(null)
    val createdCategory: State<Category?> get() = _createdCategory
    
    private val _category = mutableStateOf<Category?>(null)
    val category: State<Category?> get() = _category
    
    private val _updatedCategory = mutableStateOf<Category?>(null)
    val updatedCategory: State<Category?> get() = _updatedCategory

    private val _categorysList = mutableStateOf<List<Category>>(emptyList())
    val categorysList: State<List<Category>> get() = _categorysList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createCategory(category: Category) {
        val result = categoryRepository.createCategory(category)
        if (result != null) {
            _createdCategory.value = result
            _operationStatus.value = "Category Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Category"
        }
    }

    suspend fun getCategory(categoryId: Int) {
        val result = categoryRepository.getCategory(categoryId)
        if (result != null) {
            _category.value = result
            _operationStatus.value = "Category Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Category or Category Not Found"
        }
    }

    suspend fun updateCategory(categoryId: Int, category: Category) {
        val result = categoryRepository.updateCategory(categoryId, category)
        if (result != null) {
            _updatedCategory.value = result
            _operationStatus.value = "Category Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Category"
        }
    }

    suspend fun deleteCategory(categoryId: Int) {
        val result = categoryRepository.deleteCategory(categoryId)
        if (result) {
            _operationStatus.value = "Category Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Category"
        }
    }

    suspend fun getAllCategorys(currentPage: Int) {
        val result = categoryRepository.getAllCategorys(currentPage)
        if (result != null) {
            _categorysList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Category Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Category"
        }
    }
}