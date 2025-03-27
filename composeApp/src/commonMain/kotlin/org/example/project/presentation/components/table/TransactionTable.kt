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
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kotlinx.datetime.*
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import org.example.project.domain.model.Order
import org.example.project.domain.model.PaymentMethod
import org.example.project.domain.model.Transaction
import org.example.project.domain.model.TransactionStatus
import org.example.project.presentation.components.Form
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Divider
import org.example.project.presentation.components.common.Tag
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@Composable
fun TransactionTable(
    headers: List<String> = listOf("Transaction ID", "Order ID", "Payment Method", "Amount", "Status", "Transaction Time"),
    weights: List<Float> = listOf(1f, 1f, 1.5f, 1.5f, 1.5f, 1.5f),
    transactionList: MutableState<List<Transaction>>
) {

    val format = DateTimeComponents.Format {
        date(LocalDate.Formats.ISO);
        char(' ');
        time(LocalTime.Format {
            hour();
            char(':');
            minute(); char(':');
            second()
        })
    }
    val offset = UtcOffset.ZERO

    Table(
        headers = headers,
        weights = weights,
        tableRowsContent = {
            transactionList.value.forEach { transaction ->
                TransactionRow(
                    weights = weights,
                    row = listOf(
                        transaction.id.toString(),
                        transaction.order!!.id.toString(),
                        transaction.paymentMethodName!!,
                        transaction.order!!.amount!!.doubleValue().toString(),
                        transaction.transactionStatus!!.name!!,
                        transaction.transactionTime!!.format(format, offset)
                    )
                )
            }
        }
    )
}

@Composable
fun TransactionRow(
    weights: List<Float>,
    row: List<String>
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        row.forEachIndexed { idx, cell ->
            if (idx == 4) {
                var color: ButtonColor = Themes.Light.primaryBrandTag
                when(cell) {
                    "Pending" -> { color = Themes.Light.primaryWarningTag }
                    "Completed" -> { color = Themes.Light.primaryPositiveTag }
                    "Failed" -> { color = Themes.Light.primaryDangerTag }
                    "Cancelled" -> { color = Themes.Light.secondaryDangerTag }
                    "Refunded" -> { color = Themes.Light.secondaryPositiveTag }
                    "Processing" -> { color = Themes.Light.secondaryWarningTag }
                    "On hold" -> { color = Themes.Light.secondaryWarningTag }
                }
                Tag(
                    modifier = Modifier.weight(weights[idx]),
                    text = cell,
                    color = color
                )
            } else {
                BodyText(
                    modifier = Modifier.weight(weights[idx]),
                    text = cell,
                )
            }
        }
    }
}
