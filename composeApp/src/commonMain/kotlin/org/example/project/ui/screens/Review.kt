package org.example.project.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.data.args.ReviewArgs
import org.example.project.model.Product
import org.example.project.model.Review
import org.example.project.ui.theme.Colors
import org.example.project.ui.widgets.CustomAppBar

@Composable
fun Review(navController: NavController, reviewArgs: ReviewArgs) {
    val product = reviewArgs.getProduct()
    val reviews = listOf(
        Review("Review title", "Review body", 1, "", "Reviewer name", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date),
        Review("Review title", "Review body", 3, "", "Reviewer name", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date),
        Review("Review title", "Review body", 5, "", "Reviewer name", Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date),
    )

    Scaffold(
        topBar = { CustomAppBar(navController) }
    ) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            ProductInformation(product)
            LastReview(reviews)
        }
    }
}

@Composable
fun ProductInformation(product: Product) {
    var quantity by remember { mutableStateOf(0) }
    var rate by remember { mutableStateOf(0) }
    var ratingStr by remember { mutableStateOf("") }

    Box(modifier = Modifier.padding(top = 64.dp, start = 64.dp, end = 64.dp)) {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
            Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Colors.Primitive.Gray100),
                    modifier = Modifier.fillMaxSize()
                ) {}
                IconButton(
                    modifier = Modifier.background(Colors.Primitive.Black100).clip(CircleShape)
                        .size(44.dp),
                    onClick = {},
                ) {
                    Icon(Icons.Default.Favorite, contentDescription = null)
                }
            }
            Box(modifier = Modifier.width(24.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = product.name
                )
                Box(
                    modifier = Modifier.background(
                        Colors.Primitive.Green1300,
                        shape = RoundedCornerShape(8.dp)
                    )
                        .padding(8.dp),
                ) {
                    Text(
                        color = Colors.Primitive.Green1100,
                        text = product.tag
                    )
                }
                Box(modifier = Modifier.size(4.dp))
                Text(
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    text = "${product.price} $"
                )
                Box(modifier = Modifier.height(16.dp))
                Text(
                    color = Colors.Primitive.Brand500,
                    fontSize = 16.sp,
                    text = product.description
                )
                Box(modifier = Modifier.height(24.dp))
                Text(
                    fontSize = 16.sp,
                    text = "Số lượng"
                )
                Box(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = quantity.toString(),
                    onValueChange = { newValue: String ->
                        if (newValue.all { it -> it.isDigit() || it == '.' }) {
                            quantity = newValue.toIntOrNull() ?: 0
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    ),
                )
                Box(modifier = Modifier.height(24.dp))
                Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth(),
                        text = "Thêm vào giỏ hàng",
                        textAlign = TextAlign.Center
                    )
                }
                Box(modifier = Modifier.height(24.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Đánh giá sản phẩm")
                    Box(modifier = Modifier.width(6.dp))
                    for (i in 1.. rate)
                        IconButton(onClick = {rate = i}) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "$i",
                                tint = Color.Yellow
                            )
                        }
                    for (i in rate + 1.. 5)
                        IconButton(onClick = {rate = i}) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "$i",
                                tint = Color.Gray
                            )
                        }
                }
                Box(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = ratingStr,
                    onValueChange = { ratingStr = it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(6.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = Color.White,
                        focusedBorderColor = Colors.Primitive.Gray900,
                        placeholderColor = Colors.Primitive.Green1100,
                        unfocusedBorderColor = Colors.Primitive.Gray300
                    ),
                )
            }
        }
    }
}

@Composable
fun LastReview(reviews: List<Review>) {
    Box(modifier = Modifier.padding(64.dp).fillMaxWidth())
    {
        Column {
            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                text = "Latest reviews"
            )
            Box(modifier = Modifier.height(48.dp))
            LazyRow {
                reviews.map { review -> item {
                    ReviewItem(review)
                    Box(modifier = Modifier.width(48.dp))
                } }
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Box(modifier = Modifier
        .border(BorderStroke(1.dp, Colors.Primitive.Brand300), shape = RoundedCornerShape(8.dp))
        .clip(RoundedCornerShape(8.dp)).padding(24.dp)
        .width(350.dp)
    ) {
        Column {
            Row {
                for (i in 1.. review.rate)
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "$i",
                        tint = Color.Yellow
                    )
                for (i in review.rate + 1.. 5)
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "$i",
                        tint = Color.Gray
                    )
            }
            Box(modifier = Modifier.height(24.dp))
            Text(
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                text = review.title
            )
            Box(modifier = Modifier.height(4.dp))
            Text(
                fontSize = 16.sp,
                overflow = TextOverflow.Visible,
                text = review.body
            )
            Box(modifier = Modifier.height(24.dp))
            Row {
                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.Gray))
                Box(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        color = Colors.Primitive.Brand500,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis,
                        text = review.name
                    )
                    Text(
                        color = Colors.Primitive.Brand400,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        text = review.date.toString()
                    )
                }
            }
        }
    }
}