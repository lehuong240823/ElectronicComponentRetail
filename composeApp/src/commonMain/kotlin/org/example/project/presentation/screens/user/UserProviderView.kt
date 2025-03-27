package org.example.project.presentation.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.core.enums.AlertType
import org.example.project.data.api.ProviderApi
import org.example.project.data.repository.ProviderRepository
import org.example.project.domain.model.Provider
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.CategoryCard
import org.example.project.presentation.components.card.ProviderCard
import org.example.project.presentation.components.common.AlertDialog
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.screens.administrator.handlerGetAllProviders
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.viewmodel.ProviderViewModel

class UserProviderView: Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val currentPage = remember { mutableStateOf(0) }
        val totalPage = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val showLoadingOverlay = mutableStateOf(true)
        val showErrorDialog = mutableStateOf(false)
        val alertType = mutableStateOf(AlertType.Default)
        val providerViewModel = ProviderViewModel(ProviderRepository(ProviderApi()))
        val providerList: MutableState<List<Provider>> = mutableStateOf(emptyList())

        scope.launch {
            handlerGetAllProviders(
                totalPage = totalPage,
                currentPage = currentPage,
                providerViewModel = providerViewModel,
                providerList = providerList,
                showLoadingOverlay = showLoadingOverlay,
                showErrorDialog = showErrorDialog,
                alertType = alertType
            )
        }

        AlertDialog(
            alertType = alertType,
            showDialog = showErrorDialog
        )

        ColumnBackground(
            rootMaxWidth = rootMaxWidth,
            showLoadingOverlay = showLoadingOverlay
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = Themes.Light.primaryLayout.defaultBackground!!)
                    .padding(Size.Space.S800),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S1600),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = spacedBy(Size.Space.S300),
                    horizontalArrangement = spacedBy(Size.Space.S300, alignment = Alignment.CenterHorizontally),
                    maxItemsInEachRow = 10,
                    overflow = FlowRowOverflow.Visible
                ) {
                    providerList.value.forEach { provider ->
                        ProviderCard(
                            modifier = Modifier.fillMaxRowHeight(),
                            provider = provider
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