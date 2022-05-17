package pl.dev.workoutmapcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.factories.WorkoutViewModelFactory
import pl.dev.workoutmapcompose.ui.screenWorkout.MainWorkout
import pl.dev.workoutmapcompose.ui.screenWorkout.WorkoutViewModel
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme

class WorkoutActivity : ComponentActivity() {

    private lateinit var viewModel: WorkoutViewModel

    private var trainingPlanIndex = 0

    override fun onResume() {
        super.onResume()

        trainingPlanIndex = intent.getIntExtra("TRAINING_INDEX", 0)

        setContent {
            WorkoutMapComposeTheme {
                viewModel = viewModel(factory = WorkoutViewModelFactory(application))

                MainWorkout(
                    instance = this,
                    viewModel = viewModel,
                    trainingPlanIndex = trainingPlanIndex
                )
            }
        }
    }


}