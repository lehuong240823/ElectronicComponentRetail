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
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_sort_ascending
import electroniccomponentretail.composeapp.generated.resources.ic_sort_descending
import kotlinx.coroutines.Dispatchers
import org.example.project.SessionData
import org.example.project.core.enums.AccountRoleType
import org.example.project.domain.model.Account
import org.example.project.domain.model.Product
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.ImageAddDialog
import org.example.project.presentation.components.card.CategoryCard
import org.example.project.presentation.components.card.ProductItem
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.common.CustomIconToggleButton
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.components.input.CustomRangerSlider
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.input.RatingToggleGroup
import org.example.project.presentation.components.input.SearchBar
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.pushWithLimitScreen

class ProductList : Screen {
    @Composable
    override fun Content() {
        val currentAccount = SessionData.getCurrentAccount()
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val currentPage = remember { mutableStateOf(1) }
        val totalPage = remember { mutableStateOf(14) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val priceAsc = remember { mutableStateOf(true) }
        val rateAsc = remember { mutableStateOf(true) }
        val searchKeyword = remember { mutableStateOf("") }
        val priceRange = remember { mutableStateOf(0f..100f) }
        val rating = remember { mutableStateOf(0) }
        val showAddNewProductDialog = remember { mutableStateOf(false) }
        val productList = remember { mutableStateOf(listOf(Product(id = 1))) }

        AddNewProductDialog(showAddNewProductDialog = showAddNewProductDialog)

        ColumnBackground(
            rootMaxWidth = rootMaxWidth
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Themes.Light.primaryLayout.defaultBackground!!)
                    .padding(Size.Space.S800),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S1600)
            ) {
                CategoryFlowRow(
                    rootMaxWidth = rootMaxWidth
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
                        onSearchBarClick = {},
                        productList = productList,
                        totalPage = totalPage,
                        currentPage = currentPage,
                        showAddNewProductDialog = showAddNewProductDialog
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
    showAddNewProductDialog: MutableState<Boolean>
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
            currentPage = currentPage
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryFlowRow(
    rootMaxWidth: MutableState<Int> = remember { mutableStateOf(0) },
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
            for (i in 1..20) {
                CategoryCard()
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
    navigator: Navigator?,
    productList: MutableState<List<Product>>,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>
) {
    FlowRow(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S600),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
        overflow = FlowRowOverflow.Visible,
    ) {
        productList.value.forEach { product ->
            ProductItem(
                modifier = Modifier.wrapContentSize()
                    .clickable(
                        enabled = true,
                        onClick = {
                            pushWithLimitScreen(navigator, ProductDetail())
                        },
                        role = Role.Button,
                        indication = ripple(bounded = true),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                //product = product
            )
        }
    }
    if (totalPage.value > 0) {
        Pagination(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            totalPage = totalPage,
            currentPage = currentPage,
            onCurrentPageChange = {}
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddNewProductDialog(
    showAddNewProductDialog: MutableState<Boolean>,
) {
    ImageAddDialog(
        title = "Add New Product",
        showImageAddDialog = showAddNewProductDialog,
        onUploadButtonClick = {},
        onConfirmation = {},
        content = {
            InputField(
                placeHolder = "Name",
                value = "",
                onValueChange = {}
            )
            InputField(
                placeHolder = "Description",
                singleLine = false,
                maxLines = 3,
                value = "",
                onValueChange = {}
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                InputField(
                    modifier = Modifier.weight(1f),
                    placeHolder = "Price",
                    value = "",
                    onValueChange = {}
                )
                InputField(
                    modifier = Modifier.weight(1f),
                    placeHolder = "Stock",
                    value = "",
                    onValueChange = {}
                )
            }
            ExposedDropdownInputField(
                modifier = Modifier.weight(1f),
                placeholder = "Category",
            )
            ExposedDropdownInputField(
                modifier = Modifier.weight(1f),
                placeholder = "Provider",
            )
            ExposedDropdownInputField(
                modifier = Modifier.weight(1f),
                placeholder = "Status",
            )
        }
    )
}