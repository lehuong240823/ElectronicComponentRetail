package org.example.project.presentation.screens.administrator

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import org.example.project.domain.model.Provider
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.Pagination
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.components.table.Table
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.vectorResource

class AdministratorProviderView : Screen {
    @Composable
    override fun Content() {
        val totalPage = mutableStateOf(0)
        val currentPage = mutableStateOf(0)

        ColumnBackground {
            Column(
                modifier = Modifier.fillMaxSize().padding(Size.Space.S600),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S400),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProviderTable()
                if(totalPage.value > 0) {
                    Pagination(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        totalPage = totalPage,
                        currentPage = currentPage,
                        onCurrentPageChange = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ProviderTable(
    headers: List<String> = listOf("Provider ID", "Name", "Type", "Email", "Phone Number", "Address", "Action"),
    weights: List<Float> = listOf(2f, 1f, 2f, 1f, 2f, 2f, 1f),
    textAligns: List<TextAlign> = listOf(
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Left,
        TextAlign.Center
    ),
) {
    Table(
        headers = headers,
        weights = weights,
        textAligns = textAligns,
        tableRowsContent = {
            ProviderRow(
                weights = weights,
                provider = Provider()
            )
        }
    )
}

@Composable
fun ProviderRow(
    weights: List<Float>,
    provider: Provider
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyText(
            modifier = Modifier.weight(weights[0]),
            text = provider.id.toString(),
            style = Typography.Style.BodyText,
        )
        BodyText(
            modifier = Modifier.weight(weights[1]),
            text = provider.name ?: "",
            style = Typography.Style.BodyText,
        )
        BodyText(
            modifier = Modifier.weight(weights[2]),
            text = provider.type ?: "",
            style = Typography.Style.BodyText,
        )
        BodyText(
            modifier = Modifier.weight(weights[3]),
            text = provider.email ?: "",
            style = Typography.Style.BodyText,
        )
        BodyText(
            modifier = Modifier.weight(weights[4]),
            text = provider.phoneNumber ?: "",
            style = Typography.Style.BodyText,
        )
        BodyText(
            modifier = Modifier.weight(weights[5]),
            text = provider.address ?: "",
            style = Typography.Style.BodyText,
        )
        Box(Modifier.weight(weights[6]))
        {
            ExposedDropdownMenuButton(
                modifier = Modifier.align(Alignment.Center),
                icon = vectorResource(Res.drawable.ic_dots_vertical),
                content = {}
            )
        }
    }
}