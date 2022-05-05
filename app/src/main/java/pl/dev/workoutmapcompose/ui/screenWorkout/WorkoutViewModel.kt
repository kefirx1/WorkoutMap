package pl.dev.workoutmapcompose.ui.screenWorkout

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.ProgressHistory
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
    val progressHistoryResult: MutableState<ProgressHistory?> = mutableStateOf(null)

    fun getWorkoutHistory(){
        viewModelScope.launch {
            workoutHistoryResult.value = wmRepository.getWorkoutHistoryList()
        }
    }

    fun getProgressHistory(){
        viewModelScope.launch {
            progressHistoryResult.value = wmRepository.getProgressHistory()
        }
    }

    fun getTrainingPlansList(){
        viewModelScope.launch {
            trainingPlansListResult.value = wmRepository.getTrainingPlansList()
        }
    }

}