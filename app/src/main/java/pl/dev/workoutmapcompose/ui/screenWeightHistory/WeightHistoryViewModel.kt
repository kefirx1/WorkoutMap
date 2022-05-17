package pl.dev.workoutmapcompose.ui.screenWeightHistory

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.datbase.WMRepository
import javax.inject.Inject

@HiltViewModel
class WeightHistoryViewModel
@Inject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val weightHistoryResult: MutableState<ArrayList<WeightHistory>?> = mutableStateOf(null)

    fun insertNewWeightHistory(weightHistory: WeightHistory) = wmRepository.insertNewWeightHistory(weightHistory)
    fun deleteWeightHistory(weightHistory: WeightHistory) = wmRepository.deleteWeightHistory(weightHistory)

    fun getWeightHistory(){
        viewModelScope.launch {
            weightHistoryResult.value = wmRepository.getUserWeightHistory()
        }
    }

}