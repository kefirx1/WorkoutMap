package pl.dev.workoutmapcompose.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.WorkoutActivity
import pl.dev.workoutmapcompose.ui.screenWorkout.WorkoutViewModel
import pl.dev.workoutmapcompose.ui.theme.Purple500
import pl.dev.workoutmapcompose.ui.theme.mainFamily


fun exitSettings(
    instance: Activity
){
    instance.finish()
}


@Composable
fun WorkoutHeader(
    screenName: String,
    instance: WorkoutActivity,
    viewModel: WorkoutViewModel
){
    var openWorkoutEndDialog by remember {
        mutableStateOf(false)
    }

    if(openWorkoutEndDialog) {
        openWorkoutEndDialog = DialogAlerts.workoutEndDialogAlert(
            instance = instance,
            viewModel = viewModel
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
                exitSettings(instance)
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

