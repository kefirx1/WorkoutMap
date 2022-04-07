package pl.dev.workoutmapcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import pl.dev.workoutmapcompose.datbase.WMViewModel

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainActivity.viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WMViewModel::class.java)

        setContent {

        }

    }


}