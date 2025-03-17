package org.example.project.presentation.components.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import org.example.project.domain.model.Category
import org.example.project.domain.model.Transaction
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun CategoryTable(
    headers: List<String> = listOf("Category ID", "Image", "Name", "Delete", "Edit", "View More"),
    weights: List<Float> = listOf(1f, 2f, 3f, 1f, 1f, 1f),
    color: ButtonColor = Themes.Light.primaryLayout,
    categoryList: MutableState<List<Category>> = mutableStateOf(listOf(Category()))
) {
    Form(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(color = color.defaultBackground!!, shape = RectangleShape),
            verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                headers.forEachIndexed { idx, header ->
                    BodyText(
                        text = header,
                        modifier = Modifier
                            .weight(if (weights.isNotEmpty() && weights[idx] != null) weights[idx] else 1f),
                        style = Typography.Style.BodyStrong,
                    )
                }
            }
            Divider()

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Size.Padding.Xl)
            ) {

            }
        }
    }
}

@Composable
fun CategoryRow(
    weights: List<Float>,
    row: List<String>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        row.forEachIndexed { idx, cell ->

        }
    }
}