package org.example.project.presentation.screens.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.Dispatchers
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.CategoryCard
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes

class UserProviderView: Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val rootMaxWidth = remember { mutableStateOf(0) }
        val scope = rememberCoroutineScope{ Dispatchers.Default}
        val showLoadingOverlay = mutableStateOf(true)
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)

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
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = spacedBy(Size.Space.S300),
                    horizontalArrangement = spacedBy(Size.Space.S300, alignment = Alignment.CenterHorizontally),
                    maxItemsInEachRow = 10,
                    overflow = FlowRowOverflow.Visible
                ) {
                    for (i in 1..20) {
                        CategoryCard()
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