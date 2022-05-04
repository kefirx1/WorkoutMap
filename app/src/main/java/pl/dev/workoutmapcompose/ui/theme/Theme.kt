package pl.dev.workoutmapcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BlueGray800,
    background = BlueGray900,
    secondary = BlueGray500,
    surface = BlueGray800
)

private val LightColorPalette = lightColors(
    primary = BlueGray200,
    background = BlueGray50,
    secondary = BlueGray500,
    surface = BlueGray200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WorkoutMapComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        typography = if (darkTheme) TypographyDark else TypographyLight,
        shapes = Shapes,
        content = content
    )
}