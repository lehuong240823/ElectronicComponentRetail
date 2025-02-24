package org.example.project.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.theme.Size

@Composable
fun Form(
    modifier: Modifier = Modifier.wrapContentSize().width(320.dp),
    horizontalArrangement: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(Size.Space.S600),
        verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
        horizontalAlignment = horizontalArrangement
    ) {
        content()
    }
}