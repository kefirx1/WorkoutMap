package pl.dev.workoutmapcompose.ui.screenDashboard

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.MainViewInfo
import pl.dev.workoutmapcompose.datbase.WMRepository


class DashboardViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val userMainViewInfoResult: MutableState<MainViewInfo?> = mutableStateOf(null)

    init {
        viewModelScope.launch {
            val result = wmRepository.getUserFirstPageInfo()
            userMainViewInfoResult.value = result
        }
    }

    fun getUserMainViewInfo() {
        viewModelScope.launch {
            val result = wmRepository.getUserFirstPageInfo()
            userMainViewInfoResult.value = result
        }
    }




}