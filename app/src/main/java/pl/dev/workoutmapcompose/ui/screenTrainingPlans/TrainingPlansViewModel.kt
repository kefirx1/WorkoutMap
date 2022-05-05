package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.datbase.WMRepository

class TrainingPlansViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val trainingPlansListResult: MutableState<ArrayList<TrainingPlan>?> = mutableStateOf(ArrayList())

    fun getTrainingPlansList(){
        viewModelScope.launch {
            trainingPlansListResult.value = wmRepository.getTrainingPlansList()
        }
    }

    fun deleteTrainingPlan(trainingPlan: TrainingPlan){
        wmRepository.deleteTrainingPlan(trainingPlan = trainingPlan)
    }

}