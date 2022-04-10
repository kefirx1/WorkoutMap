package pl.dev.workoutmapcompose

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.ui.screenWeightHistory.MainWeightHistoryView
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryViewModel

class WeightHistoryViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = WeightHistoryViewModel(application = application) as T
}

class WeightHistoryActivity : ComponentActivity() {

    private lateinit var viewModel: WeightHistoryViewModel

    override fun onResume() {
        super.onResume()

        setContent {
            viewModel = viewModel(factory = WeightHistoryViewModelFactory(application))
            MainWeightHistoryView(
                instance = this,
                viewModel = viewModel
            )
        }

    }

}