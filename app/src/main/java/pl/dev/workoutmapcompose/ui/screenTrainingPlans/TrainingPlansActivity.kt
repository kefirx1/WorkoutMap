package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme

@AndroidEntryPoint
class TrainingPlansActivity : ComponentActivity() {

    private val viewModel: TrainingPlansViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        setContent {
            WorkoutMapComposeTheme {
                MainTrainingPlansView(
                    instance = this,
                    viewModel = viewModel
                )
            }
        }
    }


}