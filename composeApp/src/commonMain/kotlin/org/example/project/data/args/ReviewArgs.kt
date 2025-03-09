package org.example.project.data.args

import org.example.project.model.Product

class ReviewArgs {
    private var product: Product = Product("P01", "Sản phẩm A", "Tag", 50.0, "Mô tả sản phẩm", "")

    fun getProduct() : Product = product
    fun setProduct(product: Product) {
        this.product = product
    }
}