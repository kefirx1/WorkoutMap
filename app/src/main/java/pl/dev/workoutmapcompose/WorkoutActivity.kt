package pl.dev.workoutmapcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.factories.WorkoutViewModelFactory
import pl.dev.workoutmapcompose.ui.screenWorkout.MainWorkout
import pl.dev.workoutmapcompose.ui.screenWorkout.WorkoutViewModel

class WorkoutActivity : ComponentActivity() {


    private lateinit var viewModel: WorkoutViewModel

    override fun onResume() {
        super.onResume()

        setContent {
            viewModel = viewModel(factory = WorkoutViewModelFactory(application))
            MainWorkout(
                instance = this,
                viewModel = viewModel
            )
        }
    }
}