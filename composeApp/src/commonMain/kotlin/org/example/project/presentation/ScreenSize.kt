package org.example.project.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
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

@Composable
fun Int.toDp(): Dp = (this / LocalDensity.current.density).dp

@Composable
fun Int.isCompact(): Boolean {
    return ScreenSize.fromDevice(this.toDp()) == ScreenSize.Compact
}

@Composable
fun Int.isMedium(): Boolean {
    return ScreenSize.fromDevice(this.toDp()) == ScreenSize.Medium
}

@Composable
fun Int.isExpanded(): Boolean {
    return ScreenSize.fromDevice(this.toDp()) == ScreenSize.Expanded
}

@Composable
fun Int.isCompactOrMedium(): Boolean {
    var screen = ScreenSize.fromDevice(this.toDp())
    return screen == ScreenSize.Compact || screen == ScreenSize.Medium
}

fun Modifier.forExpanded(rootMaxWidth: Int) = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    when (ScreenSize.fromDevice(rootMaxWidth.toDp())) {
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

