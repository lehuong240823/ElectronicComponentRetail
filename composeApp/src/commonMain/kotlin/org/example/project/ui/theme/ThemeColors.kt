package org.example.project.ui.theme

import androidx.compose.ui.graphics.Color

data class ThemeColors(
    val background: BackgroundColors? = null,
    val text: TextColors? = null,
    val border: BorderColors? = null,
    val icon: IconColors? = null
)

data class ButtonColor(
    val defaultBackground: Color? = null,
    val hoverBackground: Color? = null,
    val disabledBackground: Color? = null,
    val border: Color? = null,
    val primaryText: Color? = null,
    val secondaryText: Color? = null,
    val icon: Color? = null,
)

data class BackgroundColors(
    val default: Color? = null,
    val hover: Color? = null,
    val secondary: Color? = null,
    val secondaryHover: Color? = null,
    val tertiary: Color? = null,
    val tertiaryHover: Color? = null,

    val disabled: Color? = null,
    val scrim: Color? = null,
    val blanket: Color? = null,
    val overlay: Color? = null,
    val measurement: Color? = null,
)

data class TextColors(
    val default: Color? = null,
    val secondary: Color? = null,
    val tertiary: Color? = null,

    val onDisabled: Color? = null,
    val scrim: Color? = null,
    val blanket: Color? = null,
    val overlay: Color? = null,
    val measurement: Color? = null,

    val onNeutral: Color? = null,
    val onNeutralSecondary: Color? = null,
    val onNeutralTertiary: Color? = null,

    val onBrand: Color? = null,
    val onBrandSecondary: Color? = null,
    val onBrandTertiary: Color? = null,

    val onPositive: Color? = null,
    val onPositiveSecondary: Color? = null,
    val onPositiveTertiary: Color? = null,

    val onWarning: Color? = null,
    val onWarningSecondary: Color? = null,
    val onWarningTertiary: Color? = null,

    val onDanger: Color? = null,
    val onDangerSecondary: Color? = null,
    val onDangerTertiary: Color? = null,
)

data class BorderColors(
    val default: Color? = null,
    val secondary: Color? = null,
    val tertiary: Color? = null,
)

data class IconColors(
    val default: Color? = null,
    val secondary: Color? = null,
    val tertiary: Color? = null,

    val onDisabled: Color? = null,
    val scrim: Color? = null,
    val blanket: Color? = null,
    val overlay: Color? = null,
    val measurement: Color? = null,

    val onNeutral: Color? = null,
    val onNeutralSecondary: Color? = null,
    val onNeutralTertiary: Color? = null,

    val onBrand: Color? = null,
    val onBrandSecondary: Color? = null,
    val onBrandTertiary: Color? = null,

    val onPositive: Color? = null,
    val onPositiveSecondary: Color? = null,
    val onPositiveTertiary: Color? = null,

    val onWarning: Color? = null,
    val onWarningSecondary: Color? = null,
    val onWarningTertiary: Color? = null,

    val onDanger: Color? = null,
    val onDangerSecondary: Color? = null,
    val onDangerTertiary: Color? = null,
)