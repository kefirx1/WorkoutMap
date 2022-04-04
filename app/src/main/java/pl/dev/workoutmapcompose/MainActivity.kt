package pl.dev.workoutmapcompose

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.User
import pl.dev.workoutmapcompose.data.WorkoutInfo
import pl.dev.workoutmapcompose.datbase.WMViewModel
import pl.dev.workoutmapcompose.ui.theme.BlueGray50
import pl.dev.workoutmapcompose.ui.theme.BlueGray800
import pl.dev.workoutmapcompose.ui.theme.BlueGray900
import pl.dev.workoutmapcompose.ui.theme.mainFamily

class MainActivity : ComponentActivity() {

    companion object {
        private val emptyExercises = arrayListOf(Exercise("", ""))
        private val emptyTrainingPlan = TrainingPlan("", emptyExercises)
        private val emptyHashMap: HashMap<String, ArrayList<Float>> = HashMap()
        private val emptyWorkoutInfo = WorkoutInfo(emptyTrainingPlan, 0, emptyExercises, emptyHashMap)

        var currentUser = User(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            arrayListOf(emptyTrainingPlan),
            arrayListOf(0.0F),
            arrayListOf(emptyWorkoutInfo)
        )
    }

    private lateinit var viewModel: WMViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(WMViewModel::class.java)
        viewModel.setDataListener()
        setContent {
            MainDashboard(this)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "RESUME")
    }


}

@Composable
fun MainDashboard(
    instance: MainActivity
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueGray900)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.85f)
                .fillMaxWidth()
        ) {
            Text(text = "Test")
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                modifier = Modifier
                    .fillMaxSize(),
                onClick = {
                          
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = BlueGray800
                ),
            ) {
                Text(
                    text = "TRENUJ",
                    color = BlueGray50,
                    fontFamily = mainFamily,
                    fontSize = 40.sp
                    )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainDashboard(MainActivity())
}
