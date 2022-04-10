package pl.dev.workoutmapcompose

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import pl.dev.workoutmapcompose.datbase.WMViewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.DashboardViewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.MainDashboard


class DashboardViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = DashboardViewModel(application = application) as T
}



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
                MainDashboard(this, viewModel)
            }
        }

    }

}


