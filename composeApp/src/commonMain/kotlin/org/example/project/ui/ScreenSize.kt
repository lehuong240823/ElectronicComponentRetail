package org.example.project.ui

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ScreenSize(val minWidthDp: Dp) {
    Compact(0.dp),       // Phones (< 600dp)
    Medium(600.dp),      // Small Tablets, Foldables (600dp - 839dp)
    Expanded(840.dp);    // Large Tablets, Desktops (≥ 840dp)

    companion object {
        fun fromDevice(widthDp: Dp): ScreenSize {
            return when {
                widthDp < Medium.minWidthDp -> Compact
                widthDp < Expanded.minWidthDp -> Medium
                else -> Expanded
            }
        }
    }
}

fun Modifier.forCompact() = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    when (ScreenSize.fromDevice(constraints.maxWidth.toDp())) {
        ScreenSize.Compact -> {
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }

        ScreenSize.Medium -> {
            layout(0, 0) {}
        }

        ScreenSize.Expanded -> {
            layout(0, 0) {}
        }
    }
}

fun Modifier.forCompactAndMedium() = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    when (ScreenSize.fromDevice(constraints.maxWidth.toDp())) {
        ScreenSize.Compact -> {
            layout(0, 0) {}
        }

        ScreenSize.Medium -> {
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }

        ScreenSize.Expanded -> {
            layout(0, 0) {}
        }
    }
}

fun Modifier.forExpanded() = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    when (ScreenSize.fromDevice(constraints.maxWidth.toDp())) {
        ScreenSize.Compact -> {
            layout(0, 0) {}
        }

        ScreenSize.Medium -> {
            layout(0, 0) {}
        }

        ScreenSize.Expanded -> {
            layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }
    }
}

