package pl.dev.workoutmapcompose.ui.utils

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.WorkoutActivity

@Suppress("FunctionName")
@Composable
fun WorkoutHeader(
    screenName: String,
    instance: WorkoutActivity
){
    var openWorkoutEndDialog by remember {
        mutableStateOf(false)
    }

    if(openWorkoutEndDialog) {
        openWorkoutEndDialog = workoutEndDialogAlert(
            instance = instance
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                openWorkoutEndDialog = true
            }) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Back button",
                tint = MaterialTheme.typography.caption.color
            )
        }
        Text(
            text = screenName,
            style = MaterialTheme.typography.caption,
            fontSize = 30.sp
        )
    }

    Spacer(
        modifier = Modifier
            .background(color = Color.Black)
            .height(2.dp)
            .fillMaxWidth()
    )
}

@Suppress("FunctionName")
@Composable
fun HeaderComponent(
    screenName: String,
    instance: Activity
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                instance.finish()
            }) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "Back button",
                tint = MaterialTheme.typography.caption.color
            )
        }
        Text(
            text = screenName,
            style = MaterialTheme.typography.caption,
            fontSize = 30.sp
        )
    }

    Spacer(
        modifier = Modifier
            .background(color = Color.Black)
            .height(2.dp)
            .fillMaxWidth()
    )

}

