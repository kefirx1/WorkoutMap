package pl.dev.workoutmapcompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.DashboardViewModel
import pl.dev.workoutmapcompose.ui.screenSettings.MainSettingsView
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel

class SettingsViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(application = application) as T
}

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


