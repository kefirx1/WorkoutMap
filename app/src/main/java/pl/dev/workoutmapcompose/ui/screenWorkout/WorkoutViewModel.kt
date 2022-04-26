package pl.dev.workoutmapcompose.ui.screenWorkout

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.WorkoutHistory
import pl.dev.workoutmapcompose.datbase.WMRepository

class WorkoutViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val workoutHistoryResult: MutableState<ArrayList<WorkoutHistory>?> = mutableStateOf(null)
    val trainingPlansListResult: MutableState<ArrayList<TrainingPlan>?> = mutableStateOf(ArrayList())

    fun getWorkoutHistory(){
//        viewModelScope.launch {
            val result = wmRepository.getWorkoutHistoryList()
            workoutHistoryResult.value = result
//        }
    }

    fun getTrainingPlansList(){
        trainingPlansListResult.value = wmRepository.getTrainingPlansList()
    }

}