package org.example.project.model

/**
 * Model đại diện cho một sản phẩm trong hệ thống
 *
 * @property id Mã sản phẩm
 * @property name Tên sản phẩm
 * @property tag Nhãn/Tag của sản phẩm
 * @property price Giá sản phẩm
 * @property description Mô tả sản phẩm
 * @property imageUrl URL hình ảnh sản phẩm
 * @property category Danh mục mà sản phẩm thuộc về
 */
data class Product(
    val id: String,
    val name: String,
    val tag: String,
    val price: Double,
    val description: String,
    val imageUrl: String,
    val categoryId: String = ""
) {
    override fun toString(): String = name
}