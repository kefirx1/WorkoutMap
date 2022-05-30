package pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.datbase.WMRepository
import pl.dev.workoutmapcompose.json.data.JSONExercisesData
import javax.inject.Inject

@HiltViewModel
class AddNewTrainingPlanViewModel
@Inject
constructor(
    private val wmRepository: WMRepository
): ViewModel(){

    private val exercisesJSONResult: MutableState<JSONExercisesData?> = mutableStateOf(null)
    var exercisesListResult: MutableState<List<String>> = mutableStateOf(listOf())
    val trainingPlansListResult: MutableState<List<TrainingPlan>?> = mutableStateOf(listOf())


    fun getExercisesJSON(){
        viewModelScope.launch {
            exercisesJSONResult.value = wmRepository.getExercisesJSON()
        }
    }

    fun getTrainingPlansList(){
        viewModelScope.launch {
            trainingPlansListResult.value = wmRepository.getTrainingPlansList()
        }
    }

    fun getExercisesArrayList(
        type: String
    ){
        viewModelScope.launch {
            when(type){
                "Klatka piersiowa" -> exercisesListResult.value = exercisesJSONResult.value!!.chest
                "Plecy" -> exercisesListResult.value = exercisesJSONResult.value!!.back
                "Barki" -> exercisesListResult.value = exercisesJSONResult.value!!.shoulders
                "Biceps" -> exercisesListResult.value = exercisesJSONResult.value!!.biceps
                "Triceps" -> exercisesListResult.value = exercisesJSONResult.value!!.triceps
                "Nogi" -> exercisesListResult.value = exercisesJSONResult.value!!.legs
                "Przedramiona" -> exercisesListResult.value = exercisesJSONResult.value!!.forearms
                "Brzuch" -> exercisesListResult.value = exercisesJSONResult.value!!.belly
            }
        }
    }

    fun addNewTrainingPlan(trainingPlan: TrainingPlan){
        wmRepository.addNewTrainingPlan(trainingPlan = trainingPlan)
    }


}