package org.example.project.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.Voucher
import org.example.project.ui.theme.ButtonColor
import org.example.project.ui.theme.Size
import org.example.project.ui.theme.Themes
import org.example.project.ui.theme.Typography

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun VoucherItem(
    voucher: Voucher? = null,
    onClick: () -> Unit,
    color: ButtonColor=  Themes.Light.voucherItem,
) {
    Row (
        modifier = Modifier.wrapContentHeight()
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
                text = "VOUCHERCODE1234",
                style = Typography.Style.BodyStrong.copy(color = color.primaryText!!)
            )
            Text(
                text = "20% off, up to \$10",
                style = Typography.Style.BodyText.copy(color = color.primaryText!!)
            )
            Text(
                text = "Expires: 2024-12-12",
                style = Typography.Style.BodyText.copy(color = color.secondaryText!!)
            )
            Text(
                text = "Remaining Uses: 20",
                style = Typography.Style.BodyText.copy(color = color.secondaryText!!)
            )
        }
        Spacer(Modifier.weight(1f))
            TextButton(
                text = "Use",
                onClick = { onClick }
            )

    }
}