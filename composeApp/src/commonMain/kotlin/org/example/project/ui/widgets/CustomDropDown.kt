package org.example.project.ui.widgets

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomDropdown(
    onChanged: (value: T) -> Unit,
    options: List<T>
) {
    var expanded by remember { mutableStateOf(false) }
    var selected: T by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        modifier = Modifier.wrapContentWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        TextField(
            value = "$selected",
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option.toString()) },
                    onClick = {
                        selected = option
                        expanded = false

                        onChanged(selected)
                    }
                )
            }
        }
    }
}