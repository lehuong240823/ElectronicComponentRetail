package org.example.project.presentation.components.dropdown

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.theme.Size


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {
        DefaultMenuItems(
            onEdit = onEdit,
            onDelete = onDelete,
        ) }
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
        }
    }
}

@Composable
fun ColumnScope.DefaultMenuItems(
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    DropdownMenuItem(
        content = { BodyText(text = "Edit") },
        onClick = onEdit,
    )
    DropdownMenuItem(
        content = { BodyText(text = "Delete") },
        onClick = onDelete,
    )
}