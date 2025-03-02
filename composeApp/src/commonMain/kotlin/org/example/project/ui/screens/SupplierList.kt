package org.example.project.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import org.example.project.model.Supplier
import org.example.project.ui.theme.Colors
import org.example.project.ui.widgets.SupplierTable

@Composable
fun ManageSuppliers() {
//    Data
    var suppliers by remember { mutableStateOf(
        listOf(
            Supplier("SUP001", "Công Ty A", "Hà Nội", "0123 456 789"),
            Supplier("SUP002", "Công Ty B", "Hà Nội", "0123 456 789"),
            Supplier("SUP003", "Công Ty C", "Hà Nội", "0123 456 789"),
            Supplier("SUP004", "Công Ty D", "Hà Nội", "0123 456 789")
        )
    )}

//    Add & Update Dialog
    var showForm by remember { mutableStateOf(false) }
    var selectedId: String? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = { TopAppBar(
            // Nút thêm nhà cung cấp
            actions = {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {}
                ) { Text("Products", fontWeight = FontWeight.Normal) }
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {}
                ) { Text("Solutions", fontWeight = FontWeight.Normal) }
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {}
                ) { Text("Community", fontWeight = FontWeight.Normal) }
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {}
                ) { Text("Resources", fontWeight = FontWeight.Normal) }
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {}
                ) { Text("Pricing", fontWeight = FontWeight.Normal) }
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.dp),
                    onClick = {}
                ) { Text("Contacts", fontWeight = FontWeight.Normal) }
            },
            backgroundColor = Color.White,
            title = {}
        )},
        content = { Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Colors.Primitive.Green1300).fillMaxSize().padding(16.dp)
        ) {
//             Hiển thị dialog khi showForm là true
            if (showForm) {
                SupplierFormDialog(
                    onDismiss = {
                        selectedId = null
                        showForm = false
                    },
                    onUpdate = {
                        newSupplier -> suppliers = suppliers.map {
                            oldSupplier -> if (oldSupplier.id == newSupplier.id) newSupplier else oldSupplier
                        }
                        selectedId = null
                        showForm = false
                    },
                    onSave = { newSupplier ->
                        suppliers = suppliers + newSupplier // Thêm nhà cung cấp mới
                        selectedId = null
                        showForm = false
                    },
                    supplierId = selectedId
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100),
                content = { Text(color = Color.White, text = "Thêm Nhà cung cấp") },
                onClick = { showForm = true }
            )
            Text(
                color = Colors.Primitive.Green1100,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                text = "Quản lý nhà cung cấp",
            )
            SupplierTable(
                actions = listOf<@Composable (String) -> Unit>(
                    { id -> Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Blue100),
                        onClick = {
                            selectedId = id
                            showForm = true
                        }
                    ) {
                        Text(color = Color.White, text = "Sửa")
                    }},
                    { id -> Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Red1100),
                        onClick = { suppliers = suppliers.filterNot { supplier -> supplier.id == id } }
                    ) {
                        Text(color = Color.White, text = "Xóa")
                    }}
                ),
                data = suppliers,
                headers = listOf(
                    "MÃ NHÀ CUNG CẤP",
                    "TÊN NHÀ CUNG CẤP",
                    "ĐỊA CHỈ",
                    "LIÊN HỆ"
                ),
            )
        }},
    )
}

@Composable
fun SupplierFormDialog(
    onDismiss: () -> Unit,
    onSave: (Supplier) -> Unit,
    onUpdate: (Supplier) -> Unit,
    supplierId: String? = null
) {
    var id by remember { mutableStateOf(supplierId ?: "") }
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    // AlertDialog hiển thị form thêm nhà cung cấp
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            text = "Thêm Nhà Cung Cấp"
        )},
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    enabled = supplierId == null,
                    label = { Text("Mã nhà cung cấp") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    ),
                )
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Tên nhà cung cấp") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Địa chỉ") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    )
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Liên hệ") },
                    modifier = Modifier.fillMaxWidth(),
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
                    if (supplierId == null)
                        onSave(Supplier(id, name, address, phone)) // Lưu nhà cung cấp mới
                    else
                        onUpdate(Supplier(id, name, address, phone))
                }
            ) {
                Text(color = Color.White, text = "Lưu")
            }
        },
        dismissButton = {
            OutlinedButton(
                border = BorderStroke(0.75.dp, Colors.Primitive.Green1100),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White),
                onClick = onDismiss) {
                Text(color = Colors.Primitive.Green1100, text = "Hủy")
            }
        }
    )
}
