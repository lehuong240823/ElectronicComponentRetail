package org.example.project.ui.widgets

import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePickerDialog(
    onDismiss: () -> Unit,
    onDateSelected: (startDate: Long?, endDate: Long?) -> Unit
) {
    val currentDateTime: LocalDateTime =
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val startMillis = currentDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = startMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    // Lấy ngày bắt đầu và kết thúc từ state
                    val startDate = dateRangePickerState.selectedStartDateMillis
                    val endDate = dateRangePickerState.selectedEndDateMillis

                    // Gọi callback với dữ liệu đã chọn
                    onDateSelected(startDate, endDate)
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState
        )
    }
}