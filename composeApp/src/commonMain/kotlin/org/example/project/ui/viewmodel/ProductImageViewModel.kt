package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ProductImageRepository
import org.example.project.domain.model.ProductImage

class ProductImageViewModel(private val productImageRepository: ProductImageRepository) {
    private val _productImage = mutableStateOf<ProductImage?>(null)
    val productImage: State<ProductImage?> get() = _productImage

    private val _productImagesList = mutableStateOf<List<ProductImage>>(emptyList())
    val productImagesList: State<List<ProductImage>> get() = _productImagesList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createProductImage(productImage: ProductImage) {
        val result = productImageRepository.createProductImage(productImage)
        if (result != null) {
            _operationStatus.value = "ProductImage Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create ProductImage"
        }
    }

    suspend fun getProductImage(productImageId: Int) {
        val result = productImageRepository.getProductImage(productImageId)
        if (result != null) {
            _productImage.value = result
        } else {
            _operationStatus.value = "ProductImage Not Found"
        }
    }

    suspend fun updateProductImage(productImageId: Int, productImage: ProductImage) {
        val result = productImageRepository.updateProductImage(productImageId, productImage)
        if (result != null) {
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

    suspend fun getAllProductImages() {
        val result = productImageRepository.getAllProductImages()
        _productImagesList.value = result?.content ?: emptyList()
    }
}