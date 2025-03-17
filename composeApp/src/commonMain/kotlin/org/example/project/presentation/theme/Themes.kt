package org.example.project.presentation.theme

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
            hoverBackground = Colors.Default.Light.Background.BrandDefault,
            border = Colors.Default.Light.Border.DefaultDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )

        val toggleButton = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.BrandTertiary,
            hoverBackground = Colors.Default.Light.Background.BrandDefault,
            border = Color.Transparent,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            secondaryText = Colors.Default.Light.Text.BrandOnBrand,
            icon = Colors.Default.Light.Icon.BrandTertiary
        )

        val navigationPill = ButtonColor(
            defaultBackground = Color.Transparent,
            hoverBackground = Colors.Default.Light.Background.DefaultDefaultHover,
            border = Color.Transparent,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )

        val paginationButton = ButtonColor(
            defaultBackground = Color.Transparent,
            hoverBackground = Colors.Default.Light.Background.BrandDefault,
            disabledBackground = Color.Transparent,
            border = Color.Transparent,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            hoverPrimaryText = Colors.Default.Light.Text.BrandOnBrand,
            disabledPrimaryText = Colors.Default.Light.Text.DefaultSecondary,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )

        val quantityButton = ButtonColor(
            defaultBackground = Color.Transparent,
            hoverBackground = Colors.Default.Light.Background.DefaultDefaultHover,
            border = Colors.Default.Light.Border.DefaultDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )

        val primaryLayout = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultDefault,
            border = Colors.Default.Light.Border.DefaultDefault,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            secondaryText = Colors.Default.Light.Text.DefaultSecondary,
            tertiaryText = Colors.Default.Light.Text.DefaultTertiary,
            icon = Colors.Default.Light.Icon.DefaultDefault
        )

        val secondaryLayout = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultSecondary,
            border = Colors.Default.Light.Border.DefaultSecondary,
            primaryText = Colors.Default.Light.Text.DefaultSecondary,
        )

        val brandLayout = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.BrandDefault,
            border = Colors.Default.Light.Border.BrandDefault,
            primaryText = Colors.Default.Light.Text.BrandOnBrand,
            secondaryText = Colors.Default.Light.Text.BrandOnBrandSecondary,
            icon = Colors.Default.Light.Icon.BrandOnBrand
        )

        val rangeSliderLayout = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.BrandDefault,
            hoverBackground = Colors.Default.Light.Background.BrandSecondary,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
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

        val ratingStar = ButtonColor(
            defaultBackground = Colors.Default.Light.Icon.DefaultSecondary,
            hoverBackground = Colors.Default.Light.Icon.BrandDefault
        )

        val primaryBrandTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.BrandDefault,
            hoverBackground = Colors.Default.Light.Background.BrandHover,
            primaryText = Colors.Default.Light.Text.BrandOnBrand,
            icon = Colors.Default.Light.Icon.BrandOnBrand,
        )

        val secondaryBrandTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.BrandTertiary,
            hoverBackground = Colors.Default.Light.Background.BrandTertiaryHover,
            primaryText = Colors.Default.Light.Text.BrandOnBrandTertiary,
            icon = Colors.Default.Light.Icon.BrandOnBrandTertiary,
        )

        val primaryNeutralTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultTertiary,
            hoverBackground = Colors.Default.Light.Background.DefaultTertiaryHover,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault,
        )

        val secondaryNeutralTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DefaultSecondary,
            hoverBackground = Colors.Default.Light.Background.DefaultSecondaryHover,
            primaryText = Colors.Default.Light.Text.DefaultDefault,
            icon = Colors.Default.Light.Icon.DefaultDefault,
        )

        val primaryPositiveTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.PositiveDefault,
            hoverBackground = Colors.Default.Light.Background.PositiveHover,
            primaryText = Colors.Default.Light.Text.PositiveOnPositive,
            icon = Colors.Default.Light.Icon.PositiveOnPositive,
        )

        val secondaryPositiveTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.PositiveSecondary,
            hoverBackground = Colors.Default.Light.Background.PositiveSecondaryHover,
            primaryText = Colors.Default.Light.Text.PositiveOnPositiveSecondary,
            icon = Colors.Default.Light.Icon.PositiveOnPositiveSecondary,
        )

        val primaryDangerTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DangerDefault,
            hoverBackground = Colors.Default.Light.Background.DangerHover,
            primaryText = Colors.Default.Light.Text.DangerOnDanger,
            icon = Colors.Default.Light.Icon.DangerOnDanger,
        )

        val secondaryDangerTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.DangerSecondary,
            hoverBackground = Colors.Default.Light.Background.DangerSecondaryHover,
            primaryText = Colors.Default.Light.Text.DangerOnDangerSecondary,
            icon = Colors.Default.Light.Icon.DangerOnDangerSecondary,
        )

        val primaryWarningTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.WarningDefault,
            hoverBackground = Colors.Default.Light.Background.WarningHover,
            primaryText = Colors.Default.Light.Text.WarningOnWarning,
            icon = Colors.Default.Light.Icon.WarningOnWarning,
        )

        val secondaryWarningTag = ButtonColor(
            defaultBackground = Colors.Default.Light.Background.WarningSecondary,
            hoverBackground = Colors.Default.Light.Background.WarningSecondaryHover,
            primaryText = Colors.Default.Light.Text.WarningOnWarningSecondary,
            icon = Colors.Default.Light.Icon.WarningOnWarningSecondary,
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