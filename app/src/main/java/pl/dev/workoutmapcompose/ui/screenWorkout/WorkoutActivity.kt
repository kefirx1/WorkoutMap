package pl.dev.workoutmapcompose.ui.screenWorkout

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme
@AndroidEntryPoint
class WorkoutActivity : ComponentActivity() {

    private val viewModel: WorkoutViewModel by viewModels()
    private var doubleBackToExitPressedOnce = false
    private var trainingPlanIndex = 0

    override fun onResume() {
        super.onResume()

        trainingPlanIndex = intent.getIntExtra("TRAINING_INDEX", 0)

        setContent {
            WorkoutMapComposeTheme {
                MainWorkout(
                    instance = this,
                    viewModel = viewModel,
                    trainingPlanIndex = trainingPlanIndex
                )
            }
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(
            this,
            "Kliknij dwa razy, by wyjść bez zapisywania",
            Toast.LENGTH_SHORT
        ).show()

        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }


}