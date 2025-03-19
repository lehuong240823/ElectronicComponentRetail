package org.example.project.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import org.example.project.CURRENCY
import org.example.project.domain.model.Product
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomRoundedCorner
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun ProductItem(
    modifier: Modifier = Modifier.wrapContentSize(),
    padding: Dp = Size.Space.S400,
    space: Dp = Size.Space.S400,
    color: ButtonColor = Themes.Light.primaryLayout,
    unit: String = CURRENCY,
    showMoreActionButton: Boolean = true,
    product: Product = Product(name = "Product", price = 0.toBigDecimal())
) {
    Column(
        modifier = modifier.clip(shape = CustomRoundedCorner())
            .background(color = color.defaultBackground!!)
            .border(width = Size.Stroke.Border, color = color.border!!, shape = CustomRoundedCorner())
            .padding(padding)
            .widthIn(min = 120.dp, max = 230.dp),
        verticalArrangement = Arrangement.spacedBy(space),
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(Res.drawable.Image),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                BodyText(
                    text = product.name!!
                )
                BodyText(
                    text = "${unit}${product.price}",
                    style = Typography.Style.BodyStrong
                )
            }
            if(showMoreActionButton) {
                ExposedDropdownMenuButton(
                    icon = vectorResource(Res.drawable.ic_dots_vertical),
                    content = {}
                )
            }
        }
    }
}