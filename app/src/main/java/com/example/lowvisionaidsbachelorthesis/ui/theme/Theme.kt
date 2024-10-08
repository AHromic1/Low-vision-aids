package com.example.lowvisionaidsbachelorthesis.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.material.Typography

private val DarkColorPalette = darkColors(
    primary = Martinique,
    primaryVariant = Smoky,
    secondary = Linen,
)

private val LightColorPalette = lightColors(
    primary = Linen,
    primaryVariant = Smoky,
    secondary = Martinique,
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
