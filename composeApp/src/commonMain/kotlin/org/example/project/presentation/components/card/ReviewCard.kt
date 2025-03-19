package org.example.project.presentation.components.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Review
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.input.InputField
import org.example.project.presentation.components.input.RatingToggleGroup
import org.example.project.presentation.screens.AvatarBlock

@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    editable: Boolean = false,
    showMoreActionButton: Boolean = editable,
    review: Review? = null,
    editableReview: MutableState<Review>? = mutableStateOf(Review())
) {
    Form(
        modifier = modifier,
        maxWidth = 700.dp
    ) {
        AvatarBlock(
            showMoreActionButton = showMoreActionButton
        )
        RatingToggleGroup(
            enabled = editable
        )
        if (editable && editableReview != null) {
            InputField(
                value = editableReview.value.content?: "",
                onValueChange = { editableReview.value.content = it }
            )
        } else if(review != null) {
            BodyText(
                text = review.content?: ""
            )
        }
    }
}