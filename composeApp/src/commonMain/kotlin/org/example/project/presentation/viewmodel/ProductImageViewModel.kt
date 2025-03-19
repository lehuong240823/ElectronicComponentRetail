package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ProductImageRepository
import org.example.project.domain.model.ProductImage

class ProductImageViewModel(private val productImageRepository: ProductImageRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdProductImage = mutableStateOf<ProductImage?>(null)
    val createdProductImage: State<ProductImage?> get() = _createdProductImage
    
    private val _productImage = mutableStateOf<ProductImage?>(null)
    val productImage: State<ProductImage?> get() = _productImage
    
    private val _updatedProductImage = mutableStateOf<ProductImage?>(null)
    val updatedProductImage: State<ProductImage?> get() = _updatedProductImage

    private val _productImagesList = mutableStateOf<List<ProductImage>>(emptyList())
    val productImagesList: State<List<ProductImage>> get() = _productImagesList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createProductImage(productImage: ProductImage) {
        val result = productImageRepository.createProductImage(productImage)
        if (result != null) {
            _createdProductImage.value = result
            _operationStatus.value = "ProductImage Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create ProductImage"
        }
    }

    suspend fun getProductImage(productImageId: Int) {
        val result = productImageRepository.getProductImage(productImageId)
        if (result != null) {
            _productImage.value = result
            _operationStatus.value = "ProductImage Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get ProductImage or ProductImage Not Found"
        }
    }

    suspend fun updateProductImage(productImageId: Int, productImage: ProductImage) {
        val result = productImageRepository.updateProductImage(productImageId, productImage)
        if (result != null) {
            _updatedProductImage.value = result
            _operationStatus.value = "ProductImage Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update ProductImage"
        }
    }

    suspend fun deleteProductImage(productImageId: Int) {
        val result = productImageRepository.deleteProductImage(productImageId)
        if (result) {
            _operationStatus.value = "ProductImage Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete ProductImage"
        }
    }

    suspend fun getAllProductImages(currentPage: Int) {
        val result = productImageRepository.getAllProductImages(currentPage)
        if (result != null) {
            _productImagesList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "ProductImage Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All ProductImage"
        }
    }

    suspend fun getProductImagesByProductId(currentPage: Int, product: Int) {
        val result = productImageRepository.getProductImagesByProductId(currentPage, product)
        if (result != null) {
            _productImagesList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "ProductImage Get All Successfully"
        } else {
            _operationStatus.value = "ProductImage to Get All Account"
        }
    }
}