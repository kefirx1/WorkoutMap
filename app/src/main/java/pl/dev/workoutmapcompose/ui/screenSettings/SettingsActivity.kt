package pl.dev.workoutmapcompose.ui.screenSettings

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        setContent {
            WorkoutMapComposeTheme {
                MainSettingsView(
                    instance = this,
                    viewModel = viewModel
                )
            }
        }
    }


}


