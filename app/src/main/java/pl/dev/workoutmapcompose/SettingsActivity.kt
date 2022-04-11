package pl.dev.workoutmapcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.factories.SettingsViewModelFactory
import pl.dev.workoutmapcompose.ui.screenSettings.MainSettingsView
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel


class SettingsActivity : ComponentActivity() {

    private lateinit var viewModel: SettingsViewModel

    override fun onResume() {
        super.onResume()

        setContent {
            viewModel = viewModel(factory = SettingsViewModelFactory(application))
            MainSettingsView(
                instance = this,
                viewModel = viewModel
            )
        }
    }


}


