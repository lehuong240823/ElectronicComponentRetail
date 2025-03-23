package org.example.project.utils

import kotlin.math.roundToInt

/**
 * Format a double value to display with two decimal places
 */
fun Double.toStringWithTwoDecimals(): String {
    val rounded = (this * 100).roundToInt() / 100.0
    val intValue = rounded.toInt()
    
    return if (rounded == intValue.toDouble()) {
        "$intValue.00"
    } else {
        val decimalPart = ((rounded - intValue) * 100).toInt()
        val formattedDecimal = if (decimalPart < 10) "0$decimalPart" else "$decimalPart"
        "$intValue.$formattedDecimal"
    }
}
