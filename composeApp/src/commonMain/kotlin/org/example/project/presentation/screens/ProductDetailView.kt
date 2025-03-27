package org.example.project.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import coil3.compose.AsyncImage
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.blank_profile
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.example.project.CURRENCY
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AlertType
import org.example.project.data.api.CartApi
import org.example.project.data.repository.CartRepository
import org.example.project.domain.model.*
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.ReviewCard
import org.example.project.presentation.components.common.BackButton
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.input.QuantityGroup
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.presentation.theme.Typography.Primitive
import org.example.project.presentation.viewmodel.CartViewModel
import org.example.project.presentation.viewmodel.ReviewViewModel
import org.jetbrains.compose.resources.painterResource

class ProductDetail(
    val product: Product,
    val productImage: ProductImage
) : Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var rootMaxWidth = remember { mutableStateOf(0) }
        val showLoadingOverlay = mutableStateOf(false)
        val showErrorDialog = mutableStateOf(false)
        var color: ButtonColor = Themes.Light.primaryLayout
        val quantity = remember { mutableStateOf(1) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val cartViewModel = CartViewModel(CartRepository(CartApi()))
        val alertType = mutableStateOf(AlertType.Default)

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = color.defaultBackground!!)
                    .padding(
                        top = Size.Space.S600,
                        start = Size.Space.S1600,
                        bottom = Size.Space.S1600,
                        end = Size.Space.S1600
                    ),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S600)
            ) {
                BackButton()
                FlowRow(
                    maxItemsInEachRow = 2,
                    overflow = FlowRowOverflow.Visible,
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S1600),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S600)
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.weight(1f).aspectRatio(1f)
                            .widthIn(max = 420.dp)
                            .heightIn(min = 300.dp, max = 420.dp),
                        model =  productImage.url,
                        error = painterResource(Res.drawable.Image),
                        placeholder = painterResource(Res.drawable.Image),
                        contentDescription = null,
                    )
                    
                    ProductInfoColumn(
                        modifier = Modifier.wrapContentHeight()
                            .weight(1f),
                        quantity = quantity,
                        product = product,
                        onAddToCart = {
                            when (SessionData.getCurrentAccount()?.accountRole?.name) {
                                AccountRoleType.User.name -> {
                                    scope.launch {
                                        handlerAddToCart(
                                            cartViewModel = cartViewModel,
                                            cart = mutableStateOf(
                                                Cart(
                                                    user = SessionData.getCurrentUser(),
                                                    product = product,
                                                    quantity = quantity.value,
                                                    addedAt = Clock.System.now()
                                                )
                                            ),
                                            showLoadingOverlay = showLoadingOverlay,
                                            showErrorDialog = showErrorDialog,
                                            alertType = alertType
                                        )
                                    }
                                }
                            }
                        },
                        onBuyNow = {

                        }
                    )
                    ProductDescriptionColumn(
                        modifier = Modifier.weight(1f),
                        product = product
                    )
                    ProductReviewColumn(
                        product = product
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductInfoColumn(
    modifier: Modifier = Modifier,
    quantity: MutableState<Int>,
    product: Product,
    onAddToCart: () -> Unit,
    onBuyNow: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Size.Space.S600)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Size.Space.S400)
        ) {
            BodyText(
                text = product.name?:"",
                style = Typography.Style.Heading
            )
            Row(
                modifier = Modifier.wrapContentHeight()
            ) {
                BodyText(
                    text = CURRENCY,
                    style = Typography.Style.Subtitle
                        .merge(
                            fontSize = Typography.Default.Subtitle.FontSizeSmall,
                            fontWeight = Primitive.Weight.Bold
                        )
                )
                BodyText(
                    text = product.price?.toPlainString().toString(),
                    style = Typography.Style.TitlePage
                )
            }
        }
        when (SessionData.getCurrentAccount()?.accountRole?.name) {
            AccountRoleType.User.name -> {
                QuantityGroup(quantity = quantity)
                ProductDetailButtonRow(
                    onAddToCart = onAddToCart,
                    onBuyNow = onBuyNow
                )
            }
        }
    }
}

@Composable
fun ProductDescriptionColumn(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        BodyText(
            text = "Product Detail",
            style = Typography.Style.Heading
        )
        Row {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                BodyText(
                    text = "Category",
                    color = Themes.Light.primaryLayout.copy(primaryText = Themes.Light.primaryLayout.secondaryText)
                )
                BodyText(
                    text = "Provider",
                    color = Themes.Light.primaryLayout.copy(primaryText = Themes.Light.primaryLayout.secondaryText)
                )
                BodyText(
                    text = "Stock",
                    color = Themes.Light.primaryLayout.copy(primaryText = Themes.Light.primaryLayout.secondaryText)
                )
            }
            //Spacer(modifier = Modifier.width(Size.Space.S200))
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                BodyText(
                    text = product.category?.name?:"",
                )
                BodyText(
                    text = product.provider?.name?:"",
                )
                BodyText(
                    text = if(product.stock == null) "Out of stock" else product.stock.toString(),
                )
            }
        }
        BodyText(
            text = "Product Description",
            style = Typography.Style.Heading
        )
        BodyText(
            text = product.description?:"",
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductReviewColumn(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        BodyText(
            text = "Product Review",
            style = Typography.Style.Heading
        )
        FlowRow(
            modifier = Modifier.wrapContentSize(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
            verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
        ) {
            ReviewCard(
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Composable
fun AvatarBlock(
    modifier: Modifier = Modifier,
    color: ButtonColor = Themes.Light.primaryLayout,
    showMoreActionButton: Boolean = true,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        Image(
            modifier = Modifier.size(40.dp)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(Res.drawable.blank_profile),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            BodyText(
                text = "example@gmail.com",
                style = Typography.Style.BodyStrong
            )
            BodyText(
                text = "23/12/2024",
                style = Typography.Style.BodyBase.copy(color = color.secondaryText!!)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if(showMoreActionButton) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_dots_vertical),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ProductDetailButtonRow(
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
    onBuyNow: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
    ) {
        CustomButton(
            modifier = Modifier.weight(1f),
            text = "Add to Cart",
            color = Themes.Light.neutralButton,
            onClick = onAddToCart
        )
        CustomButton(
            modifier = Modifier.weight(1f),
            text = "Buy Now",
            onClick = onBuyNow
        )
    }
}

suspend fun handlerGetAllReviewsByOrderItem(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    reviewViewModel: ReviewViewModel,
    reviewList: MutableState<List<Review>>,
    orderItem: MutableState<OrderItem>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            reviewViewModel.getReviewsByOrderItemId(currentPage.value, orderItem.value.id ?: 0)
            totalPage.value = reviewViewModel.totalPage.value ?: 0
            reviewList.value = reviewViewModel.reviewsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = reviewViewModel.operationStatus,
    )
}

suspend fun handlerAddReview(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    reviewViewModel: ReviewViewModel,
    reviewList: MutableState<List<Review>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    review: MutableState<Review>,
    orderItem: MutableState<OrderItem>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            reviewViewModel.createReview(review.value)
            handlerGetAllReviewsByOrderItem(
                totalPage = totalPage,
                currentPage = currentPage,
                reviewViewModel = reviewViewModel,
                reviewList = reviewList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType,
                orderItem = orderItem
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = reviewViewModel.operationStatus,
        onSuccess = {
            if (reviewViewModel.createdReview.value == null) {
                alertType.value = AlertType.Duplication
                showErrorDialog.value = true
            } else {
                alertType.value = AlertType.Success
                showErrorDialog.value = true
            }
        },
        onFailure = {
            alertType.value = AlertType.Default
        }
    )
}

suspend fun handlerEditReview(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    reviewViewModel: ReviewViewModel,
    reviewList: MutableState<List<Review>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    review: MutableState<Review>,
    orderItem: MutableState<OrderItem>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            reviewViewModel.updateReview(review.value.id ?: 0, review.value)
            handlerGetAllReviewsByOrderItem(
                totalPage = totalPage,
                currentPage = currentPage,
                reviewViewModel = reviewViewModel,
                reviewList = reviewList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType,
                orderItem = orderItem
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = reviewViewModel.operationStatus,
    )
}