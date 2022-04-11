package pl.dev.workoutmapcompose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import pl.dev.workoutmapcompose.factories.RegistrationViewModelFactory
import pl.dev.workoutmapcompose.ui.screenRegistration.MainRegistration
import pl.dev.workoutmapcompose.ui.screenRegistration.RegistrationViewModel

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

