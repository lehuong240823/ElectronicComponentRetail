package org.example.project.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.ripple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_sort_ascending
import electroniccomponentretail.composeapp.generated.resources.ic_sort_descending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.SessionData
import org.example.project.checkError
import org.example.project.core.enums.AccountRoleType
import org.example.project.core.enums.AlertType
import org.example.project.data.api.*
import org.example.project.data.repository.*
import org.example.project.domain.model.*
import org.example.project.executeSuspendFunction
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.ImageAddDialog
import org.example.project.presentation.components.card.CategoryCard
import org.example.project.presentation.components.card.ProductItem
import org.example.project.presentation.components.common.*
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.components.input.CustomRangerSlider
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.input.RatingToggleGroup
import org.example.project.presentation.components.input.SearchBar
import org.example.project.presentation.isExpanded
import org.example.project.presentation.screens.administrator.handlerGetAllCategories
import org.example.project.presentation.screens.administrator.handlerGetAllProviders
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.presentation.viewmodel.*
import org.example.project.pushWithLimitScreen

class ProductList : Screen {
    @Composable
    override fun Content() {
        val currentAccount = SessionData.getCurrentAccount()
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val currentPage = remember { mutableStateOf(0) }
        val totalPage = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val priceAsc = remember { mutableStateOf(true) }
        val rateAsc = remember { mutableStateOf(true) }
        val searchKeyword = remember { mutableStateOf("") }
        val priceRange = remember { mutableStateOf(0f..100f) }
        val rating = remember { mutableStateOf(0) }
        val showAddNewProductDialog = remember { mutableStateOf(false) }
        val showEditProductDialog = remember { mutableStateOf(false) }
        val showLoadingOverlay = mutableStateOf(true)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)
        val productViewModel = ProductViewModel(ProductRepository(ProductApi()))
        val productList = remember { mutableStateOf(emptyList<Product>()) }
        val categoryViewModel = CategoryViewModel(CategoryRepository(CategoryApi()))
        val cloudViewModel = CloudinaryViewModel(CloudinaryStorageRepository(CloudinaryStorageApi()))
        val productImageViewModel = ProductImageViewModel(ProductImageRepository(ProductImageApi()))
        val categoryList: MutableState<List<Category>> = mutableStateOf(emptyList())
        val newProduct = mutableStateOf(Product())
        val updateProduct = mutableStateOf(Product())
        val newProductImage = mutableStateOf(ProductImage())
        val updateProductImage = mutableStateOf(ProductImage())
        val imageByteArray = mutableStateOf(byteArrayOf())

        scope.launch {
            handlerGetAllCategories(
                totalPage = mutableStateOf(0),
                currentPage = mutableStateOf(0),
                categoryViewModel = categoryViewModel,
                categoryList = categoryList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )

            handlerGetAllProducts(
                totalPage = totalPage,
                currentPage = currentPage,
                productViewModel = productViewModel,
                productList = productList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }

        AlertDialog(
            alertType = alertType,
            showDialog = showErrorDialog
        )

        AddNewProductDialog(
            title = "Add",
            scope = scope,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            showAddNewProductDialog = showAddNewProductDialog,
            alertType = alertType,
            product = newProduct,
            onConfirmation = {
                when {
                    newProduct.value.name.isNullOrBlank() -> {
                        alertType.value = AlertType.ProductNameNull
                        showErrorDialog.value = true
                    }
                    newProduct.value.price == null -> {
                        alertType.value = AlertType.ProductPriceNull
                        showErrorDialog.value = true
                    }
                    newProduct.value.stock == null -> {
                        alertType.value = AlertType.ProductStockNull
                        showErrorDialog.value = true
                    }
                    newProduct.value.productStatus == null -> {
                        alertType.value = AlertType.ProductAvailableNull
                        showErrorDialog.value = true
                    }
                    else -> {
                        scope.launch {
                            handlerAddProduct(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                productViewModel = productViewModel,
                                productList = productList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                product = newProduct
                            )

                            addProductImage(
                                product = newProduct,
                                productImageViewModel = productImageViewModel,
                                cloudViewModel = cloudViewModel,
                                newProductImage = newProductImage,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType,
                                imageByteArray = imageByteArray
                            )
                            handlerGetAllProducts(
                                totalPage = totalPage,
                                currentPage = currentPage,
                                productViewModel = productViewModel,
                                productList = productList,
                                showLoadingOverlay = showLoadingOverlay,
                                showErrorDialog = showErrorDialog,
                                alertType = alertType
                            )


                            updateProductImage.value = ProductImage()
                            newProduct.value = Product()
                        }
                    }
                }
            },
            productImage = newProductImage,
            imageByteArray = imageByteArray,
            productImageViewModel = productImageViewModel,
        )

        AddNewProductDialog(
            title = "Edit",
            scope = scope,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            showAddNewProductDialog = showEditProductDialog,
            alertType = alertType,
            product = updateProduct,
            onConfirmation = {
                if (updateProduct.value.name != null && updateProduct.value.price != null
                    && updateProduct.value.stock != null
                ) {
                    scope.launch {
                        handlerEditProduct(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            productViewModel = productViewModel,
                            productList = productList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType,
                            product = updateProduct
                        )

                        updatedProductImage(
                            product = updateProduct,
                            productImageViewModel = productImageViewModel,
                            cloudViewModel = cloudViewModel,
                            updateProductImage = updateProductImage,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType,
                            imageByteArray = imageByteArray
                        )
                        handlerGetAllProducts(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            productViewModel = productViewModel,
                            productList = productList,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType
                        )
                        updateProduct.value = Product()
                        updateProductImage.value = ProductImage()
                    }
                } else {
                    alertType.value = AlertType.Null
                    showErrorDialog.value = true
                }
            },
            productImage = updateProductImage,
            imageByteArray = imageByteArray,
            productImageViewModel = productImageViewModel,
        )

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Themes.Light.primaryLayout.defaultBackground!!)
                    .padding(Size.Space.S800),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S1600)
            ) {
                CategoryFlowRow(
                    rootMaxWidth = rootMaxWidth,
                    categoryList = categoryList,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S1600)
                ) {
                    if (rootMaxWidth.value.isExpanded()) {
                        FilterMenu(
                            priceRange = priceRange,
                            rating = rating
                        )
                    }
                    SectionProductGrid(
                        navigator = navigator,
                        currentAccount = currentAccount,
                        priceAsc = priceAsc,
                        onPriceValueChange = { },
                        rateAsc = rateAsc,
                        onRateValueChange = {},
                        searchKeyword = searchKeyword,
                        onSearchBarClick = {
                            scope.launch {
                                handlerFindProductsByNameContainingIgnoreCase(
                                    totalPage = totalPage,
                                    currentPage = currentPage,
                                    productViewModel = productViewModel,
                                    productList = productList,
                                    showLoadingOverlay = showLoadingOverlay,
                                    showErrorDialog = showErrorDialog,
                                    alertType = alertType,
                                    searchKeyword = searchKeyword
                                )
                            }
                        },
                        productList = productList,
                        totalPage = totalPage,
                        currentPage = currentPage,
                        showAddNewProductDialog = showAddNewProductDialog,
                        updateProduct = updateProduct,
                        showEditProductDialog = showEditProductDialog,
                        scope = scope,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        alertType = alertType,
                        productViewModel = productViewModel,
                        productImageViewModel = productImageViewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterMenu(
    priceRange: MutableState<ClosedFloatingPointRange<Float>>,
    rating: MutableState<Int>
) {
    Form(
        maxWidth = 300.dp,
        padding = Size.Space.S400,
        space = Size.Space.S600
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
        ) {
            BodyText(text = "Category")
            ExposedDropdownInputField()
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
        ) {
            BodyText(text = "Provider")
            ExposedDropdownInputField()
        }
        CustomRangerSlider(
            label = "Price",
            showRange = true,
            range = priceRange,
            value = priceRange.value,
            valueRange = 0f..1000f,
            onValueChange = {
                priceRange.value = it.start..it.endInclusive
            }
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
        ) {
            BodyText(text = "Minimum Rating")
            RatingToggleGroup(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                rate = rating
            )
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
            maxItemsInEachRow = 2
        ) {
            CustomButton(
                modifier = Modifier.weight(1f),
                text = "Reset",
                color = Themes.Light.neutralButton,
                onClick = {}
            )
            CustomButton(
                modifier = Modifier.weight(1f),
                text = "Apply",
                onClick = {}
            )
        }
    }
}

@Composable
fun SectionProductGrid(
    scope: CoroutineScope,
    currentAccount: Account?,
    navigator: Navigator?,
    priceAsc: MutableState<Boolean> = mutableStateOf(true),
    onPriceValueChange: (Boolean) -> Unit,
    rateAsc: MutableState<Boolean> = mutableStateOf(true),
    onRateValueChange: (Boolean) -> Unit,
    searchKeyword: MutableState<String> = mutableStateOf(""),
    onSearchBarClick: () -> Unit,
    productList: MutableState<List<Product>>,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    showAddNewProductDialog: MutableState<Boolean>,
    updateProduct: MutableState<Product>,
    showEditProductDialog: MutableState<Boolean>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    productViewModel: ProductViewModel,
    productImageViewModel: ProductImageViewModel
    ) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S1200)
    )
    {
        FilterBar(
            currentAccount = currentAccount,
            priceAsc = priceAsc,
            onPriceValueChange = onPriceValueChange,
            rateAsc = rateAsc,
            onRateValueChange = onRateValueChange,
            searchKeyword = searchKeyword,
            onSearchBarClick = onSearchBarClick,
            showAddNewProductDialog = showAddNewProductDialog
        )
        ProductCardGrid(
            navigator = navigator,
            productList = productList,
            totalPage = totalPage,
            currentPage = currentPage,
            updateProduct = updateProduct,
            showEditProductDialog = showEditProductDialog,
            scope = scope,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType,
            productViewModel = productViewModel,
            productImageViewModel = productImageViewModel
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryFlowRow(
    rootMaxWidth: MutableState<Int> = remember { mutableStateOf(0) },
    categoryList: MutableState<List<Category>>,
    color: ButtonColor = Themes.Light.primaryLayout
) {
    var isExpanded by remember { mutableStateOf(false) }
    Form(
        modifier = Modifier.fillMaxWidth(),
        padding = Size.Space.S400,
        space = Size.Space.S400
    ) {
        BodyText(
            modifier = Modifier.align(Alignment.Start),
            text = "Category",
            style = Typography.Style.Heading6.merge(color = color.secondaryText ?: Color.Unspecified)
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = if (rootMaxWidth.value.isExpanded()) 10 else 4,
            maxLines = 1,
            overflow = if (isExpanded) FlowRowOverflow.Visible else FlowRowOverflow.Clip
        ) {
            categoryList.value.forEach { category ->
                CategoryCard(
                    category = category,
                )
            }
        }
        IconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { isExpanded = !isExpanded }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (isExpanded) 180f else 360f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterBar(
    currentAccount: Account?,
    priceAsc: MutableState<Boolean> = mutableStateOf(true),
    onPriceValueChange: (Boolean) -> Unit,
    rateAsc: MutableState<Boolean> = mutableStateOf(true),
    onRateValueChange: (Boolean) -> Unit,
    searchKeyword: MutableState<String> = mutableStateOf(""),
    onSearchBarClick: () -> Unit,
    showAddNewProductDialog: MutableState<Boolean>
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = Size.Space.S800, alignment = Alignment.CenterVertically),
        horizontalArrangement = Arrangement.spacedBy(space = Size.Space.S800)
    ) {
        SearchBar(
            modifier = Modifier.weight(0.5f).fillMaxRowHeight(),
            searchKeyword = searchKeyword,
            onClick = onSearchBarClick
        )
        FlowRow(
            modifier = Modifier.weight(0.5f).fillMaxRowHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.SpaceBetween,
            ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomIconToggleButton(
                    checked = priceAsc,
                    text = "Price",
                    trueIcon = Res.drawable.ic_sort_ascending,
                    falseIcon = Res.drawable.ic_sort_descending,
                    onValueChange = onPriceValueChange
                )
                CustomIconToggleButton(
                    checked = rateAsc,
                    text = "Rate",
                    trueIcon = Res.drawable.ic_sort_ascending,
                    falseIcon = Res.drawable.ic_sort_descending,
                    onValueChange = onRateValueChange
                )
            }

            AddProductButton(
                showAddNewProductDialog = showAddNewProductDialog,
                currentAccount = currentAccount
            )
        }
    }
}

@Composable
fun AddProductButton(
    showAddNewProductDialog: MutableState<Boolean>,
    currentAccount: Account?
) {
    if (AccountRoleType.Administrator.name == currentAccount?.accountRole?.name) {
        CustomButton(
            modifier = Modifier.defaultMinSize(minWidth = 80.dp),
            icon = Icons.Outlined.Add,
            isIconFirst = true,
            text = "Add Product",
            onClick = { showAddNewProductDialog.value = true }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColumnScope.ProductCardGrid(
    scope: CoroutineScope,
    navigator: Navigator?,
    productList: MutableState<List<Product>>,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    updateProduct: MutableState<Product>,
    showEditProductDialog: MutableState<Boolean>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    productViewModel: ProductViewModel,
    productImageViewModel: ProductImageViewModel,
    ) {
    FlowRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S600),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
        overflow = FlowRowOverflow.Visible,
    ) {
        productList.value.forEach { product ->
            val productImage = mutableStateOf(ProductImage())
            scope.launch {
                getProductImagesByProductId(
                    product = mutableStateOf(product),
                    productImageViewModel = productImageViewModel,
                    productImage = productImage,
                    showLoadingOverlay = showLoadingOverlay,
                    showErrorDialog = showErrorDialog,
                    alertType = alertType
                )
            }
            ProductItem(
                modifier = Modifier.wrapContentSize().fillMaxRowHeight()
                    .clickable(
                        enabled = true,
                        onClick = {
                            pushWithLimitScreen(navigator, ProductDetail(
                                product = product,
                                productImage = productImage.value,
                            ))
                        },
                        role = Role.Button,
                        indication = ripple(bounded = true),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                product = product,
                productImage = productImage,
                onEdit = {
                    updateProduct.value = product
                    showEditProductDialog.value = true
                },
                onDelete = {
                    scope.launch {
                        handlerDeleteProduct(
                            totalPage = totalPage,
                            currentPage = currentPage,
                            productList = productList,
                            product = mutableStateOf(product),
                            productViewModel = productViewModel,
                            showLoadingOverlay = showLoadingOverlay,
                            showErrorDialog = showErrorDialog,
                            alertType = alertType
                        )
                    }
                }
            )
        }
    }
    if (totalPage.value > 0) {
        Pagination(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            totalPage = totalPage,
            currentPage = currentPage,
            onCurrentPageChange = {
                scope.launch {
                    handlerGetAllProducts(
                        totalPage = totalPage,
                        currentPage = currentPage,
                        productViewModel = productViewModel,
                        productList = productList,
                        showLoadingOverlay = showLoadingOverlay,
                        showErrorDialog = showErrorDialog,
                        alertType = alertType
                    )
                }
            }
        )
    }
}

@Composable
fun AddNewProductDialog(
    title: String,
    scope: CoroutineScope,
    showAddNewProductDialog: MutableState<Boolean>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    onConfirmation: () -> Unit,
    product: MutableState<Product>,
    productImage: MutableState<ProductImage>,
    productImageViewModel: ProductImageViewModel,
    imageByteArray: MutableState<ByteArray>,
    //url:MutableState<String>
) {
    val categoryViewModel = CategoryViewModel(CategoryRepository(CategoryApi()))
    val categoryList: MutableState<List<Category>> = mutableStateOf(emptyList())
    val providerViewModel = ProviderViewModel(ProviderRepository(ProviderApi()))
    val providerList: MutableState<List<Provider>> = mutableStateOf(emptyList())
    val productStatusViewModel = ProductStatusViewModel(ProductStatusRepository(ProductStatusApi()))
    val productStatusList: MutableState<List<ProductStatus>> = mutableStateOf(emptyList())

    scope.launch {
        handlerGetAllCategories(
            totalPage = mutableStateOf(0),
            currentPage = mutableStateOf(0),
            categoryViewModel = categoryViewModel,
            categoryList = categoryList,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType
        )
        handlerGetAllProviders(
            totalPage = mutableStateOf(0),
            currentPage = mutableStateOf(0),
            providerViewModel = providerViewModel,
            providerList = providerList,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType
        )
        handlerGetAllProductStatuses(
            productStatusViewModel = productStatusViewModel,
            productStatusList = productStatusList,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType
        )
        getProductImagesByProductId(
            product = product,
            productImageViewModel = productImageViewModel,
            productImage = productImage,
            showLoadingOverlay = showLoadingOverlay,
            showErrorDialog = showErrorDialog,
            alertType = alertType
        )
    }
    ImageAddDialog(
        title = "$title New Product",
        showImageAddDialog = showAddNewProductDialog,
        onConfirmation = onConfirmation,
        scope = scope,
        imageByteArray = imageByteArray,
        url = mutableStateOf(productImage.value.url?:"") ,
        content = {
            InputField(
                label = "Name",
                placeHolder = "Name",
                value = product.value.name?:"",
                onValueChange = {
                    product.value = product.value.copy(name = it)
                }
            )
            InputField(
                label = "Description",
                placeHolder = "Description",
                singleLine = false,
                maxLines = 3,
                value = product.value.description?:"",
                onValueChange = {
                    product.value = product.value.copy(description = it)
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                InputField(
                    label = "Price",
                    modifier = Modifier.weight(1f),
                    placeHolder = "Price",
                    value = if(product.value.price != null) product.value.price?.toPlainString().toString() else "",
                    onValueChange = {
                        var change = it.filter { c -> c.isDigit() }
                        product.value = product.value.copy(price = if(!change.isNullOrEmpty()) change.toBigDecimal() else BigDecimal.ZERO)
                    }
                )
                InputField(
                    label = "Stock",
                    modifier = Modifier.weight(1f),
                    placeHolder = "Stock",
                    value = if(product.value.stock==null) "" else product.value.stock.toString(),
                    onValueChange = {
                        var change = it.filter { c -> !c.isLetter() }
                        if (!change.isNullOrEmpty()) {
                            product.value = product.value.copy(stock = change.toInt())
                        }
                    }
                )
            }

            ExposedDropdownInputField(
                modifier = Modifier.weight(1f),
                label = "Category",
                placeholder = "Category",
                options = categoryList.value.map { it.name ?: "" },
                textFieldValue = mutableStateOf(product.value.category?.name?:""),
                onValueChange = {
                    product.value = product.value.copy(
                        category = categoryList.value.find { type -> type.name == it }
                    )
                }
            )
            ExposedDropdownInputField(
                modifier = Modifier.weight(1f),
                label = "Provider",
                placeholder = "Provider",
                options = providerList.value.map { it.name ?: "" },
                textFieldValue = mutableStateOf(product.value.provider?.name?:""),
                onValueChange = {
                    product.value = product.value.copy(
                        provider = providerList.value.find { type -> type.name == it }
                    )
                }
            )
            ExposedDropdownInputField(
                modifier = Modifier.weight(1f),
                label = "Status",
                placeholder = "Status",
                options = productStatusList.value.map { it.name ?: "" },
                textFieldValue = mutableStateOf(product.value.productStatus?.name?:""),
                onValueChange = {
                    product.value = product.value.copy(
                        productStatus = productStatusList.value.find { type -> type.name == it }
                    )
                }
            )
        }
    )
}

suspend fun handlerGetAllProducts(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    productViewModel: ProductViewModel,
    productList: MutableState<List<Product>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            productViewModel.getAllProducts(currentPage.value)
            totalPage.value = productViewModel.totalPage.value ?: 0
            productList.value = productViewModel.productsList.value
        }
    )

    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productViewModel.operationStatus,
    )
}

suspend fun handlerAddProduct(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    productViewModel: ProductViewModel,
    productList: MutableState<List<Product>>,
    showLoadingOverlay: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    showErrorDialog: MutableState<Boolean>,
    product: MutableState<Product>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            productViewModel.createProduct(product.value)
            product.value = productViewModel.createdProduct.value?:Product()
                handlerGetAllProducts(
                totalPage = totalPage,
                currentPage = currentPage,
                productViewModel = productViewModel,
                productList = productList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productViewModel.operationStatus,
        onSuccess = {
            if (productViewModel.createdProduct.value == null) {
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

suspend fun handlerEditProduct(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    productViewModel: ProductViewModel,
    productList: MutableState<List<Product>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    product: MutableState<Product>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            productViewModel.updateProduct(product.value.id ?: 0, product.value)
            handlerGetAllProducts(
                totalPage = totalPage,
                currentPage = currentPage,
                productViewModel = productViewModel,
                productList = productList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productViewModel.operationStatus,
    )
}

suspend fun handlerDeleteProduct(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    productViewModel: ProductViewModel,
    productList: MutableState<List<Product>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    product: MutableState<Product>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            productViewModel.deleteProduct(product.value.id ?: 0)
            handlerGetAllProducts(
                totalPage = totalPage,
                currentPage = currentPage,
                productViewModel = productViewModel,
                productList = productList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productViewModel.operationStatus,
    )
}

suspend fun handlerGetAllProductStatuses(
    productStatusViewModel: ProductStatusViewModel,
    productStatusList: MutableState<List<ProductStatus>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            val titles = mutableListOf("All")
            productStatusViewModel.getAllProductStatuss(0)
            productStatusList.value = productStatusViewModel.productStatussList.value
        }
    )
    checkError(
        showErrorDialog = showErrorDialog,
        alertType = alertType,
        operationStatus = productStatusViewModel.operationStatus,
        onSuccess = {}
    )
}

suspend fun handlerFindProductsByNameContainingIgnoreCase(
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>,
    productViewModel: ProductViewModel,
    productList: MutableState<List<Product>>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    searchKeyword: MutableState<String>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            currentPage.value = 0
            productViewModel.findProductsByNameContainingIgnoreCase(currentPage.value, searchKeyword.value)
            productList.value = productViewModel.productsList.value
        }
    )
    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productViewModel.operationStatus,
    )
}

suspend fun getProductImagesByProductId(
    totalPage: MutableState<Int> = mutableStateOf(0),
    currentPage: MutableState<Int> = mutableStateOf(0),
    product: MutableState<Product>,
    productImageViewModel: ProductImageViewModel,
    //productImageList: MutableState<List<ProductImage>>,
    productImage: MutableState<ProductImage>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            productImageViewModel.getProductImagesByProductId(currentPage.value, product.value.id?:0)
            totalPage.value = productImageViewModel.totalPage.value ?: 0
            if(productImageViewModel.productImagesList.value.isNotEmpty()) {
                productImage.value = productImageViewModel.productImagesList.value.first()
            } else {
                productImage.value = ProductImage()
            }
        }
    )

    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productImageViewModel.operationStatus,
    )
}

suspend fun addProductImage(
    product: MutableState<Product>,
    productImageViewModel: ProductImageViewModel,
    cloudViewModel: CloudinaryViewModel,
    newProductImage: MutableState<ProductImage>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    imageByteArray: MutableState<ByteArray>,
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            val findImage = mutableStateOf(ProductImage())
            getProductImagesByProductId(
                product = product,
                productImageViewModel = productImageViewModel,
                productImage = findImage,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
            cloudViewModel.uploadImage(imageByteArray.value)
            newProductImage.value = newProductImage.value.copy(url = cloudViewModel.url.value,
                product = product.value)

            if(findImage.value.id == null) {
                productImageViewModel.createProductImage(newProductImage.value)

                newProductImage.value = productImageViewModel.createdProductImage.value!!
            }
        }
    )

    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productImageViewModel.operationStatus,
    )
}

suspend fun updatedProductImage(
    product: MutableState<Product>,
    productImageViewModel: ProductImageViewModel,
    cloudViewModel: CloudinaryViewModel,
    updateProductImage: MutableState<ProductImage>,
    showLoadingOverlay: MutableState<Boolean>,
    showErrorDialog: MutableState<Boolean>,
    alertType: MutableState<AlertType>,
    imageByteArray: MutableState<ByteArray>
) {
    executeSuspendFunction(
        showLoadingOverlay = showLoadingOverlay,
        function = {
            val findImage = mutableStateOf(ProductImage())
            getProductImagesByProductId(
                product = product,
                productImageViewModel = productImageViewModel,
                productImage = findImage,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
            updateProductImage.value = findImage.value
            cloudViewModel.uploadImage(imageByteArray.value)
            updateProductImage.value = updateProductImage.value.copy(url = cloudViewModel.url.value,
                product = product.value)

            if(findImage.value.id != null) {
                updateProductImage.value = updateProductImage.value.copy(id = findImage.value.id)
                updateProductImage.value.id?.let {
                    productImageViewModel.updateProductImage(
                        updateProductImage.value.id!!,
                        updateProductImage.value
                    )
                }
                updateProductImage.value = productImageViewModel.updatedProductImage.value!!
            } else {
                productImageViewModel.createProductImage(updateProductImage.value)
                updateProductImage.value = productImageViewModel.createdProductImage.value!!
            }

        }
    )

    checkError(
        alertType = alertType,
        showErrorDialog = showErrorDialog,
        operationStatus = productImageViewModel.operationStatus,
    )
}