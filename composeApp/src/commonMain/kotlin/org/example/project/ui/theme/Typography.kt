package org.example.project.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import electroniccomponentretail.composeapp.generated.resources.*
import electroniccomponentretail.composeapp.generated.resources.Res
import electroniccomponentretail.composeapp.generated.resources.inter_regular
import org.jetbrains.compose.resources.Font

object Typography {
    @Composable
    fun loadInterFontFamily(): FontFamily {
        return FontFamily(
            Font(resource = Res.font.inter_thin, weight = FontWeight.Thin),
            Font(resource = Res.font.inter_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
            Font(resource = Res.font.inter_extralight, weight = FontWeight.ExtraLight),
            Font(resource = Res.font.inter_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
            Font(resource = Res.font.inter_light, weight = FontWeight.Light),
            Font(resource = Res.font.inter_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
            Font(resource = Res.font.inter_regular, weight = FontWeight.Normal),
            Font(resource = Res.font.inter_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
            Font(resource = Res.font.inter_medium, weight = FontWeight.Medium),
            Font(resource = Res.font.inter_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
            Font(resource = Res.font.inter_semibold, weight = FontWeight.SemiBold),
            Font(resource = Res.font.inter_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
            Font(resource = Res.font.inter_bold, weight = FontWeight.Bold),
            Font(resource = Res.font.inter_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
            Font(resource = Res.font.inter_extrabold, weight = FontWeight.ExtraBold),
            Font(resource = Res.font.inter_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
            Font(resource = Res.font.inter_black, weight = FontWeight.Black),
            Font(resource = Res.font.inter_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic)
        )
    }

    object Primitive {

        object Family {
            val Mono = FontFamily.Monospace
            val Sans = FontFamily.SansSerif
            val Serif = FontFamily.Serif
        }

        object Scale {
            val S01 = (0.75f * 16).sp
            val S02 = (0.875f * 16).sp
            val S03 = (1f * 16).sp
            val S04 = (1.25f * 16).sp
            val S05 = (1.5f * 16).sp
            val S06 = (2f * 16).sp
            val S07 = (2.5f * 16).sp
            val S08 = (3f * 16).sp
            val S09 = (4f * 16).sp
            val S10 = (4.5f * 16).sp
        }

        object Weight {
            val Black = FontWeight.Black
            val BlackItalic = FontWeight.Black
            val Bold = FontWeight.Bold
            val BoldItalic = FontWeight.Bold
            val ExtraBold = FontWeight.ExtraBold
            val ExtraBoldItalic = FontWeight.ExtraBold
            val ExtraLight = FontWeight.ExtraLight
            val ExtraLightItalic = FontWeight.ExtraLight
            val Light = FontWeight.Light
            val LightItalic = FontWeight.Light
            val Medium = FontWeight.Medium
            val MediumItalic = FontWeight.Medium
            val Regular = FontWeight.Normal
            val RegularItalic = FontWeight.Normal
            val SemiBold = FontWeight.SemiBold
            val SemiBoldItalic = FontWeight.SemiBold
            val Thin = FontWeight.Thin
            val ThinItalic = FontWeight.Thin
        }
    }

    object Default {
        object Body {
            val FontFamily = Primitive.Family.Sans
            val FontWeightRegular = Primitive.Weight.Regular
            val FontWeightStrong = Primitive.Weight.SemiBold
            val FontSizeLarge = Primitive.Scale.S04
            val FontSizeMedium = Primitive.Scale.S03
            val FontSizeSmall = Primitive.Scale.S02
        }

        object Code {
            val FontFamily = Primitive.Family.Mono
            val FontWeight = Body.FontWeightRegular
            val FontSizeBase = Body.FontSizeMedium
            val FontSizeLarge = Body.FontSizeLarge
            val FontSizeSmall = Body.FontSizeSmall
        }

        object Heading {
            val FontFamily = Primitive.Family.Sans
            val FontWeight = Primitive.Weight.SemiBold
            val FontSizeBase = Primitive.Scale.S05
            val FontSizeLarge = Primitive.Scale.S06
            val FontSizeSmall = Primitive.Scale.S04
        }

        object Subheading {
            val FontFamily = Primitive.Family.Sans
            val FontWeight = Primitive.Weight.Regular
            val FontSizeLarge = Primitive.Scale.S05
            val FontSizeMedium = Primitive.Scale.S04
            val FontSizeSmall = Primitive.Scale.S03
        }

        object Subtitle {
            val FontFamily = Primitive.Family.Sans
            val FontWeight = Primitive.Weight.Regular
            val FontSizeBase = Primitive.Scale.S06
            val FontSizeLarge = Primitive.Scale.S07
            val FontSizeSmall = Primitive.Scale.S05
        }

        object Title {
            object Hero {
                val FontFamily = Primitive.Family.Sans
                val FontWeight = Primitive.Weight.Bold
                val FontSize = Primitive.Scale.S10
            }

            object Page {
                val FontFamily = Primitive.Family.Sans
                val FontWeight = Primitive.Weight.Bold
                val FontSizeBase = Primitive.Scale.S08
                val FontSizeLarge = Primitive.Scale.S09
                val FontSizeSmall = Primitive.Scale.S07
            }
        }
    }

    object Style {
        val TitleHero = TextStyle(
            //fontFamily = Default.Title.Hero.FontFamily,
            fontWeight = Default.Title.Hero.FontWeight,
            fontSize = Default.Title.Hero.FontSize
        )

        val TitlePage = TextStyle(
            //fontFamily = Default.Title.Page.FontFamily,
            fontWeight = Default.Title.Page.FontWeight,
            fontSize = Default.Title.Page.FontSizeBase
        )

        val Subtitle = TextStyle(
            //fontFamily = Default.Subtitle.FontFamily,
            fontWeight = Default.Subtitle.FontWeight,
            fontSize = Default.Subtitle.FontSizeBase
        )

        val Heading = TextStyle(
            //fontFamily = Default.Heading.FontFamily,
            fontWeight = Default.Heading.FontWeight,
            fontSize = Default.Heading.FontSizeBase
        )

        val Subheading = TextStyle(
            //fontFamily = Default.Subheading.FontFamily,
            fontWeight = Default.Subheading.FontWeight,
            fontSize = Default.Subheading.FontSizeMedium
        )

        val BodyBase = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightRegular,
            fontSize = Default.Body.FontSizeMedium
        )

        val BodyStrong = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightStrong,
            fontSize = Default.Body.FontSizeMedium
        )

        val BodyEmphasis = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightRegular,
            fontSize = Default.Body.FontSizeMedium
        )

        val BodyLink = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightRegular,
            fontSize = Default.Body.FontSizeMedium
        )

        val BodySmall = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightRegular,
            fontSize = Default.Body.FontSizeSmall
        )

        val BodySmallStrong = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightStrong,
            fontSize = Default.Body.FontSizeSmall
        )

        val SingleLineBodyBase = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightRegular,
            fontSize = Default.Body.FontSizeMedium
        )

        val BodyCode = TextStyle(
            //fontFamily = Default.Code.FontFamily,
            fontWeight = Default.Code.FontWeight,
            fontSize = Default.Code.FontSizeBase
        )

        val SingleLineBodySmallStrong = TextStyle(
            //fontFamily = Default.Body.FontFamily,
            fontWeight = Default.Body.FontWeightStrong,
            fontSize = Default.Body.FontSizeSmall
        )

        val UtilitiesComponentNotesRegular = TextStyle(
            //fontFamily = Default.Code.FontFamily,
            fontWeight = Primitive.Weight.Regular,
            fontSize = Primitive.Scale.S03
        )

        val UtilitiesComponentNotesBold = TextStyle(
            //fontFamily = Default.Code.FontFamily,
            fontWeight = Primitive.Weight.Bold,
            fontSize = Primitive.Scale.S03
        )


        val Heading1 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )

        val Heading2 = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )

        val Heading3 = TextStyle(
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp
        )

        val Heading4 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        )

        val Heading5 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        )

        val Heading6 = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        )

        val BodyText = BodyBase

        val ButtonText = SingleLineBodyBase

        val InputField = SingleLineBodyBase

        val Caption = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )

        val Label = BodyBase

        val Hyperlink = BodyLink
    }
}
