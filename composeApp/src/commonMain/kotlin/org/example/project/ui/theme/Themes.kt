package org.example.project.ui.theme

import androidx.compose.ui.graphics.Color

object Themes {
    object Light {
        val primaryButton = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.BrandDefault,
            hoverBackground = Colors.Default.Light.Background.BrandHover,
            border = Colors.Default.Light.Border.BrandDefault,
            primaryText = Colors.Default.Light.Text.BrandOnBrand,
            icon = Colors.Default.Light.Icon.BrandOnBrand
        )

        val neutralButton = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.NeutralTertiary,
            hoverBackground = Colors.Default.Light.Background.NeutralTertiaryHover,
            border = Colors.Default.Light.Border.NeutralSecondary,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )

        val subtleButton = ButtonColor(
            defaultBackground = Color.Transparent,
            border = Colors.Default.Light.Border.DefaultDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )

        val navigationPill = ButtonColor(
            defaultBackground = Color.Transparent,
            hoverBackground = Colors.Default.Light.Background.DefaultDefaultHover,
            border = Color.Transparent,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )


        val secondaryBrandTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.BrandTertiary,
            hoverBackground = Colors.Default.Light.Background.BrandTertiaryHover,
            border = Color.Transparent,
            primaryText = Colors.Default.Light.Text.BrandOnBrandTertiary,
            icon = Colors.Default.Light.Icon.BrandOnBrandTertiary
        )

        val primaryLayout = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultDefault,
            border = Colors.Default.Light.Border.DefaultDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            secondaryText = Colors.Default.Light.Text.DefaultSecondary,
        )

        val secondaryLayout = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultSecondary,
            border = Colors.Default.Light.Border.DefaultSecondary,
            primaryText = Colors.Default.Light.Text.DefaultSecondary,
        )

        val textField = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultDefault,
            border = Colors.Default.Light.Border.DefaultDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
        )

        val voucherItem = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultDefault,
            border = Colors.Default.Light.Border.DefaultDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            secondaryText = Colors.Default.Light.Text.DefaultSecondary
        )

        val tab = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultDefault,
            border = Colors.Default.Light.Border.BrandDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            secondaryText = Colors.Default.Light.Text.DefaultSecondary
        )

        val default = ThemeColors(
            background = BackgroundColors(
                default = Colors.Default.Light.Background.DefaultDefault,
                hover = Colors.Default.Light.Background.DefaultDefaultHover
            ),

            text = TextColors(
                default = Colors.Default.Light.Text.DefaultDefault,
                secondary = Colors.Default.Light.Text.DefaultSecondary,
                tertiary = Colors.Default.Light.Text.DefaultTertiary
            ),

            border = BorderColors(
                default = Colors.Default.Light.Border.DefaultDefault,
                secondary = Colors.Default.Light.Border.DefaultSecondary,
                tertiary = Colors.Default.Light.Border.DefaultTertiary
            ),

            icon = IconColors(
                default = Colors.Default.Light.Icon.DefaultDefault,
                secondary = Colors.Default.Light.Icon.DefaultSecondary,
                tertiary = Colors.Default.Light.Icon.DefaultTertiary
            )
        )
        val brand = ThemeColors(
            background = BackgroundColors(
                default = Colors.Default.Light.Background.BrandDefault,
                hover = Colors.Default.Light.Background.BrandHover
            ),

            text = TextColors(
                onBrand = Colors.Default.Light.Text.BrandOnBrand,
                onBrandSecondary = Colors.Default.Light.Text.BrandSecondary,
                onBrandTertiary = Colors.Default.Light.Text.BrandTertiary
            ),

            border = BorderColors(
                default = Colors.Default.Light.Border.DefaultDefault,
                secondary = Colors.Default.Light.Border.DefaultSecondary,
                tertiary = Colors.Default.Light.Border.DefaultTertiary
            ),

            icon = IconColors(
                default = Colors.Default.Light.Icon.DefaultDefault,
                secondary = Colors.Default.Light.Icon.DefaultSecondary,
                tertiary = Colors.Default.Light.Icon.DefaultTertiary
            )
        )
    }
}