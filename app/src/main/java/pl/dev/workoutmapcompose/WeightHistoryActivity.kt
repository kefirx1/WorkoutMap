package pl.dev.workoutmapcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.factories.WeightHistoryViewModelFactory
import pl.dev.workoutmapcompose.ui.screenWeightHistory.MainWeightHistoryView
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryViewModel


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