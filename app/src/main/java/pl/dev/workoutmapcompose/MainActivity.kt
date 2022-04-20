package pl.dev.workoutmapcompose

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import pl.dev.workoutmapcompose.datbase.WMViewModel
import pl.dev.workoutmapcompose.factories.DashboardViewModelFactory
import pl.dev.workoutmapcompose.ui.screenDashboard.DashboardViewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.MainDashboard

class MainActivity : ComponentActivity(){

    private lateinit var viewModelMain: WMViewModel
    private lateinit var viewModel: DashboardViewModel

    @OptIn(ExperimentalPagerApi::class)
    override fun onResume() {
        super.onResume()

        viewModelMain = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WMViewModel::class.java)

        if (!viewModelMain.userExist()) {
            Log.e("TAG", "User not exist")
            //REGISTER USER
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        } else {
            setContent {

                viewModel = viewModel(factory = DashboardViewModelFactory(application))
                viewModel.setFirebaseListener()
                MainDashboard(this, viewModel)
            }
        }

    }

}


