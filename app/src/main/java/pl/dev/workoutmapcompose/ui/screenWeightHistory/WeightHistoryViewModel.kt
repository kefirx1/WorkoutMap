package pl.dev.workoutmapcompose.ui.screenWeightHistory

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
    private val wmRepository: WMRepository
): ViewModel(){

    val weightHistoryResult: MutableState<List<WeightHistory>?> = mutableStateOf(null)

    fun insertNewWeightHistory(weightHistory: WeightHistory) = wmRepository.insertNewWeightHistory(weightHistory)
    fun deleteWeightHistory(weightHistory: WeightHistory) = wmRepository.deleteWeightHistory(weightHistory)

    fun getWeightHistory(){
        viewModelScope.launch {
            weightHistoryResult.value = wmRepository.getUserWeightHistory()
        }
    }

}