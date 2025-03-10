package org.example.project.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ProductRepository
import org.example.project.domain.model.Product

class ProductViewModel(private val productRepository: ProductRepository) {
    private val _product = mutableStateOf<Product?>(null)
    val product: State<Product?> get() = _product

    private val _productsList = mutableStateOf<List<Product>>(emptyList())
    val productsList: State<List<Product>> get() = _productsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createProduct(product: Product) {
        val result = productRepository.createProduct(product)
        if (result != null) {
            _operationStatus.value = "Product Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Product"
        }
    }

    suspend fun getProduct(productId: Int) {
        val result = productRepository.getProduct(productId)
        if (result != null) {
            _product.value = result
        } else {
            _operationStatus.value = "Product Not Found"
        }
    }

    suspend fun updateProduct(productId: Int, product: Product) {
        val result = productRepository.updateProduct(productId, product)
        if (result != null) {
            _operationStatus.value = "Product Updated Successfully"
        } else {
            _operationStatus.value = "Failed to Update Product"
        }
    }

    suspend fun deleteProduct(productId: Int) {
        val result = productRepository.deleteProduct(productId)
        if (result) {
            _operationStatus.value = "Product Deleted Successfully"
        } else {
            _operationStatus.value = "Failed to Delete Product"
        }
    }

    suspend fun getAllProducts() {
        val result = productRepository.getAllProducts()
        _productsList.value = result?.content ?: emptyList()
    }
}