package org.example.project.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.model.Supplier
import org.example.project.ui.theme.Colors

@Composable
fun SupplierTable(
    actions: List<@Composable (String) -> Unit>? = null,
    data: List<Supplier> = listOf(),
    headers: List<String>,
) {
    LazyColumn(Modifier.fillMaxWidth().padding(16.dp)) {
        // HEADER
        item {
            Row(
                modifier = Modifier.fillMaxWidth().background(Colors.Primitive.Green1200)
                    .height(IntrinsicSize.Max)
            ) {
                headers.forEach { item -> TableCell(
                    fontColor = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    text = item
                )}
                if (!actions.isNullOrEmpty()) TableCell(
                    fontColor = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    text = "HÀNH ĐỘNG"
                )
            }
        }
        // DATA
        item {
            data.forEach { item ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)
                ) {
                    TableCell(text = item.id)
                    TableCell(text = item.name)
                    TableCell(text = item.address)
                    TableCell(text = item.phone)
                    if (!actions.isNullOrEmpty())
                        TableCell {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) { actions.forEach { action -> action(item.id) } }
                        }
                }
            }
        }
    }
}