package pl.dev.workoutmapcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val TypographyDark = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = BlueGray50
    ),
    caption = TextStyle(
        fontFamily = mainFamily,
        color = BlueGray50
    )

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val TypographyLight = Typography(
    caption = TextStyle(
        fontFamily = mainFamily,
        color = BlueGray900
    )
)

val buttonsDashboard = TextStyle(
    color = BlueGray50,
    fontFamily = mainFamily,
    fontSize = 15.sp,
    textAlign = TextAlign.Center
)

val buttonsSettings = TextStyle(
    color = BlueGray50,
    fontFamily = mainFamily,
    fontSize = 20.sp
)