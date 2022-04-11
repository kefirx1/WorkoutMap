package pl.dev.workoutmapcompose.ui.screenWeightHistory

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.MainViewInfo
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.datbase.WMRepository

class WeightHistoryViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val weightHistoryResult: MutableState<ArrayList<WeightHistory>?> = mutableStateOf(null)

    fun insertNewWeightHistory(weightHistory: WeightHistory) = wmRepository.insertNewWeightHistory(weightHistory)

    fun getWeightHistory(){
        viewModelScope.launch {
            val result = wmRepository.getUserWeightHistory()
            weightHistoryResult.value = result as ArrayList<WeightHistory>
        }
    }

}