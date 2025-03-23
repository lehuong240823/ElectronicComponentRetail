package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.project.data.CartManager
import org.example.project.model.CartItem
import org.example.project.ui.theme.Colors
import org.example.project.utils.toStringWithTwoDecimals

@Composable
fun ShoppingCart(navController: NavController) {
    val cartItems by remember { mutableStateOf(CartManager.items) }
    var showCheckoutDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                backgroundColor = Colors.Primitive.Green1100,
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                EmptyCart(navController)
            } else {
                // Cart items list
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemCard(cartItem = item)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Order summary
                OrderSummary()
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Checkout button
                Button(
                    onClick = { showCheckoutDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100)
                ) {
                    Text("CHECKOUT", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
    
    // Checkout dialog
    if (showCheckoutDialog) {
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            title = { Text("Order Confirmation") },
            text = { Text("Thank you for your order! Your order has been placed successfully.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showCheckoutDialog = false
                        CartManager.clearCart()
                        navController.navigate("customerProducts") {
                            popUpTo("customerProducts") { inclusive = true }
                        }
                    }
                ) {
                    Text("OK", color = Colors.Primitive.Green1100)
                }
            },
            backgroundColor = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(8.dp)
        )
    }
}

@Composable
fun EmptyCart(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Empty Cart",
            tint = Colors.Primitive.Gray400,
            modifier = Modifier.size(100.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Your cart is empty",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Looks like you haven't added any items to your cart yet",
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 40.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = { navController.navigate("customerProducts") },
            colors = ButtonDefaults.buttonColors(backgroundColor = Colors.Primitive.Green1100),
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text("CONTINUE SHOPPING", color = Color.White)
        }
    }
}

@Composable
fun CartItemCard(cartItem: CartItem) {
    val product = cartItem.product
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product image placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Colors.Primitive.Gray200)
                    .clip(RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(product.tag, color = Color.Gray)
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Product details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = "$${product.price}",
                    color = Colors.Primitive.Green1100,
                    fontWeight = FontWeight.Medium
                )
                
                if (cartItem.appliedVoucher != null) {
                    Text(
                        text = "Voucher: ${cartItem.appliedVoucher.name} (-${(cartItem.appliedVoucher.value * 100).toInt()}%)",
                        color = Colors.Primitive.Blue100,
                        fontSize = 12.sp
                    )
                }
            }
            
            // Quantity adjuster
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = { CartManager.updateQuantity(product.id, cartItem.quantity - 1) },
                    modifier = Modifier
                        .size(28.dp)
                        .border(1.dp, Colors.Primitive.Gray400, RoundedCornerShape(4.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown, // Changed from Remove to KeyboardArrowDown
                        contentDescription = "Decrease",
                        tint = Colors.Primitive.Green1100,
                        modifier = Modifier.size(16.dp)
                    )
                }
                
                Text(
                    text = cartItem.quantity.toString(),
                    fontWeight = FontWeight.Bold
                )
                
                IconButton(
                    onClick = { CartManager.updateQuantity(product.id, cartItem.quantity + 1) },
                    modifier = Modifier
                        .size(28.dp)
                        .border(1.dp, Colors.Primitive.Gray400, RoundedCornerShape(4.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase",
                        tint = Colors.Primitive.Green1100,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(8.dp))
            
            // Delete button
            IconButton(
                onClick = { CartManager.removeItem(product.id) }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun OrderSummary() {
    val subtotal = CartManager.subtotal
    val discount = CartManager.discount
    val total = CartManager.total
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Order Summary",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Subtotal")
                Text(text = "$${subtotal.toStringWithTwoDecimals()}")
            }
            
            if (discount > 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Discount")
                    Text(text = "-$${discount.toStringWithTwoDecimals()}", color = Colors.Primitive.Blue100)
                }
            }
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total", fontWeight = FontWeight.Bold)
                Text(
                    text = "$${total.toStringWithTwoDecimals()}",
                    fontWeight = FontWeight.Bold,
                    color = Colors.Primitive.Green1100
                )
            }
        }
    }
}
