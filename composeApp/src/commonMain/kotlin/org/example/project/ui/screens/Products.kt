package org.example.project.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.project.model.Category
import org.example.project.model.Product
import org.example.project.ui.theme.Colors
import org.example.project.ui.widgets.CustomAppBar

@Composable
fun ManageProducts(navController: NavController) {
    // Danh sách mẫu các danh mục
    val categories by remember { mutableStateOf(
        listOf(
            Category("C001", "Vi xử lý", "Các linh kiện vi xử lý, vi điều khiển"),
            Category("C002", "Điện trở", "Các loại điện trở, biến trở"),
            Category("C003", "Tụ điện", "Tụ điện, tụ hóa, tụ gốm"),
            Category("C004", "Module", "Các loại module, mạch hoàn chỉnh"),
            Category("C005", "Cảm biến", "Các loại cảm biến nhiệt độ, áp suất, độ ẩm"),
        )
    )}
    
    // Danh sách mẫu các sản phẩm
    var products by remember { mutableStateOf(
        listOf(
            Product("P001", "Arduino Nano", "Vi điều khiển", 8.5, "Bo mạch Arduino Nano ATmega328P", "", "C001"),
            Product("P002", "Raspberry Pi 4", "Vi xử lý", 45.0, "Máy tính nhúng Raspberry Pi 4 Model B", "", "C001"),
            Product("P003", "Điện trở 1K", "Linh kiện", 0.05, "Điện trở carbon 1KΩ 1/4W", "", "C002"),
            Product("P004", "Điện trở 10K", "Linh kiện", 0.05, "Điện trở carbon 10KΩ 1/4W", "", "C002"),
            Product("P005", "Tụ điện 100nF", "Linh kiện", 0.1, "Tụ gốm 100nF 50V", "", "C003"),
            Product("P006", "Module Relay 4 kênh", "Module", 3.5, "Module relay 4 kênh 5V với opto cách ly", "", "C004"),
            Product("P007", "DHT11", "Cảm biến", 2.5, "Cảm biến nhiệt độ và độ ẩm DHT11", "", "C005"),
            Product("P008", "HC-SR04", "Cảm biến", 1.8, "Cảm biến siêu âm đo khoảng cách HC-SR04", "", "C005"),
        )
    )}

    // Dialog form thêm/sửa sản phẩm
    var showForm by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    // Filter theo danh mục
    var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }
    
    // Search text
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        topBar = { CustomAppBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Colors.Primitive.Green1100,
                onClick = { 
                    selectedProduct = null
                    showForm = true 
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm sản phẩm", tint = Color.White)
            }
        },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Colors.Primitive.Green1300)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Dialog hiển thị form
                if (showForm) {
                    ProductFormDialog(
                        onDismiss = {
                            showForm = false
                            selectedProduct = null
                        },
                        onSave = { newProduct ->
                            if (selectedProduct != null) {
                                // Cập nhật sản phẩm
                                products = products.map { 
                                    if (it.id == selectedProduct?.id) newProduct else it 
                                }
                            } else {
                                // Thêm sản phẩm mới
                                products = products + newProduct
                            }
                            showForm = false
                            selectedProduct = null
                        },
                        product = selectedProduct,
                        categories = categories
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Colors.Primitive.Green1100,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        text = "Quản lý sản phẩm",
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        onClick = {
                            selectedProduct = null
                            showForm = true
                        }
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Thêm mới",
                                tint = Color.White
                            )
                            Text(
                                "Thêm sản phẩm",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                
                // Bộ lọc và tìm kiếm
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Tìm kiếm
                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp), // Thiết lập chiều cao cụ thể
                        placeholder = { Text("Tìm kiếm sản phẩm") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Tìm kiếm") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = Colors.Primitive.Gray900,
                            placeholderColor = Colors.Primitive.Gray500,
                            unfocusedBorderColor = Colors.Primitive.Gray300
                        ),
                        shape = RoundedCornerShape(6.dp),
                        singleLine = true // Đảm bảo input chỉ có một dòng
                    )
                    
                    // Lọc theo danh mục
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp), // Đặt chiều cao giống với ô tìm kiếm
                        shape = RoundedCornerShape(6.dp),
                        backgroundColor = Color.White,
                        elevation = 0.dp,
                        border = BorderStroke(1.dp, Colors.Primitive.Gray300)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight() // Đảm bảo row lấp đầy chiều cao của Card
                                .padding(horizontal = 16.dp), // Padding ngang giống với TextField
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            // Biểu tượng lọc
                            Icon(
                                Icons.Default.Search,  // Hoặc biểu tượng tương tự
                                contentDescription = "Lọc",
                                tint = Colors.Primitive.Gray700,
                                modifier = Modifier.padding(end = 12.dp)
                            )
                            
                            var expanded by remember { mutableStateOf(false) }
                            
                            Box(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = selectedCategoryFilter?.let { categoryId -> 
                                        categories.find { it.id == categoryId }?.name ?: "Tất cả danh mục"
                                    } ?: "Tất cả danh mục",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { expanded = true },
                                    color = if (selectedCategoryFilter == null) Colors.Primitive.Gray500 else Color.Black
                                )
                                
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    DropdownMenuItem(onClick = {
                                        selectedCategoryFilter = null
                                        expanded = false
                                    }) {
                                        Text("Tất cả danh mục")
                                    }
                                    
                                    categories.forEach { category ->
                                        DropdownMenuItem(onClick = {
                                            selectedCategoryFilter = category.id
                                            expanded = false
                                        }) {
                                            Text(category.name)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                // Lọc sản phẩm theo điều kiện tìm kiếm và danh mục
                val filteredProducts = products.filter { product ->
                    (searchText.isEmpty() || product.name.contains(searchText, ignoreCase = true) || 
                     product.description.contains(searchText, ignoreCase = true)) &&
                    (selectedCategoryFilter == null || product.categoryId == selectedCategoryFilter)
                }
                
                // Bảng danh sách sản phẩm
                ProductList(
                    products = filteredProducts,
                    categories = categories,
                    onEdit = { product ->
                        selectedProduct = product
                        showForm = true
                    },
                    onDelete = { productId ->
                        products = products.filterNot { it.id == productId }
                    }
                )
            }
        }
    )
}

@Composable
fun ProductList(
    products: List<Product>,
    categories: List<Category>,
    onEdit: (Product) -> Unit,
    onDelete: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.White
    ) {
        LazyColumn {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Colors.Primitive.Gray100)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "MÃ",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.6f)
                    )
                    Text(
                        "TÊN SẢN PHẨM",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1.5f)
                    )
                    Text(
                        "DANH MỤC",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "GIÁ",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.7f)
                    )
                    Text(
                        "MÔ TẢ",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        "THAO TÁC",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(0.8f),
                        color = Colors.Primitive.Gray900
                    )
                }
            }
            
            // Danh sách các sản phẩm
            items(products) { product ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        product.id,
                        modifier = Modifier.weight(0.6f)
                    )
                    Text(
                        product.name,
                        modifier = Modifier.weight(1.5f)
                    )
                    Text(
                        categories.find { it.id == product.categoryId }?.name ?: "Không có",
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "$${product.price}",
                        modifier = Modifier.weight(0.7f)
                    )
                    Text(
                        product.description,
                        maxLines = 2,
                        modifier = Modifier.weight(2f)
                    )
                    Row(
                        modifier = Modifier.weight(0.8f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Blue100),
                            onClick = { onEdit(product) },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Sửa", tint = Color.White)
                        }
                        
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Red1100),
                            onClick = { onDelete(product.id) },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Xóa", tint = Color.White)
                        }
                    }
                }
                Divider()
            }
        }
    }
}

@Composable
fun ProductFormDialog(
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit,
    product: Product? = null,
    categories: List<Category>
) {
    var id by remember { mutableStateOf(product?.id ?: "") }
    var name by remember { mutableStateOf(product?.name ?: "") }
    var tag by remember { mutableStateOf(product?.tag ?: "") }
    var price by remember { mutableStateOf((product?.price ?: 0.0).toString()) }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var imageUrl by remember { mutableStateOf(product?.imageUrl ?: "") }
    var selectedCategoryId by remember { mutableStateOf(product?.categoryId ?: categories.firstOrNull()?.id ?: "") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                text = if (product == null) "Thêm sản phẩm" else "Cập nhật sản phẩm"
            )
        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                // Mã sản phẩm
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    enabled = product == null,
                    label = { Text("Mã sản phẩm") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Tên sản phẩm
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên sản phẩm") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Tag
                OutlinedTextField(
                    value = tag,
                    onValueChange = { tag = it },
                    label = { Text("Tag") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Giá
                OutlinedTextField(
                    value = price,
                    onValueChange = { 
                        // Chỉ cho phép nhập số và dấu chấm
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            price = it
                        }
                    },
                    label = { Text("Giá") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Danh mục
                Text(
                    "Danh mục",
                    color = Colors.Primitive.Gray700,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                
                var expanded by remember { mutableStateOf(false) }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Colors.Primitive.Gray300,
                            shape = RoundedCornerShape(6.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = categories.find { it.id == selectedCategoryId }?.name ?: "Chọn danh mục",
                            color = if (selectedCategoryId.isEmpty()) Colors.Primitive.Gray500 else Color.Black
                        )
                        
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = Colors.Primitive.Gray700
                        )
                    }
                    
                    // Sửa đổi DropdownMenu để có kích thước phù hợp
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        // Bỏ modifier fillMaxWidth, để menu tự điều chỉnh kích thước theo nội dung
                        // Thêm kích thước tối đa để không bị quá to
                        modifier = Modifier.widthIn(min = 200.dp, max = 300.dp)
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(onClick = {
                                selectedCategoryId = category.id
                                expanded = false
                            }) {
                                Text(category.name)
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Mô tả
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // URL hình ảnh
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("URL hình ảnh") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100),
                onClick = {
                    if (id.isNotEmpty() && name.isNotEmpty()) {
                        val priceValue = price.toDoubleOrNull() ?: 0.0
                        onSave(Product(id, name, tag, priceValue, description, imageUrl, selectedCategoryId))
                    }
                }
            ) {
                Text(color = Color.White, text = "Lưu")
            }
        },
        dismissButton = {
            OutlinedButton(
                border = BorderStroke(0.75.dp, Colors.Primitive.Green1100),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
                onClick = onDismiss
            ) {
                Text(color = Colors.Primitive.Green1100, text = "Hủy")
            }
        }
    )
}