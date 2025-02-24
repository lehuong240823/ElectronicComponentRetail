package org.example.project.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.unit.dp
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_google
import org.example.project.ui.theme.Themes

@Composable
fun SideMenu(color: org.example.project.ui.theme.ButtonColor = Themes.Light.textField) {
    MaterialTheme {
        Form(horizontalArrangement = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.spacedBy(space = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.wrapContentHeight().fillMaxWidth().padding(0.dp, 16.dp)
                    .drawWithContent {
                        drawContent()
                        drawLine(
                            color = color.border!!,
                            start = androidx.compose.ui.geometry.Offset(0f, size.height + 24),
                            end = androidx.compose.ui.geometry.Offset(size.width, size.height + 24),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
            ) {
                IconButton(Res.drawable.ic_google)
                Text("Email")
            }
            Text("Edit profile")
            Text("Address")
            Text("Payment")
            Text("Order")
            Text("Transaction")
        }
    }

}
