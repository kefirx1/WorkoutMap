package pl.dev.workoutmapcompose.ui.screenWorkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.dev.workoutmapcompose.WorkoutActivity
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.ui.components.HeaderComponent
import pl.dev.workoutmapcompose.ui.theme.BlueGray900


@Composable
fun MainWorkout(
    instance: WorkoutActivity,
    viewModel: WorkoutViewModel,
    trainingPlanIndex: Int
){

    viewModel.getTrainingPlansList()
    viewModel.getWorkoutHistory()

    val trainingPlan = viewModel.trainingPlansListResult.value!![trainingPlanIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderComponent(
            screenName = "TRENING - ${trainingPlan.planName}",
            instance = instance
        )




    }

}