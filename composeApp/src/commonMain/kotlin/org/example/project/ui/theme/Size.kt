package org.example.project.ui.theme

import androidx.compose.ui.unit.dp

object Size {
    object Blur {
        val Blur100 = 4.dp;
    }
    object Depth {
        val Depth0 = 0.dp
        val Depth025 = (0.0625f * 16).dp
        val Depth100 = (0.25f * 16).dp
        val Depth200 = (0.5f * 16).dp
        val Depth400 = (1f * 16).dp
        val Depth800 = (2f * 16).dp
        val Depth1200 = (3f * 16).dp

        val Negative025 = (-0.0625f * 16).dp
        val Negative100 = (-0.25f * 16).dp
        val Negative200 = (-0.5f * 16).dp
        val Negative400 = (-1f * 16).dp
        val Negative800 = (-2f * 16).dp
        val Negative1200 = (-3f * 16).dp
    }

    object Icon {
        val Large = (2.5f * 16).dp
        val Medium = (2f * 16).dp
        val Small = (1.5f * 16).dp
    }

    object Padding {
        val Lg = (1f * 16).dp
        val Sm = (0.5f * 16).dp
        val Xl = (1.5f * 16).dp
        val Xs = (0.25f * 16).dp
        val Xxl = (2f * 16).dp
    }

    object Radius {
        val R100 = (0.25f * 16).dp
        val R200 = (0.5f * 16).dp
        val R400 = (1f * 16).dp
        val Full = (624.9375f * 16).dp
    }

    object Space {
        val S0 = 0.dp
        val S050 = (0.125f * 16).dp
        val S100 = (0.25f * 16).dp
        val S1200 = (3f * 16).dp
        val S150 = (0.375f * 16).dp
        val S1600 = (4f * 16).dp
        val S200 = (0.5f * 16).dp
        val S2400 = (6f * 16).dp
        val S300 = (0.75f * 16).dp
        val S400 = (1f * 16).dp
        val S4000 = (10f * 16).dp
        val S600 = (1.5f * 16).dp
        val S800 = (2f * 16).dp

        val Negative100 = (-0.25f * 16).dp
        val Negative200 = (-0.5f * 16).dp
        val Negative300 = (-0.75f * 16).dp
        val Negative400 = (-1f * 16).dp
        val Negative600 = (-1.5f * 16).dp
    }

    object Stroke {
        val Border = (0.0625f * 16).dp
        val FocusRing = (0.125f * 16).dp
    }
}