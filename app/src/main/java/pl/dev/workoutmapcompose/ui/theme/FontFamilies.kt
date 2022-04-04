package pl.dev.workoutmapcompose.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import pl.dev.workoutmapcompose.R

val mainFamily = FontFamily(
    Font(R.font.ubuntumono_bold, FontWeight.Bold),
    Font(R.font.ubuntumono_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.ubuntumono_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.ubuntumono_bold, FontWeight.Normal)
)