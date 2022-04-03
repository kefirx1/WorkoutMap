package pl.dev.workoutmapcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import pl.dev.workoutmap.database.WMViewModel
import pl.dev.workoutmapcompose.data.Exercise
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.User
import pl.dev.workoutmapcompose.data.WorkoutInfo
import pl.dev.workoutmapcompose.ui.theme.WorkoutMapComposeTheme

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
            WorkoutMapComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Log.e("TAG", "RESUME")
    }





}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WorkoutMapComposeTheme {
        Greeting("Android")
    }
}