package pl.dev.workoutmapcompose.ui.screenRegistration

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme
@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onResume() {
        super.onResume()

        setContent{
            WorkoutMapComposeTheme {
                MainRegistration(
                    instance = this,
                    viewModel = viewModel
                )
            }
        }
    }

}

