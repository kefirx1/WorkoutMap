package pl.dev.workoutmapcompose.ui.screenDashboard

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.MainViewInfo
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.datbase.WMRepository


class DashboardViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val userMainViewInfoResult: MutableState<MainViewInfo?> = mutableStateOf(null)
    val trainingPlansListResult: MutableState<ArrayList<TrainingPlan>?> = mutableStateOf(ArrayList())

    init {
        viewModelScope.launch {
            userMainViewInfoResult.value = wmRepository.getUserFirstPageInfo()
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



}