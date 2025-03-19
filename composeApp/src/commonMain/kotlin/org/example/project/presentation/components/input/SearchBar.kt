package org.example.project.presentation.components.input

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchKeyword: MutableState<String> = mutableStateOf(""),
    onClick: () -> Unit
) {
    InputField(
        modifier = modifier,
        value = searchKeyword.value,
        placeHolder = "Search",
        onValueChange = {
            searchKeyword.value = it
        },
        shape = RoundedCornerShape(50),
        trailingIcon = {
            IconButton(onClick = onClick, modifier = Modifier.clearAndSetSemantics {}) {
                Icon(
                    Icons.Outlined.Search,
                    null,
                )
            }
        },
    )
}