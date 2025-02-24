package org.example.project.ui.theme

object Themes {
    object Light {
        val primaryButton = ButtonColor(
            background = Colors.Default.Light.Background.BrandDefault,
            border = Colors.Default.Light.Border.BrandDefault,
            text = Colors.Default.Light.Text.BrandOnBrand
        )
        val textField = ButtonColor(
            background = Colors.Default.Light.Background.DefaultDefault,
            border = Colors.Default.Light.Border.DefaultDefault,
            text = Colors.Default.Light.Text.DefaultDefault
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