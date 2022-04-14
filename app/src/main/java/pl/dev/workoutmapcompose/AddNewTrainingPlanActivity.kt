package pl.dev.workoutmapcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.factories.AddNewTrainingPlanViewModelFactory
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.AddNewTrainingPlanViewModel
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.MainNewTrainingView

class AddNewTrainingPlanActivity : ComponentActivity() {

    private lateinit var viewModel: AddNewTrainingPlanViewModel

    override fun onResume() {
        super.onResume()

        setContent{
            viewModel = viewModel(factory = AddNewTrainingPlanViewModelFactory(application))
            MainNewTrainingView(
                instance = this,
                viewModel = viewModel
            )
        }
    }

}