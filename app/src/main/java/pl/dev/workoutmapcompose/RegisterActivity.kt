package pl.dev.workoutmapcompose

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.ui.screenRegistration.MainRegistration
import pl.dev.workoutmapcompose.ui.screenRegistration.RegistrationViewModel
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel

class RegistrationViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = RegistrationViewModel(application = application) as T
}

class RegisterActivity : ComponentActivity() {

    private lateinit var viewModel: RegistrationViewModel

    override fun onResume() {
        super.onResume()

        setContent{
            viewModel = viewModel(factory = RegistrationViewModelFactory(application))
            MainRegistration(
                instance = this,
                viewModel = viewModel
            )
        }
    }

}

