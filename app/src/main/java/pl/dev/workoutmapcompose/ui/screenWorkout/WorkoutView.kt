package pl.dev.workoutmapcompose.ui.screenWorkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import pl.dev.workoutmapcompose.Convert
import pl.dev.workoutmapcompose.WorkoutActivity
import pl.dev.workoutmapcompose.ui.components.HeaderComponent


@Composable
fun MainWorkout(
    instance: WorkoutActivity,
    viewModel: WorkoutViewModel,
    trainingPlanIndex: Int
){

    viewModel.getTrainingPlansList()
    viewModel.getWorkoutHistory()

    val trainingPlan = viewModel.trainingPlansListResult.value!![trainingPlanIndex]
    val workoutHistory = viewModel.workoutHistoryResult.value!![trainingPlanIndex]
    viewModel.getExercisesProgressHistory(trainingPlan = trainingPlan)
    val progressHistory = viewModel.progressHistoryResult.value

    var currentExerciseIndex by remember {
        mutableStateOf(0)
    }
    var currentExerciseSet by remember {
        mutableStateOf(1)
    }
    var timerTicks by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while(true) {
            delay(1000)
            timerTicks++
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderComponent(
            screenName = "TRENING - ${trainingPlan.planName}",
            instance = instance
        )

        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = "Czas treningu: ${Convert.convertTimeInSecToTimeString(timeInSec = timerTicks)}",
            style = MaterialTheme.typography.caption,
            fontSize = 20.sp
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            //PHOTO
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .padding(top = 10.dp)
                    .border(width = 1.dp, color = Color.Black)
            ) {

            }

            Text(
                modifier = Modifier
                    .padding(top = 10.dp),
                text = Convert.convertExerciseNameToBetterView(trainingPlan.exercise[currentExerciseIndex].name),
                style = MaterialTheme.typography.caption,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = trainingPlan.exercise[currentExerciseIndex].type,
                style = MaterialTheme.typography.caption,
                fontSize = 15.sp
            )
            Text(
                modifier = Modifier
                    .padding(top = 5.dp),
                text = "$currentExerciseSet|${trainingPlan.exercise[currentExerciseIndex].numberOfSets}",
                style = MaterialTheme.typography.caption,
                fontSize = 15.sp
            )

        }

        TextButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                //TODO

                if(currentExerciseSet==trainingPlan.exercise[currentExerciseIndex].numberOfSets){
                    if (currentExerciseIndex<trainingPlan.exercise.size-1){
                        currentExerciseIndex++
                        currentExerciseSet = 1
                    }
                }else{
                    currentExerciseSet++
                }


            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary
            )
        ) {
            Text(
                text = "START",
                color = MaterialTheme.typography.caption.color,
                fontSize = 30.sp
            )
        }



    }


}

