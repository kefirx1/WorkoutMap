package pl.dev.workoutmapcompose.ui.screenWorkout

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.ExerciseProgress
import pl.dev.workoutmapcompose.data.ProgressHistory
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.WorkoutHistory
import pl.dev.workoutmapcompose.datbase.WMRepository
import java.sql.Timestamp
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel
@Inject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val workoutHistoryResult: MutableState<ArrayList<WorkoutHistory>?> = mutableStateOf(null)
    val exercisesProgressListResult: MutableState<ArrayList<ArrayList<ExerciseProgress>>?> = mutableStateOf(null)
    val trainingPlansListResult: MutableState<ArrayList<TrainingPlan>?> = mutableStateOf(ArrayList())
    val progressHistoryResult: MutableState<MutableMap<String, HashMap<String, ArrayList<String>>>> = mutableStateOf(HashMap())

    fun getWorkoutHistory(){
        viewModelScope.launch {
            workoutHistoryResult.value = wmRepository.getWorkoutHistoryList()
        }
    }

    fun getSpecificExerciseProgressHistories(exerciseNameList: Set<String>){
        viewModelScope.launch {
            val result: ArrayList<ArrayList<ExerciseProgress>> = ArrayList()
            if(exerciseNameList.isNotEmpty()){
                exerciseNameList.forEach {
                    val exerciseProgressList: ArrayList<ExerciseProgress> = ArrayList()
                    val exerciseProgressMap = progressHistoryResult.value[it]
                    val exerciseProgressTimestampsKeys = exerciseProgressMap?.keys

                    exerciseProgressTimestampsKeys?.forEach { timestamp ->
                        val exerciseProgress = ExerciseProgress(
                            dateOfWorkout = timestamp,
                            setsList = exerciseProgressMap[timestamp]!!
                        )
                        exerciseProgressList.add(exerciseProgress)
                    }

                    result.add(exerciseProgressList)
                }

                exercisesProgressListResult.value = result
            }else{
                exercisesProgressListResult.value = null
            }

        }

    }


    fun getExercisesProgressHistory(trainingPlan: TrainingPlan){
        viewModelScope.launch {

            val progressHistory = wmRepository.getProgressHistory()

            if(progressHistory!=null){
                val exercisesSet = HashSet<String>()
                val progressHistoryForPlanList: MutableMap<String, HashMap<String, ArrayList<String>>> = HashMap()

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

    fun insertNewTrainingInfo(workoutHistory: WorkoutHistory, workoutIndex: Int, newProgressHistory: ProgressHistory, timestampInt: Int){
        viewModelScope.launch {
            wmRepository.insertNewTrainingInfo(
                workoutHistory = workoutHistory,
                workoutIndex = workoutIndex,
                newProgressHistory = newProgressHistory,
                timestampInt = timestampInt
            )
        }
    }


    fun getTrainingPlansList(){
        viewModelScope.launch {
            trainingPlansListResult.value = wmRepository.getTrainingPlansList()
        }
    }


}