package org.example.project.presentation.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.format
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun Table(
    headers: List<String>,
    weights: List<Float>,
    textAligns: List<TextAlign>? = emptyList(),
    color: ButtonColor = Themes.Light.primaryLayout,
    tableHeader: @Composable () -> Unit = {
        TableHeader(
            headers = headers,
            weights = weights,
            textAligns = textAligns
        )
    },
    tableRowsContent: @Composable ColumnScope.() -> Unit
) {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(color = color.defaultBackground!!, shape = RectangleShape),
            verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
        ) {
            tableHeader()
            Divider()
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
            ) {
                tableRowsContent()
            }
        }
    }
}

@Composable
fun TopTableTemplate(
    title: String,
    showAddNewDialog: MutableState<Boolean>,
){
    Form(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            BodyText(
                text = title,
                style = Typography.Style.Heading6
            )
            CustomButton(
                modifier = Modifier.defaultMinSize(minWidth = 80.dp),
                icon = Icons.Outlined.Add,
                isIconFirst = true,
                text = "Add $title",
                onClick = { showAddNewDialog.value = true }
            )
        }
    }
}