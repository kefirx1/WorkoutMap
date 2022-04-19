package pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.dev.workoutmapcompose.datbase.WMRepository
import pl.dev.workoutmapcompose.json.data.JSONExercisesData

class AddNewTrainingPlanViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val exercisesJSONResult: MutableState<JSONExercisesData?> = mutableStateOf(null)

    fun getExercisesJSON(context: Context){
        exercisesJSONResult.value = wmRepository.getExercisesJSON(context = context)
    }

}