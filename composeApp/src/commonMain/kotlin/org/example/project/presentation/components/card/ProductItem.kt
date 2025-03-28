package org.example.project.presentation.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import org.example.project.CURRENCY
import org.example.project.SessionData
import org.example.project.core.enums.AccountRoleType
import org.example.project.domain.model.Product
import org.example.project.domain.model.ProductImage
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomRoundedCorner
import org.example.project.presentation.components.dropdown.ExposedDropdownMenuButton
import org.example.project.presentation.isExpanded
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalResourceApi::class, ExperimentalLayoutApi::class)
@Composable
fun ProductItem(
    modifier: Modifier = Modifier.wrapContentSize(),
    padding: Dp = Size.Space.S400,
    space: Dp = Size.Space.S400,
    color: ButtonColor = Themes.Light.primaryLayout,
    unit: String = CURRENCY,
    showMoreActionButton: Boolean = true,
    product: Product = Product(name = "Product", price = 0.toBigDecimal()),
    productImage: MutableState<ProductImage>,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    rootMaxWidth: MutableState<Int>
) {
    Box(
        modifier = Modifier.wrapContentSize()
    ) {
    Column(
        modifier = modifier.clip(shape = CustomRoundedCorner())
            .background(color = color.defaultBackground!!)
            .border(width = Size.Stroke.Border, color = color.border!!, shape = CustomRoundedCorner())
            .padding(padding)
            .widthIn(min = 120.dp, max = if(rootMaxWidth.value.isExpanded()) 230.dp else 100.dp),
        verticalArrangement = Arrangement.spacedBy(space),
    ) {
        /*AsyncImage(
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
            model = if(productImage.value.url.isNullOrEmpty()) Res.getUri("composeResources/drawable/Image.png") else productImage.value.url,
            contentDescription = null,
        )*/

            AsyncImage(
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                model =  productImage.value.url,
                error = painterResource(Res.drawable.Image),
                placeholder = painterResource(Res.drawable.Image),
                contentDescription = null,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(Size.Space.S200)
            ) {
                BodyText(
                    text = product.name!!
                )
                BodyText(
                    text = "${unit}${product.price?.toPlainString()}",
                    style = Typography.Style.BodyStrong
                )
            }
        }
        if (SessionData.getCurrentAccount()?.accountRole?.name == AccountRoleType.Administrator.name) {
            ExposedDropdownMenuButton(
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                icon = vectorResource(Res.drawable.ic_dots_vertical),
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}