package org.example.project.presentation.components.card

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.CurrentScreen
import org.example.project.CURRENCY
import org.example.project.domain.model.Voucher
import org.example.project.presentation.components.common.CustomRoundedCorner
import org.example.project.presentation.components.common.CustomButton
import org.example.project.presentation.theme.ButtonColor
import org.example.project.presentation.theme.Size
import org.example.project.presentation.theme.Themes
import org.example.project.presentation.theme.Typography

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun VoucherItem(
    modifier: Modifier = Modifier,
    voucher: Voucher? = null,
    onClick: () -> Unit,
    color: ButtonColor=  Themes.Light.voucherItem,
) {
    Row (
        modifier = modifier.wrapContentHeight()
            .clip(shape = CustomRoundedCorner())
            .background( color = color.defaultBackground!!)
            .border(width = Size.Stroke.Border, color = color.border!!, shape = CustomRoundedCorner())
            .padding(Size.Space.S400),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Size.Space.S400)
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            imageVector = Icons.Outlined.Image,
            contentDescription = null
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(Size.Space.S200),
        ) {
            Text(
                text = voucher?.code?:"",
                style = Typography.Style.BodyStrong.copy(color = color.primaryText!!)
            )
            var discount = if(voucher?.voucherType?.name == "PERCENTAGE") "${voucher.discountValue}%" else "${CURRENCY}${voucher?.discountValue}"
            Text(
                text = "Get ${discount} off",
                style = Typography.Style.BodyText.copy(color = color.primaryText!!)
            )
            Text(
                text = "Expires: ".plus(voucher?.validUntil?: "None"),
                style = Typography.Style.BodyText.copy(color = color.secondaryText!!)
            )
            Text(
                text = "Remaining Uses: ".plus(voucher?.maxUses?.minus(voucher.usedCount?:0)),
                style = Typography.Style.BodyText.copy(color = color.secondaryText!!)
            )
        }
        Spacer(Modifier.weight(1f))
            /*CustomButton(
                text = "Use",
                onClick = onClick
            )*/

    }
}