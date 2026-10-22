package com.jozefv.o2app.core.presentation.util

import androidx.compose.ui.graphics.Color

fun Color.darkenBy(darkenBy: Float = 0.3f): Color {
    val darkenBy = darkenBy.coerceIn(0f, 1f)
    return copy(
        red = red * (1f - darkenBy),
        green = green * (1f - darkenBy),
        blue = blue * (1f - darkenBy)
    )
}