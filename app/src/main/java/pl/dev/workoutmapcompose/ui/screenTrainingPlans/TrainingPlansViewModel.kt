package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.datbase.WMRepository
import javax.inject.Inject

@HiltViewModel
class TrainingPlansViewModel
@Inject
constructor(
    private val wmRepository: WMRepository
): ViewModel() {

    val trainingPlansListResult: MutableState<List<TrainingPlan>?> = mutableStateOf(listOf())

    fun getTrainingPlansList(){
        viewModelScope.launch {
            trainingPlansListResult.value = wmRepository.getTrainingPlansList()
        }
    }

    fun deleteTrainingPlan(trainingPlan: TrainingPlan){
        wmRepository.deleteTrainingPlan(trainingPlan = trainingPlan)
    }

}