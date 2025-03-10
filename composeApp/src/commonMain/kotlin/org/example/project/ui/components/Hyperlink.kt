package org.example.project.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import org.example.project.ui.theme.Typography

@Composable
fun Hyperlink(
    text: String,
    url: String = "",
    onClick: () -> Unit,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),

        text = buildAnnotatedString {
            val styleCenter = SpanStyle(
                //color = Color(0xff64B5F6),
                fontSize = Typography.Style.Hyperlink.fontSize,
                fontWeight = Typography.Style.Hyperlink.fontWeight,
                textDecoration = TextDecoration.Underline
            )

            if (url.isNotEmpty()) {
                withLink(LinkAnnotation.Url(url = url)) {
                    withStyle(
                        style = styleCenter
                    ) {
                        append(text)
                    }
                }
            } else {
                withStyle(
                    style = styleCenter
                ) {
                    append(text)
                }
            }
        }
    )
}