package org.example.project.presentation.screens.administrator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import org.example.project.domain.model.Provider
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.table.Table
import org.example.project.presentation.theme.Size

class AdministratorVoucherView: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope{ Dispatchers.Default}
        val showLoadingOverlay = mutableStateOf(true)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)

        ColumnBackground {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VoucherTable()
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

@Composable
fun VoucherTable(
    headers: List<String> = listOf("Voucher ID", "Code", "Type", "Discount Value",
         "Min Order", "Max Discount", "Active", "Action"),
    //"Used Count","Max Uses", "Valid From", "Valid Until"
    weights: List<Float> = listOf(1f, 1f, 1f, 2f, 2f, 2f, 1f, 1f),
    textAligns: List<TextAlign> = listOf(
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
    ),
) {
    Table(
        headers = headers,
        weights = weights,
        textAligns = textAligns,
        tableRowsContent = {
            ProviderRow(
                weights = weights,
                provider = Provider()
            )
        }
    )
}