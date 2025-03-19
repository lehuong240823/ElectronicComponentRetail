package org.example.project.presentation.components.dropdown

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        IconButton(
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        }
        ExposedDropdownMenu(
            modifier = Modifier.exposedDropdownSize(false)
                .padding(Size.Space.S200)
                .widthIn(min = 200.dp, max = 320.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            content()
            DropdownMenuItem(
                content = { Text("aa", style = Typography.Style.BodyBase) },
                onClick = {
                    //textFieldValue = option
                    //expanded = false
                },
            )
        }
    }
}