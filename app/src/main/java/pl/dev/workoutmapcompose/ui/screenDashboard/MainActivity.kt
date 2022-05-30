package pl.dev.workoutmapcompose.ui.screenDashboard

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import pl.dev.workoutmapcompose.ui.screenRegistration.RegisterActivity
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity(){

    private val viewModel: DashboardViewModel by viewModels()

    @OptIn(ExperimentalPagerApi::class)
    override fun onResume() {
        super.onResume()

        if (!viewModel.userExist()) {
            Log.e("TAG", "User not exist")
            startActivity(Intent(this, RegisterActivity::class.java))
        } else {
            setContent {
                WorkoutMapComposeTheme {
                    viewModel.setFirebaseListener()
                    MainDashboard(
                        instance = this,
                        viewModel = viewModel
                    )
                }
            }
        }

    }

}


