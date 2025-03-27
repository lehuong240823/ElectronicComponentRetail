package org.example.project.presentation.components.common

import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Themes

@Composable
fun Navigator(
    tabTitles: List<String> = listOf(),
    selectedTabIndex: MutableState<Int>,
    currentPage: MutableState<Int> = mutableStateOf(0),
    color: ButtonColor = Themes.Light.tab
) {
    TabRow(
        selectedTabIndex = selectedTabIndex.value,
        backgroundColor = color.defaultBackground!!
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = { BodyText(text = title) },
                selected = selectedTabIndex.value == index,
                selectedContentColor = color.primaryText!!,
                unselectedContentColor = color.secondaryText!!,
                onClick = {
                    selectedTabIndex.value = index
                    currentPage.value = 0
                }
            )
        }
    }
}

@Composable
fun ScrollableNavigator(
    tabTitles: List<String> = listOf(),
    selectedTabIndex: MutableState<Int>,
    currentPage: MutableState<Int> = mutableStateOf(0),
    color: ButtonColor = Themes.Light.tab
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex.value,
        backgroundColor = color.defaultBackground!!,
        edgePadding = 0.dp,
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = { BodyText(text = title) },
                selected = selectedTabIndex.value == index,
                selectedContentColor = color.primaryText!!,
                unselectedContentColor = color.secondaryText!!,
                onClick = { selectedTabIndex.value = index
                    currentPage.value = 0 }
            )
        }
    }
}