package org.example.project.presentation.components.common

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_arrow_left
import electroniccomponentretail.composeapp.generated.resources.ic_arrow_right
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.jetbrains.compose.resources.vectorResource

@Composable
fun Pagination(
    modifier: Modifier = Modifier,
    totalPage: MutableState<Int>,// = mutableStateOf(10),
    currentPage: MutableState<Int>,// = mutableStateOf(1)
    onCurrentPageChange: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S200),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var combinedRanges = mutableListOf(
            1..3,
            currentPage.value..currentPage.value,
            totalPage.value - 2..totalPage.value
        )
            .flatten()
            .toSet()
            .filter { it in 1..totalPage.value }
            .sorted()

        PaginationButton(
            text = "Previous",
            icon = vectorResource(Res.drawable.ic_arrow_left),
            isIconFirst = true,
            onClick = {
                if (currentPage.value > 1) currentPage.value--
                onCurrentPageChange()
            },
            enabled = currentPage.value != combinedRanges.first(),
            onValueChange = {},
        )

        combinedRanges.forEachIndexed { idx, pageNum ->
            if (idx > 0 && pageNum - combinedRanges[idx - 1] != 1) {
                Text(
                    modifier = Modifier.width(36.dp),
                    text = "...",
                    textAlign = TextAlign.Center
                )
            }

            PaginationButton(
                modifier = Modifier.width(36.plus(8.times(pageNum.toString().length)).dp) ,
                text = pageNum.toString(),
                onClick = {
                    currentPage.value = pageNum
                    /*combinedRanges = mutableListOf(
                        1..3,
                        currentPage.value..currentPage.value,
                        totalPage.value - 2..totalPage.value
                    )
                        .flatten()
                        .toSet()
                        .filter { it in 1..totalPage.value }
                        .sorted()*/
                    onCurrentPageChange()
                },
                value = currentPage.value == pageNum,
                onValueChange = {},
                color = if (currentPage.value == pageNum) Themes.Light.primaryButton else Themes.Light.paginationButton
            )
        }
        PaginationButton(
            text = "Next",
            icon = vectorResource(Res.drawable.ic_arrow_right),
            onClick = {
                if (currentPage.value < totalPage.value) currentPage.value++
                onCurrentPageChange()
            },
            enabled = currentPage.value != combinedRanges.last(),
            onValueChange = { },
        )
    }
}

@Composable
fun PaginationButton(
    modifier: Modifier = Modifier.wrapContentSize(),
    contentPadding: PaddingValues = PaddingValues(horizontal = Size.Space.S300, vertical = Size.Space.S200),
    onClick: () -> Unit,
    text: String,
    icon: ImageVector? = null,
    isFillMaxWidth: Boolean = false,
    isIconFirst: Boolean = false,
    textVisibility: Boolean = true,
    enabled: Boolean = true,
    value: Boolean = true,
    onValueChange: (Boolean) -> Unit,
    color: ButtonColor = Themes.Light.paginationButton,
) {
    CustomButton(
        modifier = modifier
            .toggleable(
                value = value,
                onValueChange = onValueChange,
                enabled = enabled,
                role = Role.Checkbox,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        text = text,
        icon = icon,
        isFillMaxWidth = isFillMaxWidth,
        textVisibility = textVisibility,
        isIconFirst = isIconFirst,
        enabled = enabled,
        contentPadding = contentPadding,
        color = color,
        onClick = onClick
    )
}