package pl.dev.workoutmapcompose.ui.screenSettings

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.datbase.WMRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val userInfoResult: MutableState<UserInfo?> = mutableStateOf(null)

    fun updateUserPersonalInfo(userInfo: UserInfo) = wmRepository.updateUserPersonalInfo(userInfo)

    fun getUserInfo(){
        viewModelScope.launch {
            userInfoResult.value = wmRepository.getUserInfo()
        }
    }

    fun wipeData() = wmRepository.wipeData()

    fun wipeTrainingPlans() = wmRepository.wipeTrainingPlans()


}