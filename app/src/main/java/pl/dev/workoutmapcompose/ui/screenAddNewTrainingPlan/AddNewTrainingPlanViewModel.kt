package pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.datbase.WMRepository
import pl.dev.workoutmapcompose.json.data.JSONExercisesData

class AddNewTrainingPlanViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val exercisesJSONResult: MutableState<JSONExercisesData?> = mutableStateOf(null)
    var exercisesListResult: MutableState<List<String>> = mutableStateOf(ArrayList())
    val trainingPlansListResult: MutableState<ArrayList<TrainingPlan>?> = mutableStateOf(ArrayList())

    fun getExercisesJSON(context: Context){
        exercisesJSONResult.value = wmRepository.getExercisesJSON(context = context)
    }

    fun getTrainingPlansList(){
        trainingPlansListResult.value = wmRepository.getTrainingPlansList()
    }

    fun getExercisesArrayList(
        type: String
    ){
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

    fun addNewTrainingPlan(trainingPlan: TrainingPlan){
        wmRepository.addNewTrainingPlan(trainingPlan = trainingPlan)
    }


}