package org.example.project.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconAndTextButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Button(onClick = {}) {
        androidx.compose.foundation.layout.Row {
            Icon(icon, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("some thing")
        }
    }
}