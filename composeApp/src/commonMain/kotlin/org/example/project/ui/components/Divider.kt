package org.example.project.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Divider(
    modifier: Modifier = Modifier.wrapContentSize(),
) {
    androidx.compose.material.Divider(modifier.padding(0.dp))
}