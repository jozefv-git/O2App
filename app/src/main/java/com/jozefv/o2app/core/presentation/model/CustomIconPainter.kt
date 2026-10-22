package com.jozefv.o2app.core.presentation.model

import androidx.compose.ui.graphics.painter.Painter

data class CustomIconPainter(
    val iconPainter: Painter,
    val iconDescription: String? = null,
    val onClick: () -> Unit = {},
)
