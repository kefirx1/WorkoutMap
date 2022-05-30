package pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme

@AndroidEntryPoint
class AddNewTrainingPlanActivity : ComponentActivity() {

    private val viewModel: AddNewTrainingPlanViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        setContent{
            WorkoutMapComposeTheme {
                MainNewTrainingView(
                    instance = this,
                    viewModel = viewModel
                )
            }
        }
    }

}