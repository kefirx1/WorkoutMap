package pl.dev.workoutmapcompose

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.MainTrainingPlansView
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.TrainingPlansViewModel


class TrainingPlansViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TrainingPlansViewModel(application = application) as T
}

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