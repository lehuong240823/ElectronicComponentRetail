package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.project.data.CartManager
import org.example.project.model.Product
import org.example.project.ui.theme.Colors
import org.example.project.ui.widgets.CustomAppBar

@Composable
fun CustomerProducts(navController: NavController) {
    val products by remember { mutableStateOf(listOf(
        Product("P001", "Arduino Nano", "Vi điều khiển", 8.5, "Bo mạch Arduino Nano ATmega328P", "", "C001"),
        Product("P002", "Raspberry Pi 4", "Vi xử lý", 45.0, "Máy tính nhúng Raspberry Pi 4 Model B", "", "C001"),
        Product("P003", "Điện trở 1K", "Linh kiện", 0.05, "Điện trở carbon 1KΩ 1/4W", "", "C002"),
        Product("P004", "Điện trở 10K", "Linh kiện", 0.05, "Điện trở carbon 10KΩ 1/4W", "", "C002"),
        Product("P005", "Tụ điện 100nF", "Linh kiện", 0.1, "Tụ gốm 100nF 50V", "", "C003"),
        Product("P006", "Module Relay 4 kênh", "Module", 3.5, "Module relay 4 kênh 5V với opto cách ly", "", "C004"),
        Product("P007", "DHT11", "Cảm biến", 2.5, "Cảm biến nhiệt độ và độ ẩm DHT11", "", "C005"),
        Product("P008", "HC-SR04", "Cảm biến", 1.8, "Cảm biến siêu âm đo khoảng cách HC-SR04", "", "C005"),
    )) }
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    
    val categories = listOf(
        "All Products", 
        "Vi điều khiển", 
        "Linh kiện", 
        "Module", 
        "Cảm biến"
    )
    
    val filteredProducts = remember(searchQuery, selectedCategory) {
        products.filter { product ->
            (searchQuery.isEmpty() || 
            product.name.contains(searchQuery, ignoreCase = true)) &&
            (selectedCategory == null || selectedCategory == "All Products" || 
            product.tag == selectedCategory)
        }
    }
    
    Scaffold(
        topBar = { 
            TopAppBar(
                title = { Text("Electronic Components") },
                backgroundColor = Colors.Primitive.Green1100, // Fixed color reference
                contentColor = Color.White,
                actions = {
                    // Search icon
                    IconButton(onClick = { /* Toggle search */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    
                    // Cart icon with badge
                    IconButton(onClick = { navController.navigate("shoppingCart") }) {
                        Badge(
                            backgroundColor = Colors.Primitive.Blue100, // Fixed color reference
                            contentColor = Color.White,
                            modifier = Modifier.align(Alignment.Top)
                        ) {
                            Text(CartManager.totalItems.toString())
                        }
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Shopping Cart")
                    }
                }
            )
        }
    ) { paddingValues ->
        Row(modifier = Modifier.fillMaxSize()) {
            // Sidebar for filters
            Column(
                modifier = Modifier
                    .width(250.dp)
                    .padding(16.dp)
                    .background(Color.LightGray.copy(alpha = 0.2f))
            ) {
                Text("Keywords", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(categories) { category ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedCategory = if (selectedCategory == category) null else category }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedCategory == category,
                                onCheckedChange = { selectedCategory = if (selectedCategory == category) null else category }
                            )
                            Text(text = category, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("Price Range", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = 50f,
                    onValueChange = {},
                    valueRange = 0f..100f,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Main content
            Column(modifier = Modifier.weight(1f).padding(16.dp)) {
                // Search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search products...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Sorting options
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Sort by:", fontWeight = FontWeight.Bold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { /* Sort by New */ }) { Text("New") }
                        Button(onClick = { /* Sort by Price Ascending */ }) { Text("Price ascending") }
                        Button(onClick = { /* Sort by Price Descending */ }) { Text("Price descending") }
                        Button(onClick = { /* Sort by Rating */ }) { Text("Rating") }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Product grid with even larger cells
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 320.dp), // Increased from 240.dp
                    verticalArrangement = Arrangement.spacedBy(32.dp), // Increased from 24.dp
                    horizontalArrangement = Arrangement.spacedBy(32.dp), // Increased from 24.dp
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            product = product,
                            onAddToCart = { CartManager.addItem(product) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryChip(name: String, selected: Boolean, onSelected: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(if (selected) Colors.Primitive.Green1100 else Color.LightGray.copy(alpha = 0.3f)) // Fixed color reference
            .clickable { onSelected() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = if (selected) Color.White else Color.Black
        )
    }
}

@Composable
fun ProductCard(product: Product, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp), // Increased from 280.dp
        elevation = 8.dp, // Increased from 6.dp for even more prominence
        shape = RoundedCornerShape(16.dp) // Increased from 12.dp
    ) {
        Column {
            // Product image placeholder (would be an actual image in a real app)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Increased from 140.dp
                    .background(Colors.Primitive.Gray300),
                contentAlignment = Alignment.Center
            ) {
                Text(product.tag, color = Color.White, fontSize = 22.sp) // Increased from 18.sp
            }
            
            Column(
                modifier = Modifier
                    .padding(24.dp) // Increased from 16.dp
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Product info
                Column {
                    Text(
                        text = product.name,
                        fontSize = 24.sp, // Increased from 18.sp
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp)) // Increased from 8.dp
                    
                    Text(
                        text = product.description,
                        fontSize = 16.sp, // Increased from 14.sp
                        color = Color.Gray,
                        maxLines = 3, // Increased from 2
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                // Price and add to cart
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${product.price}",
                        fontSize = 26.sp, // Increased from 20.sp
                        fontWeight = FontWeight.Bold,
                        color = Colors.Primitive.Green1100
                    )
                    
                    IconButton(
                        onClick = onAddToCart,
                        modifier = Modifier
                            .size(52.dp) // Increased from 40.dp
                            .clip(RoundedCornerShape(8.dp))
                            .background(Colors.Primitive.Green1100)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add to cart",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp) // Increased from 24.dp
                        )
                    }
                }
            }
        }
    }
}
