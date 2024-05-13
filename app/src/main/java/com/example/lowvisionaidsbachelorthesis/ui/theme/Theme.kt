package com.example.lowvisionaidsbachelorthesis.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.material.Typography

private val DarkColorPalette = darkColors(
    primary = Black,
    primaryVariant = Gray,
    secondary = White,
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = Gray,
    secondary = Black,
)

val typography = Typography()

@Composable
fun PrimaryTheme(content: @Composable () -> Unit) {
    val colors = DarkColorPalette

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun FragmentTheme(content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}
