package org.example.project.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Facebook
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.elcom
import org.example.project.presentation.components.common.BodyText
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography
import org.jetbrains.compose.resources.painterResource

@Composable
fun Footer(
    color: ButtonColor = Themes.Light.brandLayout
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .background(color = color.defaultBackground!!)
            .padding(Size.Space.S800),
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S400)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(Size.Space.S600),
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                modifier = Modifier.height(24.dp),
                painter = painterResource(Res.drawable.elcom),
                contentDescription = "Logo",
                tint = color.icon!!
            )
            Row(
                modifier = Modifier.wrapContentSize(),
                horizontalArrangement = Arrangement.spacedBy(space = Size.Space.S400, alignment = Alignment.Start)
            ) {
                IconButton(
                    modifier = Modifier.clearAndSetSemantics { },
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Facebook,
                        contentDescription = "Facebook",
                        tint = color.icon
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Email",
                        tint = color.icon
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Phone,
                        contentDescription = "Phone",
                        tint = color.icon
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.Map,
                        contentDescription = "Map",
                        tint = color.icon
                    )
                }
            }
        }
        FooterLinkList(
            modifier = Modifier.weight(1f)
        ) {
            BodyText(
                modifier = Modifier.padding(bottom = Size.Space.S400),
                text = "Shop",
                color = color,
                style = Typography.Style.BodyStrong
            )
            BodyText(
                text = "New Arrivals",
                color = color
            )
            BodyText(
                text = "Best Sellers",
                color = color
            )
            BodyText(
                text = "Categories",
                color = color
            )
            BodyText(
                text = "Deals & Offers",
                color = color
            )
        }
        FooterLinkList(
            modifier = Modifier.weight(1f)
        ) {
            BodyText(
                modifier = Modifier.padding(bottom = Size.Space.S400),
                text = "Customer Service",
                style = Typography.Style.BodyStrong,
                color = color
            )
            BodyText(
                text = "Order Tracking",
                color = color
            )
            BodyText(
                text = "Returns & Refunds",
                color = color
            )
            BodyText(
                text = "Shipping Info",
                color = color
            )
            BodyText(
                text = "FAQs",
                color = color
            )
        }
        FooterLinkList(
            modifier = Modifier.weight(1f)
        ) {
            BodyText(
                modifier = Modifier.padding(bottom = Size.Space.S400),
                text = "Company",
                style = Typography.Style.BodyStrong,
                color = color
            )
            BodyText(
                text = "About Us",
                color = color
            )
            BodyText(
                text = "Careers",
                color = color
            )
            BodyText(
                text = "Terms & Policies",
                color = color
            )
            BodyText(
                text = "Contact Us",
                color = color
            )
        }
    }
}

@Composable
fun FooterLinkList(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Size.Space.S300)
    ) {
        content()
    }
}