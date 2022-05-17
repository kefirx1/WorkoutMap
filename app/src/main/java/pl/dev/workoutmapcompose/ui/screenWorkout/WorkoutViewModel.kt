package pl.dev.workoutmapcompose.ui.screenWorkout

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.WorkoutHistory
import pl.dev.workoutmapcompose.datbase.WMRepository
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel
@Inject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val workoutHistoryResult: MutableState<ArrayList<WorkoutHistory>?> = mutableStateOf(null)
    val trainingPlansListResult: MutableState<ArrayList<TrainingPlan>?> = mutableStateOf(ArrayList())
    val progressHistoryResult: MutableState<HashMap<String, Map<String, ArrayList<String>>>> = mutableStateOf(HashMap())

    fun getWorkoutHistory(){
        viewModelScope.launch {
            workoutHistoryResult.value = wmRepository.getWorkoutHistoryList()
        }
    }

    fun getExercisesProgressHistory(trainingPlan: TrainingPlan){
        viewModelScope.launch {

            val progressHistory = wmRepository.getProgressHistory()

            if(progressHistory!=null){
                val exercisesSet = HashSet<String>()
                val progressHistoryForPlanList: HashMap<String, Map<String, ArrayList<String>>> = HashMap()

                trainingPlan.exercise.forEach{
                    if(progressHistory.exercisesProgress.keys.contains(it.name)){
                        exercisesSet.add(it.name)
                    }
                }

                progressHistory.exercisesProgress.keys.forEach{
                    if(exercisesSet.contains(it)){
                        progressHistoryForPlanList[it] = progressHistory.exercisesProgress[it]!!
                    }
                }

                progressHistoryResult.value = progressHistoryForPlanList

            }

        }
    }

    fun getTrainingPlansList(){
        viewModelScope.launch {
            trainingPlansListResult.value = wmRepository.getTrainingPlansList()
        }
    }

}