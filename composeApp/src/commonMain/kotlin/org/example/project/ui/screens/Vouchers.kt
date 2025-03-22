package org.example.project.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.model.Product
import org.example.project.model.Voucher
import org.example.project.ui.theme.Colors
import org.example.project.ui.widgets.CustomAppBar
import org.example.project.ui.widgets.CustomDropdown
import org.example.project.ui.widgets.DateRange
import org.example.project.ui.widgets.DateRangePicker
import org.example.project.ui.widgets.VoucherTable

@Composable
fun ManageVouchers(navController: NavController) {
    val products by remember { mutableStateOf(
        listOf(
            Product("P001", "Sản phẩm 1", "Tag", 50.0, "", ""),
            Product("P002", "Sản phẩm 2", "Tag", 50.0, "", ""),
            Product("P003", "Sản phẩm 3", "Tag", 50.0, "", ""),
            Product("P004", "Sản phẩm 4", "Tag", 50.0, "", ""),
            Product("P005", "Sản phẩm 5", "Tag", 50.0, "", ""),
            Product("P006", "Sản phẩm 6", "Tag", 50.0, "", ""),
            Product("P007", "Sản phẩm 7", "Tag", 50.0, "", ""),
            Product("P008", "Sản phẩm 8", "Tag", 50.0, "", ""),
        )
    ) }
    var vouchers by remember { mutableStateOf(
        listOf(
            Voucher("V001", "Voucher 1", 0.1, LocalDate(2025, 3,8), LocalDate(2025, 3, 15), listOf(products[0], products[2], products[3])),
            Voucher("V002", "Voucher 2", 0.2, LocalDate(2025, 3,8), LocalDate(2025, 3, 15), listOf(products[1], products[4], products[6])),
            Voucher("V003", "Voucher 3", 0.3, LocalDate(2025, 3,8), LocalDate(2025, 3, 15), listOf(products[3], products[5])),
            Voucher("V004", "Voucher 4", 0.4, LocalDate(2025, 3,8), LocalDate(2025, 3, 15), listOf(products[7])),
        )
    )}

//    Add & Update Dialog
    var showForm by remember { mutableStateOf(false) }
    var selectedId: String? by remember { mutableStateOf(null) }
    var selectVoucher: Voucher? by remember { mutableStateOf(null) }

    Scaffold(
        topBar = { CustomAppBar(navController) },
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.background(Colors.Primitive.Green1300).fillMaxSize().padding(16.dp)
            ) {
                if (showForm) {
                    VoucherFormDialog(
                        onDismiss = {
                            selectedId = null
                            showForm = false
                        },
                        onUpdate = {
                                newVoucher -> vouchers = vouchers.map{
                                oldVoucher -> if (oldVoucher.id == newVoucher.id) newVoucher else oldVoucher
                        }
                            selectedId = null
                            showForm = false
                        },
                        onSave = { newSupplier ->
                            vouchers = vouchers + newSupplier // Thêm nhà cung cấp mới
                            selectedId = null
                            showForm = false
                        },
                        products = products,
                        voucher = selectVoucher
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100),
                    content = { Text(color = Color.White, text = "Thêm voucher") },
                    onClick = { showForm = true }
                )
                Text(
                    color = Colors.Primitive.Green1100,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = "Quản lý mã giảm giá",
                )
                VoucherTable(
                    actions = listOf<@Composable (String) -> Unit>(
                        { id -> Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Blue100),
                            onClick = {
                                selectVoucher = vouchers.find { it -> it.id == id }
                                showForm = true
                            }
                        ) {
                            Text(color = Color.White, text = "Sửa")
                        }},
                        { id -> Button(
                            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Red1100),
                            onClick = { vouchers = vouchers.filterNot { voucher -> voucher.id == id } }
                        ) {
                            Text(color = Color.White, text = "Xóa")
                        }}
                    ),
                    data = vouchers,
                    headers = listOf(
                        "MÃ VOUCHER",
                        "TÊN VOUCHER",
                        "GIÁ TRỊ",
                        "NGÀY HIỆU LỰC",
                        "NGÀY HẾT HẠN",
                        "DANH SÁCH SẢN PHẨM ĐƯỢC ÁP DỤNG"
                    ),
                )
            }
        }
    )
}

@Composable
fun VoucherFormDialog(
    onDismiss: () -> Unit,
    onSave: (Voucher) -> Unit,
    onUpdate: (Voucher) -> Unit,
    products: List<Product> = listOf(),
    voucher: Voucher? = null
) {
    var id by remember { mutableStateOf(voucher?.id ?: "") }
    var name by remember { mutableStateOf("") }
    var value by remember { mutableStateOf(0.0) }
    var selectedDateRange by remember { mutableStateOf(DateRange()) }
    var productList = remember { mutableStateListOf<Product>().apply { addAll(products) } }
    var selectedProducts = remember { mutableStateListOf<Product>() }

    var showDateRangeDialog by remember { mutableStateOf(false) }

//    Selecting product in ComboBox
    var selectingProduct by remember { mutableStateOf(productList[0]) }

    // AlertDialog hiển thị form thêm voucher
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            text = "Thêm Nhà Cung Cấp"
        )},
        text = {
            Column(modifier = Modifier.padding(16.dp)) {
                if (showDateRangeDialog) DateRangePicker(
                    initialDateRange = selectedDateRange,
                    onDateRangeSelected = { dateRange -> selectedDateRange = dateRange },
                    onDismiss = { showDateRangeDialog = false }
                )
                Box(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = id,
                    onValueChange = { id = it },
                    enabled = voucher == null,
                    label = { Text("Mã voucher") },
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
                    label = { Text("Tên voucher") },
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
                    value = "$value",
                    onValueChange = { newValue ->
                        // Cho phép số và dấu chấm cho số thập phân
                        if (newValue.all { it.isDigit() || it == '.' }) {
                            value = newValue.toDoubleOrNull() ?: 00.0
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Giá trị voucher") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    ),
                    trailingIcon = { Text("%") }
                )
                Row() {
                    OutlinedTextField(
                        value = "${selectedDateRange.startDate}",
                        onValueChange = { },
                        label = { Text("Ngày hiệu lực") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = Colors.Primitive.Gray900,
                            placeholderColor = Colors.Primitive.Green1100,
                            unfocusedBorderColor = Colors.Primitive.Gray300
                        ),
                        readOnly = true
                    )
                    Box(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = "${selectedDateRange.endDate}",
                        onValueChange = { },
                        label = { Text("Ngày hết hạn") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(6.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White,
                            focusedBorderColor = Colors.Primitive.Gray900,
                            placeholderColor = Colors.Primitive.Green1100,
                            unfocusedBorderColor = Colors.Primitive.Gray300
                        ),
                        readOnly = true
                    )
                    IconButton(onClick = {showDateRangeDialog = true}) {
                        Icon(Icons.Default.DateRange, contentDescription = null)
                    }
                }
                Box(modifier = Modifier.height(12.dp))
                Text("Thêm mã sản phẩm", fontSize = 14.sp)
                Box(modifier = Modifier.height(6.dp))
                selectedProducts.map {
                        product -> Row {
                    Text(product.toString())
                    IconButton(onClick = {
                        selectedProducts.remove(product)
                        productList.add(product)
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    }
                }
                }
                Row {
                    CustomDropdown(
                        onChanged = {value -> selectingProduct = value},
                        options = products
                    )
                    Box(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            productList.remove(selectingProduct)
                            selectedProducts.add(selectingProduct)
                        }
                    ) {
                        Text("Thêm")
                    }
                }

            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100),
                onClick = {
                    if (voucher == null) onSave(
                        Voucher(id, name, value / 100, selectedDateRange.startDate, selectedDateRange.endDate, selectedProducts)
                    ) else onUpdate(
                        Voucher(id, name, value / 100, selectedDateRange.startDate, selectedDateRange.endDate, selectedProducts)
                    )

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