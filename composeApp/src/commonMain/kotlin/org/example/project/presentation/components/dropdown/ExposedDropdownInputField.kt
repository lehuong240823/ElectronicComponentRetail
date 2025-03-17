package org.example.project.presentation.components.dropdown

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.semantics.clearAndSetSemantics
import org.example.project.presentation.components.input.InputField

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownInputField(
    options: List<String> = listOf("aaa", "b"),
    textFieldValue: String? = null
) {
    val focusRequester = remember { FocusRequester() }
    var expanded by remember { mutableStateOf(false) }
    var textFieldValue by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        InputField(
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
            },
            trailingIcon = {
                IconButton(onClick = {}, modifier = Modifier.clearAndSetSemantics {}) {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        "Trailing icon for exposed dropdown menu",
                        Modifier.rotate(if (expanded) 180f else 360f)
                    )
                }
            },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options
                .filter { if (!textFieldValue.isNullOrEmpty()) it.startsWith(textFieldValue) else true }
                .forEach { option ->
                    DropdownMenuItem(
                        content = { Text(option, style = MaterialTheme.typography.body1) },
                        onClick = {
                            textFieldValue = option
                            expanded = false
                        },
                    )
                }
        }
    }
}