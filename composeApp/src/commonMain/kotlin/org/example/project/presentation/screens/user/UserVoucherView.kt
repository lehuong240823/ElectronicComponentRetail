package org.example.project.presentation.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import org.example.project.domain.model.Voucher
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.VoucherItem
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.screens.ProductList
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.pushWithLimitScreen

class UserVoucherView: Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope{ Dispatchers.Default}
        val showLoadingOverlay = mutableStateOf(true)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)
        val voucherList = mutableStateOf(emptyList<Voucher>())
        ColumnBackground(
            rootMaxWidth = rootMaxWidth
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Themes.Light.primaryLayout.defaultBackground!!)
                    .padding(Size.Space.S800),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S1600),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FlowRow(
                    modifier = Modifier.wrapContentSize(),
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
                ) {
                    voucherList.value.forEach { voucher ->
                        VoucherItem(
                            modifier = Modifier.weight(1f),
                            voucher = Voucher(),
                            onClick = {
                                pushWithLimitScreen(
                                    navigator = navigator, ProductList()
                                )
                            }
                        )
                    }
                }
                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {}
                    )
                }
            }
        }
    }
}