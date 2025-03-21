package org.example.project.presentation.components.dropdown

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.semantics.clearAndSetSemantics
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Themes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownInputField(
    modifier: Modifier = Modifier.wrapContentSize(),
    options: List<String> = listOf(),
    textFieldValue: MutableState<String> = mutableStateOf(""),
    onValueChange: (String) -> Unit = { textFieldValue.value = it },
    placeholder: String? = null,
    enabled: Boolean = true,
    colors: ButtonColor = Themes.Light.textField
) {
    val focusRequester = remember { FocusRequester() }
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        InputField(
            modifier = modifier,
            enabled = enabled,
            value = textFieldValue.value,
            onValueChange = {
                textFieldValue.value = it
                onValueChange(it)
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
            placeHolder = placeholder,
            colors = colors
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options
                .filter { if (!textFieldValue.value.isNullOrEmpty()) it.startsWith(textFieldValue.value) else true }
                .forEach { option ->
                    DropdownMenuItem(
                        content = {
                            BodyText(
                                text = option,
                            )
                        },
                        onClick = {
                            textFieldValue.value = option
                            onValueChange(option)
                            expanded = false
                        },
                    )
                }
        }
    }
}