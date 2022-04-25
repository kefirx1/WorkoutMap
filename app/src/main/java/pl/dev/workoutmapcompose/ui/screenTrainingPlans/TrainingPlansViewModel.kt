package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.datbase.WMRepository
import pl.dev.workoutmapcompose.json.data.JSONExercisesData

class TrainingPlansViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val trainingPlansListResult: MutableState<ArrayList<TrainingPlan>?> = mutableStateOf(ArrayList())

    fun getTrainingPlansList(){
        trainingPlansListResult.value = wmRepository.getTrainingPlansList()
    }

    fun deleteTrainingPlan(trainingPlan: TrainingPlan){
        wmRepository.deleteTrainingPlan(trainingPlan = trainingPlan)
    }

}