package pl.dev.workoutmapcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import pl.dev.workoutmapcompose.datbase.WMViewModel
import pl.dev.workoutmapcompose.ui.theme.BlueGray900

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        MainActivity.viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WMViewModel::class.java)

        setContent {
            MainSettingsView(instance = this)
        }
    }

}

fun exitSettings(
    instance: SettingsActivity
){
    //TODO
    instance.finish()
}

@Composable
fun MainSettingsView(
    instance: SettingsActivity
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
            .padding(10.dp)
    ) {

        IconButton(
            onClick = {
                exitSettings(instance)
            }) {
            Icon(
                Icons.Filled.ArrowBack, contentDescription = "Back button"
            )
        }


        Spacer(
            modifier = Modifier
                .background(color = Color.Black)
                .height(2.dp)
                .fillMaxWidth()
        )

    }

}