package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ProductStatusRepository
import org.example.project.domain.model.ProductStatus

class ProductStatusViewModel(private val productStatusRepository: ProductStatusRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdProductStatus = mutableStateOf<ProductStatus?>(null)
    val createdProductStatus: State<ProductStatus?> get() = _createdProductStatus
    
    private val _productStatus = mutableStateOf<ProductStatus?>(null)
    val productStatus: State<ProductStatus?> get() = _productStatus
    
    private val _updatedProductStatus = mutableStateOf<ProductStatus?>(null)
    val updatedProductStatus: State<ProductStatus?> get() = _updatedProductStatus

    private val _productStatussList = mutableStateOf<List<ProductStatus>>(emptyList())
    val productStatussList: State<List<ProductStatus>> get() = _productStatussList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createProductStatus(productStatus: ProductStatus) {
        val result = productStatusRepository.createProductStatus(productStatus)
        if (result != null) {
            _createdProductStatus.value = result
            _operationStatus.value = "ProductStatus Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create ProductStatus"
        }
    }

    suspend fun getProductStatus(productStatusId: Int) {
        val result = productStatusRepository.getProductStatus(productStatusId)
        if (result != null) {
            _productStatus.value = result
            _operationStatus.value = "ProductStatus Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get ProductStatus or ProductStatus Not Found"
        }
    }

    suspend fun updateProductStatus(productStatusId: Int, productStatus: ProductStatus) {
        val result = productStatusRepository.updateProductStatus(productStatusId, productStatus)
        if (result != null) {
            _updatedProductStatus.value = result
            _operationStatus.value = "ProductStatus Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update ProductStatus"
        }
    }

    suspend fun deleteProductStatus(productStatusId: Int) {
        val result = productStatusRepository.deleteProductStatus(productStatusId)
        if (result) {
            _operationStatus.value = "ProductStatus Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete ProductStatus"
        }
    }

    suspend fun getAllProductStatuss(currentPage: Int) {
        val result = productStatusRepository.getAllProductStatuss(currentPage)
        if (result != null) {
            _productStatussList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "ProductStatus Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All ProductStatus"
        }
    }
}