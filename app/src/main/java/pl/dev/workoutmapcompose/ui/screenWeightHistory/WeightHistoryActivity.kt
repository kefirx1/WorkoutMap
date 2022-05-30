package pl.dev.workoutmapcompose.ui.screenWeightHistory

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme

@AndroidEntryPoint
class WeightHistoryActivity : ComponentActivity() {

    private val viewModel: WeightHistoryViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        setContent {
            WorkoutMapComposeTheme {
                MainWeightHistoryView(
                    instance = this,
                    viewModel = viewModel
                )
            }
        }

    }

}