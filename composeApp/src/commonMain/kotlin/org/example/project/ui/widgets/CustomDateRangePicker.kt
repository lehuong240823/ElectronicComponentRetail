package org.example.project.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.datetime.*

data class DateRange(
    val startDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val endDate: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
) {
    val isValid: Boolean
        get() = startDate <= endDate // Equivalent to !startDate.isAfter(endDate)
}

// Helper data class to keep track of year and month together
data class YearMonth(val year: Int, val monthNumber: Int) {
    val month: Month = Month.entries[monthNumber - 1]

    fun getDaysInMonth(): Int {
        // Calculate days in month based on month and year
        return when (monthNumber) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (isLeapYear(year)) 29 else 28
            else -> throw IllegalArgumentException("Invalid month: $monthNumber")
        }
    }

    private fun isLeapYear(year: Int): Boolean {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
    }
}

@Composable
fun DateRangePicker(
    initialDateRange: DateRange = DateRange(),
    onDateRangeSelected: (DateRange) -> Unit,
    onDismiss: () -> Unit
) {
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val currentDate = remember { Clock.System.todayIn(TimeZone.currentSystemDefault()) }
    var currentYearMonth by remember {
        mutableStateOf(YearMonth(currentDate.year, currentDate.monthNumber))
    }
    var dateRange by remember { mutableStateOf(initialDateRange) }
    var selectionMode by remember { mutableStateOf(SelectionMode.START) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Header with selected dates
                DateRangeHeader(dateRange, selectionMode)

                Spacer(modifier = Modifier.height(16.dp))

                // Month navigation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        currentYearMonth = if (currentYearMonth.monthNumber == 1) {
                            YearMonth(currentYearMonth.year - 1, 12)
                        } else {
                            YearMonth(currentYearMonth.year, currentYearMonth.monthNumber - 1)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous Month"
                        )
                    }

                    Text(
                        text = "${months[currentYearMonth.monthNumber - 1]} ${currentYearMonth.year}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    IconButton(onClick = {
                        currentYearMonth = if (currentYearMonth.monthNumber == 12) {
                            YearMonth(currentYearMonth.year + 1, 1)
                        } else {
                            YearMonth(currentYearMonth.year, currentYearMonth.monthNumber + 1)
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next Month"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Day of week headers
                WeekdayHeader()

                // Calendar grid
                CalendarGrid(
                    yearMonth = currentYearMonth,
                    dateRange = dateRange,
                    onDateSelected = { date ->
                        dateRange = when (selectionMode) {
                            SelectionMode.START -> {
                                selectionMode = SelectionMode.END
                                DateRange(startDate = date, endDate = date)
                            }
                            SelectionMode.END -> {
                                if (date < dateRange.startDate) {
                                    // If end date is before start date, swap them
                                    selectionMode = SelectionMode.END
                                    DateRange(startDate = date, endDate = date)
                                } else {
                                    selectionMode = SelectionMode.START
                                    DateRange(startDate = dateRange.startDate, endDate = date)
                                }
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }

                    Button(
                        onClick = {
                            if (dateRange.isValid) {
                                onDateRangeSelected(dateRange)
                                onDismiss()
                            }
                        },
                        enabled = dateRange.isValid
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}

@Composable
private fun DateRangeHeader(
    dateRange: DateRange,
    selectionMode: SelectionMode
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Select Date Range",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = if (selectionMode == SelectionMode.START) Color.Blue.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "Start Date",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text =  formatDate(dateRange.startDate),
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = if (selectionMode == SelectionMode.END) Color.Blue.copy(alpha = 0.5f) else Color.Gray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "End Date",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Text(
                    text = formatDate(dateRange.endDate),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun WeekdayHeader() {
    val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")

    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { day ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun CalendarGrid(
    yearMonth: YearMonth,
    dateRange: DateRange,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDay = LocalDate(yearMonth.year, yearMonth.monthNumber, 1)
    val firstDayOfWeek = firstDay.dayOfWeek.ordinal // Sunday as 0, Monday as 1, etc.
    val daysInMonth = yearMonth.getDaysInMonth()
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    // Create grid cells (including empty cells for alignment)
    val days = List(firstDayOfWeek) { null } + (1..daysInMonth).map { day ->
        LocalDate(yearMonth.year, yearMonth.monthNumber, day)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.wrapContentHeight()
    ) {
        items(days) { date ->
            DayCell(
                date = date,
                isToday = date == today,
                isSelected = date != null && (date == dateRange.startDate || date == dateRange.endDate),
                isInRange = date != null &&
                        (date > dateRange.startDate && date < dateRange.endDate),
                onDateSelected = { if (date != null) onDateSelected(date) }
            )
        }
    }
}

@Composable
private fun DayCell(
    date: LocalDate?,
    isToday: Boolean,
    isSelected: Boolean,
    isInRange: Boolean,
    onDateSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(CircleShape)
            .background(
                when {
                    isSelected -> Color.Blue
                    isInRange -> Color.Blue.copy(alpha = 0.3f)
                    else -> Color.Transparent
                }
            )
            .then(
                if (isToday && !isSelected) {
                    Modifier.border(1.dp, Color.Blue, CircleShape)
                } else {
                    Modifier
                }
            )
            .clickable(enabled = date != null) { onDateSelected() },
        contentAlignment = Alignment.Center
    ) {
        date?.let {
            Text(
                text = it.dayOfMonth.toString(),
                color = when {
                    isSelected -> Color.White
                    else -> Color.Black
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

// Helper function to format dates in a user-friendly way
private fun formatDate(date: LocalDate): String {
    val monthNames = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun",
        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )
    val monthName = monthNames[date.monthNumber - 1]
    return "$monthName ${date.dayOfMonth}, ${date.year}"
}

enum class SelectionMode {
    START, END
}