package org.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Supplier(val id: String, val name: String, val address: String, val phone: String)

@Composable
fun ManageSuppliers() {
    var suppliers by remember { mutableStateOf(
        listOf(
            Supplier("SUP001", "Công Ty A", "Hà Nội", "0123 456 789"),
            Supplier("SUP002", "Công Ty B", "Hà Nội", "0123 456 789"),
            Supplier("SUP003", "Công Ty C", "Hà Nội", "0123 456 789"),
            Supplier("SUP004", "Công Ty D", "Hà Nội", "0123 456 789")
        )
    )}
    var showForm by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Nút thêm nhà cung cấp
        Button(onClick = { showForm = !showForm }) {
            Text("Thêm Nhà Cung Cấp")
        }

        // Hiển thị dialog khi showForm là true
        if (showForm) {
            SupplierFormDialog(
                onDismiss = { showForm = false }, // Đóng form
                onSave = { newSupplier ->
                    suppliers = suppliers + newSupplier // Thêm nhà cung cấp mới
                    showForm = false
                }
            )
        }

        // Danh sách nhà cung cấp
        SupplierList(suppliers)
    }
}

@Composable
fun SupplierList(suppliers: List<Supplier>) {
    LazyColumn {
        items(suppliers) { supplier ->
            SupplierItem(supplier)
        }
    }
}

@Composable
fun SupplierItem(supplier: Supplier) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(supplier.id, modifier = Modifier.weight(1f))
        Text(supplier.name, modifier = Modifier.weight(2f))
        Text(supplier.address, modifier = Modifier.weight(2f))
        Text(supplier.phone, modifier = Modifier.weight(1f))
        Button(onClick = { /* Sửa nhà cung cấp */ }) {
            Text("Sửa")
        }
        Button(onClick = { /* Xóa nhà cung cấp */ }) {
            Text("Xóa")
        }
    }
}

@Composable
fun SupplierFormDialog(onDismiss: () -> Unit, onSave: (Supplier) -> Unit) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    // AlertDialog hiển thị form thêm nhà cung cấp
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Thêm Nhà Cung Cấp") },
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Mã Nhà Cung Cấp")
                TextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())

                Text("Tên Nhà Cung Cấp")
                TextField(value = address, onValueChange = { address = it }, modifier = Modifier.fillMaxWidth())

                Text("Liên Hệ")
                TextField(value = phone, onValueChange = { phone = it }, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(Supplier("SUP${(1000..9999).random()}", name, address, phone)) // Lưu nhà cung cấp mới
                }
            ) {
                Text("Lưu")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}


