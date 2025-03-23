package org.example.project.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.project.model.Category
import org.example.project.ui.theme.Colors
import org.example.project.ui.widgets.CustomAppBar

@Composable
fun ManageCategories(navController: NavController) {
    // Danh sách mẫu các danh mục
    var categories by remember { mutableStateOf(
        listOf(
            Category("C001", "Vi xử lý", "Các linh kiện vi xử lý, vi điều khiển"),
            Category("C002", "Điện trở", "Các loại điện trở, biến trở"),
            Category("C003", "Tụ điện", "Tụ điện, tụ hóa, tụ gốm"),
            Category("C004", "Module", "Các loại module, mạch hoàn chỉnh"),
            Category("C005", "Cảm biến", "Các loại cảm biến nhiệt độ, áp suất, độ ẩm"),
        )
    )}
    
    // Dialog form thêm/sửa danh mục
    var showForm by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf<Category?>(null) }

    Scaffold(
        topBar = { CustomAppBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                backgroundColor = Colors.Primitive.Green1100,
                onClick = { 
                    selectedCategory = null
                    showForm = true 
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Thêm danh mục", tint = Color.White)
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
                    CategoryFormDialog(
                        onDismiss = {
                            showForm = false
                            selectedCategory = null
                        },
                        onSave = { newCategory ->
                            if (selectedCategory != null) {
                                // Cập nhật danh mục
                                categories = categories.map { 
                                    if (it.id == selectedCategory?.id) newCategory else it 
                                }
                            } else {
                                // Thêm danh mục mới
                                categories = categories + newCategory
                            }
                            showForm = false
                            selectedCategory = null
                        },
                        category = selectedCategory
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
                        text = "Quản lý danh mục sản phẩm",
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        onClick = {
                            selectedCategory = null
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
                                "Tạo mới",
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
                
                // Bảng danh sách danh mục
                CategoryList(
                    categories = categories,
                    onEdit = { category ->
                        selectedCategory = category
                        showForm = true
                    },
                    onDelete = { categoryId ->
                        categories = categories.filterNot { it.id == categoryId }
                    }
                )
            }
        }
    )
}

@Composable
fun CategoryList(
    categories: List<Category>,
    onEdit: (Category) -> Unit,
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
                        "MÃ DANH MỤC",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "TÊN DANH MỤC",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1.5f)
                    )
                    Text(
                        "MÔ TẢ",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        "THAO TÁC",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        color = Colors.Primitive.Gray900
                    )
                }
            }
            
            // Danh sách các danh mục
            items(categories) { category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        category.id,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        category.name,
                        modifier = Modifier.weight(1.5f)
                    )
                    Text(
                        category.description,
                        modifier = Modifier.weight(2f)
                    )
                    Row(
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Blue100),
                            onClick = { onEdit(category) },
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Sửa", tint = Color.White)
                        }
                        
                        Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Red1100),
                            onClick = { onDelete(category.id) },
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
fun CategoryFormDialog(
    onDismiss: () -> Unit,
    onSave: (Category) -> Unit,
    category: Category? = null
) {
    var id by remember { mutableStateOf(category?.id ?: "") }
    var name by remember { mutableStateOf(category?.name ?: "") }
    var description by remember { mutableStateOf(category?.description ?: "") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                text = if (category == null) "Thêm danh mục" else "Cập nhật danh mục"
            )
        },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    enabled = category == null,
                    label = { Text("Mã danh mục") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên danh mục") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả") },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
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
                        onSave(Category(id, name, description))
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