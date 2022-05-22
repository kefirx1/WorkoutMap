package pl.dev.workoutmapcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val TypographyDark = Typography(
    caption = TextStyle(
        fontFamily = mainFamily,
        color = BlueGray50
    )
)

val TypographyLight = Typography(
    caption = TextStyle(
        fontFamily = mainFamily,
        color = BlueGray900
    )

)

val buttonsDashboard = TextStyle(
    fontFamily = mainFamily,
    fontSize = 15.sp,
    textAlign = TextAlign.Center
)

val buttonsSettings = TextStyle(
    fontFamily = mainFamily,
    fontSize = 20.sp
)