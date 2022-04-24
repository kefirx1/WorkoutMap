package pl.dev.workoutmapcompose.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.components.ActivityComponent
import pl.dev.workoutmapcompose.factories.RegistrationViewModelFactory
import pl.dev.workoutmapcompose.factories.WorkoutViewModelFactory
import pl.dev.workoutmapcompose.ui.screenRegistration.MainRegistration
import pl.dev.workoutmapcompose.ui.screenRegistration.RegistrationViewModel
import pl.dev.workoutmapcompose.ui.screenWorkout.MainWorkout
import pl.dev.workoutmapcompose.ui.screenWorkout.WorkoutViewModel

class WorkoutActivity : ComponentActivity() {


    private lateinit var viewModel: WorkoutViewModel

    override fun onResume() {
        super.onResume()

        setContent{
            viewModel = viewModel(factory = WorkoutViewModelFactory(application))
            MainWorkout(
                instance = this,
                viewModel = viewModel
            )
        }
    }
}