package org.example.project.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.repository.ProductRepository
import org.example.project.domain.model.Product

class ProductViewModel(private val productRepository: ProductRepository) {
    private val _totalPage = mutableStateOf<Int>(0)
    val totalPage: State<Int?> get() = _totalPage

    private val _createdProduct = mutableStateOf<Product?>(null)
    val createdProduct: State<Product?> get() = _createdProduct
    
    private val _product = mutableStateOf<Product?>(null)
    val product: State<Product?> get() = _product
    
    private val _updatedProduct = mutableStateOf<Product?>(null)
    val updatedProduct: State<Product?> get() = _updatedProduct

    private val _productsList = mutableStateOf<List<Product>>(emptyList())
    val productsList: State<List<Product>> get() = _productsList

    private val _operationStatus = mutableStateOf("")
    val operationStatus: State<String> get() = _operationStatus

    suspend fun createProduct(product: Product) {
        val result = productRepository.createProduct(product)
        if (result != null) {
            _createdProduct.value = result
            _operationStatus.value = "Product Created Successfully"
        } else {
            _operationStatus.value = "Failed to Create Product"
        }
    }

    suspend fun getProduct(productId: Int) {
        val result = productRepository.getProduct(productId)
        if (result != null) {
            _product.value = result
            _operationStatus.value = "Product Get Successfully"
        } else {
            _operationStatus.value = "Failed to Get Product or Product Not Found"
        }
    }

    suspend fun updateProduct(productId: Int, product: Product) {
        val result = productRepository.updateProduct(productId, product)
        if (result != null) {
            _updatedProduct.value = result
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

    suspend fun getAllProducts(currentPage: Int) {
        val result = productRepository.getAllProducts(currentPage)
        if (result != null) {
            _productsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Product Get All Successfully"
        } else {
            _operationStatus.value = "Failed to Get All Product"
        }
    }

    suspend fun getProductsByProductStatusId(currentPage: Int, productStatus: Byte) {
        val result = productRepository.getProductsByProductStatusId(currentPage, productStatus)
        if (result != null) {
            _productsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Product Get All Successfully"
        } else {
            _operationStatus.value = "Product to Get All Account"
        }
    }

    suspend fun getProductsByCategoryId(currentPage: Int, category: Int) {
        val result = productRepository.getProductsByCategoryId(currentPage, category)
        if (result != null) {
            _productsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Product Get All Successfully"
        } else {
            _operationStatus.value = "Product to Get All Account"
        }
    }

    suspend fun getProductsByProviderId(currentPage: Int, provider: Int) {
        val result = productRepository.getProductsByProviderId(currentPage, provider)
        if (result != null) {
            _productsList.value = result.content
            _totalPage.value = result.totalPages
            _operationStatus.value = "Product Get All Successfully"
        } else {
            _operationStatus.value = "Product to Get All Account"
        }
    }
}