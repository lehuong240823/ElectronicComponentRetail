package org.example.project.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun Table(
    headers: List<String>,
    rows: List<List<String>>
) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            headers.forEach { header ->
                Text(
                    text = header,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }

        Column {
            rows.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEach { cell ->
                        Text(
                            text = cell,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            //style = MaterialTheme.typography.body2
                        )
                    }
                }
            }

        }
    }
}