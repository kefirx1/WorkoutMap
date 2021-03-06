package pl.dev.workoutmapcompose.ui.screenDashboard

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.ExerciseProgress
import pl.dev.workoutmapcompose.data.MainViewInfo
import pl.dev.workoutmapcompose.data.ProgressHistory
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.datbase.WMRepository
import pl.dev.workoutmapcompose.json.data.JSONExercisesData
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    private val wmRepository: WMRepository
): ViewModel(){

    val selectedMuscleGroup: MutableState<String> = mutableStateOf("Klatka piersiowa")
    private val exercisesJSONResult: MutableState<JSONExercisesData?> = mutableStateOf(null)
    val userMainViewInfoResult: MutableState<MainViewInfo?> = mutableStateOf(null)
    val trainingPlansListResult: MutableState<List<TrainingPlan>?> = mutableStateOf(listOf())
    var exercisesListResult: MutableState<List<String>> = mutableStateOf(ArrayList())
    private var fullProgressHistoryResult: MutableState<ProgressHistory?> = mutableStateOf(null)
    val exercisesProgressListResult: MutableState<List<List<ExerciseProgress>>?> = mutableStateOf(listOf())

    fun userExist() = wmRepository.userExist()

    fun getExercisesJSON(){
        viewModelScope.launch {
            exercisesJSONResult.value = wmRepository.getExercisesJSON()
        }
    }

    fun getFullExerciseProgressHistory(){
        viewModelScope.launch {
            delay(1000)
            fullProgressHistoryResult.value = wmRepository.getProgressHistory()

            getSpecificExercisesProgressList(selectedMuscleGroup.value)

        }
    }

    fun getSpecificExercisesProgressList(muscleGroup: String) {
        viewModelScope.launch {

            when (muscleGroup) {
                "Klatka piersiowa" -> exercisesListResult.value = exercisesJSONResult.value!!.chest
                "Plecy" -> exercisesListResult.value = exercisesJSONResult.value!!.back
                "Barki" -> exercisesListResult.value = exercisesJSONResult.value!!.shoulders
                "Biceps" -> exercisesListResult.value = exercisesJSONResult.value!!.biceps
                "Triceps" -> exercisesListResult.value = exercisesJSONResult.value!!.triceps
                "Nogi" -> exercisesListResult.value = exercisesJSONResult.value!!.legs
                "Przedramiona" -> exercisesListResult.value = exercisesJSONResult.value!!.forearms
                "Brzuch" -> exercisesListResult.value = exercisesJSONResult.value!!.belly
            }

            val result: ArrayList<List<ExerciseProgress>> = ArrayList()

            if(fullProgressHistoryResult.value!=null){
                exercisesListResult.value.forEach {
                    val exerciseProgressList: ArrayList<ExerciseProgress> = ArrayList()
                    val exerciseProgressMap = fullProgressHistoryResult.value!!.exercisesProgress[it]

                    exerciseProgressMap?.forEach{ (key, _) ->
                        exerciseProgressList.add(ExerciseProgress(
                            dateOfWorkout = key,
                            setsList = exerciseProgressMap[key]!!
                        ))
                    }

                    if(exerciseProgressList.isNotEmpty()){
                        val sortedExerciseProgressList = exerciseProgressList.sortedByDescending { item ->
                            item.dateOfWorkout
                        }

                        result.add(sortedExerciseProgressList)
                    }else{
                        result.add(exerciseProgressList)
                    }
                }
            }else{
                for(i in 0 until exercisesListResult.value.size){
                    result.add(emptyList())
                }
            }

            exercisesProgressListResult.value = result

        }


    }

    fun getTrainingPlansList(){
        viewModelScope.launch {
            trainingPlansListResult.value = wmRepository.getTrainingPlansList()
        }
    }

    fun getUserMainViewInfo() {
        viewModelScope.launch {
            userMainViewInfoResult.value = wmRepository.getUserFirstPageInfo()
        }
    }

    fun setFirebaseListener() {
        wmRepository.setFirebaseListener()
    }

    fun updateProgressHistory(
        newExerciseProgress: ExerciseProgress,
        exerciseSelectedName: String
    ){
        val fullProgressHistoryResultTemp = fullProgressHistoryResult.value
        fullProgressHistoryResultTemp!!.exercisesProgress[exerciseSelectedName]!![newExerciseProgress.dateOfWorkout] = newExerciseProgress.setsList

        wmRepository.updateProgressHistory(progressHistory = fullProgressHistoryResultTemp)
    }

}