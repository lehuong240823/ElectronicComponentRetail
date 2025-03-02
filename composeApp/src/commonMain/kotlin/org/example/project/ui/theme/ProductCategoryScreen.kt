package org.example.project.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
//import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.tv.material3.CardDefaults
//import androidx.wear.compose.material.CardDefaults

@Composable
fun ProductCategoryScreen(onCategoryClick: (String) -> Unit) {
    val categories = listOf("CPU", "RAM", "SSD", "Mainboard", "VGA", "PSU")

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Danh mục sản phẩm", fontSize = 24.sp, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn {
            items(categories) { category ->
                categoryItem(category, onCategoryClick)
            }
        }
    }
}

@Composable
fun categoryItem(category: String, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(category) },
//        elevation = extracted().cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = category,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}

private fun extracted() {
//    return CardDefaults()
}
