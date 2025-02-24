package org.example.project.ui.screens

package com.example.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Product(val name: String, val price: Int)

@Composable
fun ProductListScreen(category: String) {
    val products = remember { getProductsByCategory(category) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Sản phẩm trong danh mục: $category", fontSize = 22.sp)

        LazyColumn {
            items(products) { product ->
                ProductItem(product)
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = product.name, fontSize = 18.sp)
            Text(text = "${product.price} VND", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
        }
    }
}

fun getProductsByCategory(category: String): List<Product> {
    return when (category) {
        "CPU" -> listOf(Product("Intel i5 12400F", 5000000), Product("Ryzen 5 5600X", 6000000))
        "RAM" -> listOf(Product("Corsair 16GB DDR4", 1800000), Product("G.Skill 32GB DDR5", 4500000))
        else -> listOf(Product("Sản phẩm chưa có dữ liệu", 0))
    }
}
