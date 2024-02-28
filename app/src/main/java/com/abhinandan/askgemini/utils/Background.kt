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
        Color(0xFFFF3EC2),
        Color(0xFFED7D5B),
    )
)

val horizontalBackground = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF0202F8),
        Color(0xFF046AA1),
        Color(0xFF5D65F3),
        Color(0xFF039BE5),
        Color(0xFFC89EFD),
    )
)