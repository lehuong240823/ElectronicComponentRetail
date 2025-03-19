package org.example.project.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import electroniccomponentretail.composeapp.generated.resources.Image
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.blank_profile
import electroniccomponentretail.composeapp.generated.resources.ic_dots_vertical
import org.example.project.CURRENCY
import org.example.project.presentation.components.ColumnBackground
import org.example.project.presentation.components.card.ReviewCard
import org.example.project.presentation.components.common.BackButton
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.components.input.QuantityGroup
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.example.project.presentation.theme.Typography.Primitive
import org.jetbrains.compose.resources.painterResource

class ProductDetail : Screen {
    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        var rootMaxWidth by remember { mutableStateOf(0) }
        var color: ButtonColor = Themes.Light.primaryLayout
        val quantity = remember { mutableStateOf(1) }

        ColumnBackground(
            rootModifier = Modifier
                .onGloballyPositioned { coordinates ->
                    rootMaxWidth = coordinates.size.width
                }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(color = color.defaultBackground!!)
                    .padding(
                        top = Size.Space.S600,
                        start = Size.Space.S1600,
                        bottom = Size.Space.S1600,
                        end = Size.Space.S1600
                    ),
                verticalArrangement = Arrangement.spacedBy(Size.Space.S600)
            ) {
                BackButton()
                FlowRow(
                    maxItemsInEachRow = 2,
                    overflow = FlowRowOverflow.Visible,
                    horizontalArrangement = Arrangement.spacedBy(Size.Space.S1600),
                    verticalArrangement = Arrangement.spacedBy(Size.Space.S600)
                ) {
                    Image(
                        modifier = Modifier.weight(1f)
                            .heightIn(min = 300.dp, max = 420.dp),
                        painter = painterResource(Res.drawable.Image),
                        contentScale = ContentScale.FillWidth,
                        contentDescription = null
                    )
                    ProductInfoColumn(
                        modifier = Modifier.wrapContentHeight()
                            .weight(1f),
                        quantity = quantity
                    )
                    ProductDescriptionColumn(
                        modifier = Modifier.weight(1f)
                    )
                    ProductReiviewColumn()
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductInfoColumn(
    modifier: Modifier = Modifier,
    quantity: MutableState<Int>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Size.Space.S600)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Size.Space.S400)
        ) {
            BodyText(
                text = "Product",
                style = Typography.Style.Heading
            )
            Row(
                modifier = Modifier.wrapContentHeight()
            ) {
                BodyText(
                    text = CURRENCY,
                    style = Typography.Style.Subtitle
                        .merge(
                            fontSize = Typography.Default.Subtitle.FontSizeSmall,
                            fontWeight = Primitive.Weight.Bold
                            )
                )
                BodyText(
                    text = "50",
                    style = Typography.Style.TitlePage
                )
            }
        }
        QuantityGroup(quantity = quantity)
        ProductDetailButtonRow()
    }
}

@Composable
fun ProductDescriptionColumn(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        BodyText(
            text = "Product Description",
            style = Typography.Style.Heading
        )
        BodyText(
            text = "Product Product Description Product Description Product Description Product Description Product Description Description Product Description Product Description Product Description Product Description Product Description Product Description Product Description"
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductReiviewColumn(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        BodyText(
            text = "Product Review",
            style = Typography.Style.Heading
        )
        FlowRow(
            modifier = Modifier.wrapContentSize(),
            maxItemsInEachRow = 2,
            horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
            verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
        ) {
            ReviewCard(
                modifier = Modifier.weight(1f)
            )
            ReviewCard(
                modifier = Modifier.weight(1f)
            )
            ReviewCard(
                modifier = Modifier.weight(1f)
            )
            ReviewCard(
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun AvatarBlock(
    modifier: Modifier = Modifier,
    color: ButtonColor = Themes.Light.primaryLayout,
    showMoreActionButton: Boolean = true,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        Image(
            modifier = Modifier.size(40.dp)
                .clip(RoundedCornerShape(50)),
            painter = painterResource(Res.drawable.blank_profile),
            contentScale = ContentScale.FillWidth,
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            BodyText(
                text = "example@gmail.com",
                style = Typography.Style.BodyStrong
            )
            BodyText(
                text = "23/12/2024",
                style = Typography.Style.BodyBase.copy(color = color.secondaryText!!)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if(showMoreActionButton) {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_dots_vertical),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ProductDetailButtonRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S300),
    ) {
        CustomButton(
            modifier = Modifier.weight(1f),
            text = "Add to Cart",
            color = Themes.Light.neutralButton,
            onClick = {}
        )
        CustomButton(
            modifier = Modifier.weight(1f),
            text = "Buy Now",
            onClick = {}
        )
    }
}