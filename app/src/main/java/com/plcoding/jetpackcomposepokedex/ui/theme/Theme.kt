package com.plcoding.jetpackcomposepokedex.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = gray200,
    background = gray900,
    onBackground = gray50,
    surface = gray600,
    onSurface = gray50
)

private val LightColorPalette = lightColors(
    primary = gray400,
    background = gray50,
    onBackground = gray900,
    surface = gray50,
    onSurface = gray900
)

@Composable
fun JetpackComposePokedexTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}