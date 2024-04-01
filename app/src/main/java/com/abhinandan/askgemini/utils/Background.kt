package com.abhinandan.askgemini.utils

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val linearBackground = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF1E88E5),
        Color(0xFF014404),
    ),
)

val linearBackground2 = Brush.linearGradient(
    colors = listOf(
        Color(0xFF03ABF9),
        Color(0xFF066BF8),
        Color(0xFF5126FA),
        Color(0xFFD835A4),
        Color(0xFFDE6743),
    )
)

val horizontalBackground = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF0202F8),
        Color(0xFF0E58F6),
        Color(0xFF06857A),
        Color(0xFF198D34),
        Color(0xFF01390A),
    )
)