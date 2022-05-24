package pl.dev.workoutmapcompose.ui.utils

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dev.workoutmapcompose.WorkoutActivity
import pl.dev.workoutmapcompose.ui.screenDashboard.DashboardViewModel
import pl.dev.workoutmapcompose.ui.theme.mainFamily

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

@Suppress("FunctionName")
@Composable
fun DashboardProgressList(
    viewModel: DashboardViewModel
){
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colors.primary)
            .fillMaxHeight()
    ) {
        items(count = viewModel.exercisesProgressListResult.value!!.size) { exerciseIndex ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = TextModifier.convertExerciseNameToBetterText(viewModel.exercisesListResult.value[exerciseIndex]),
                    fontFamily = mainFamily,
                    fontSize = 15.sp,
                    color = MaterialTheme.typography.caption.color,
                    textAlign = TextAlign.Center
                )

                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .fillMaxWidth()
                )

                if (viewModel.exercisesProgressListResult.value!![exerciseIndex].isNotEmpty()) {

                    viewModel.exercisesProgressListResult.value!![exerciseIndex].forEach {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = DateTimeFunctionalities.convertDateInSecToDateString(
                                    it.dateOfWorkout.toInt()
                                ),
                                color = MaterialTheme.typography.caption.color,
                                fontFamily = mainFamily,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .fillMaxWidth(0.4f),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = TextModifier.convertExerciseProgressListToBetterText(
                                    it.setsList
                                ),
                                color = MaterialTheme.typography.caption.color,
                                fontFamily = mainFamily,
                                fontSize = 10.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }

                        Spacer(
                            modifier = Modifier
                                .height(10.dp)
                                .fillMaxWidth()
                        )

                    }

                }else{
                    Text(
                        text = "-",
                        fontFamily = mainFamily,
                        fontSize = 15.sp,
                        color = MaterialTheme.typography.caption.color
                    )
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )
                }

                Divider(color = Color.Black)

            }
        }
    }
}
