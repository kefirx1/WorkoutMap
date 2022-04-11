package pl.dev.workoutmapcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.factories.TrainingPlansViewModelFactory
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.MainTrainingPlansView
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.TrainingPlansViewModel


class TrainingPlansActivity : ComponentActivity() {

    private lateinit var viewModel: TrainingPlansViewModel

    override fun onResume() {
        super.onResume()

        setContent {
            viewModel = viewModel(factory = TrainingPlansViewModelFactory(application))
            MainTrainingPlansView(
                instance = this,
                viewModel = viewModel
            )
        }
    }


}