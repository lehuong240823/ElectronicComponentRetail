package org.example.project.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.ripple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_sort_ascending
import electroniccomponentretail.composeapp.generated.resources.ic_sort_descending
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.example.project.domain.model.Product
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.card.CategoryCard
import org.example.project.presentation.components.card.ProductItem
import org.example.project.presentation.components.common.*
import org.example.project.presentation.components.dropdown.ExposedDropdownInputField
import org.example.project.presentation.components.input.CustomRangerSlider
import org.example.project.presentation.components.input.RatingToggleGroup
import org.example.project.presentation.components.input.SearchBar
import org.example.project.presentation.theme.Colors
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.pushWithLimitScreen

class ProductList : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var rootMaxWidth by remember { mutableStateOf(0) }
        val priceAsc = remember { mutableStateOf(true) }
        val rateAsc = remember { mutableStateOf(true) }
        val searchKeyword = remember { mutableStateOf("") }
        val productList = remember { mutableStateOf(listOf(Product(id = 1))) }
        val currentPage = remember { mutableStateOf(1) }
        val totalPage = remember { mutableStateOf(14) }

        ColumnBackground(
            rootModifier = Modifier
                .onGloballyPositioned { coordinates ->
                    rootMaxWidth = coordinates.size.width
                }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Themes.Light.primaryLayout.defaultBackground!!)
                .padding(Size.Space.S800),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S1600)
            ) {
                CategoryFlowRow()
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S1600)
                ) {
                    FilterMenu()
                    SectionProductGrid(
                        navigator = navigator,
                        priceAsc = priceAsc,
                        onPriceValueChange = { },
                        rateAsc = rateAsc,
                        onRateValueChange = {},
                        searchKeyword = searchKeyword,
                        onSearchBarClick = {},
                        productList = productList,
                        totalPage = totalPage,
                        currentPage = currentPage,
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterMenu() {
    var priceRange = remember { mutableStateOf(0f..100f) }
    var rate = remember { mutableStateOf(0) }
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
                rate = rate
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
    navigator: Navigator?,
    priceAsc: MutableState<Boolean> = mutableStateOf(true),
    onPriceValueChange: (Boolean) -> Unit,
    rateAsc: MutableState<Boolean> = mutableStateOf(true),
    onRateValueChange: (Boolean) -> Unit,
    searchKeyword: MutableState<String> = mutableStateOf(""),
    onSearchBarClick: () -> Unit,
    productList: MutableState<List<Product>>,
    totalPage: MutableState<Int>,
    currentPage: MutableState<Int>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S1200)
    )
    {
        FilterBar(
            priceAsc = priceAsc,
            onPriceValueChange = onPriceValueChange,
            rateAsc = rateAsc,
            onRateValueChange = onRateValueChange,
            searchKeyword = searchKeyword,
            onSearchBarClick = onSearchBarClick
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
) {
    var isExpanded by remember { mutableStateOf(false) }
    Form(
       modifier = Modifier.fillMaxWidth(),
        padding = Size.Space.S400,
    ) {
        BodyText(
            modifier = Modifier.align(Alignment.Start),
            text = "Category",
            style = Typography.Style.Heading6
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            maxItemsInEachRow = 10,
            maxLines = 2,
            overflow = if(isExpanded) FlowRowOverflow.Visible else FlowRowOverflow.Clip
        ) {
            for(i in 0..30) {
                CategoryCard(
                    modifier = Modifier.fillMaxWidth(0.0990f)
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

@Composable
fun FilterBar(
    priceAsc: MutableState<Boolean> = mutableStateOf(true),
    onPriceValueChange: (Boolean) -> Unit,
    rateAsc: MutableState<Boolean> = mutableStateOf(true),
    onRateValueChange: (Boolean) -> Unit,
    searchKeyword: MutableState<String> = mutableStateOf(""),
    onSearchBarClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            searchKeyword = searchKeyword,
            onClick = onSearchBarClick
        )
        Spacer(Modifier.width(Size.Space.S800))
        Row(
            horizontalArrangement = Arrangement.spacedBy(Size.Space.S200)
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
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductCardGrid(
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
                        onClickLabel = null,
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

    Pagination(
        currentPage = currentPage,
        totalPage = totalPage,
        onCurrentPageChange = {

        }
    )
}