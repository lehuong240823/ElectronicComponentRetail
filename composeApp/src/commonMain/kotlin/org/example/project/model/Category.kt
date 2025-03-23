package org.example.project.model

/**
 * Đại diện cho một danh mục sản phẩm trong hệ thống
 *
 * @property id Mã danh mục, ví dụ: C001, C002
 * @property name Tên danh mục, ví dụ: Vi xử lý, Điện trở
 * @property description Mô tả chi tiết về danh mục
 */
data class Category(
    val id: String,
    val name: String,
    val description: String
) {
    override fun toString(): String = name
}