package pl.dev.workoutmapcompose.ui.screenSettings

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.datbase.WMRepository

class SettingsViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    val userInfoListResult: MutableState<UserInfo?> = mutableStateOf(null)

    fun updateUserName(userName: String) = wmRepository.updateUserName(userName)

    fun updateUserSurname(userSurname: String) = wmRepository.updateUserSurname(userSurname)

    fun updateUserAge(userAge: String) = wmRepository.updateUserAge(userAge)

    fun updateUserGender(userGender: String) = wmRepository.updateUserGender(userGender)

    fun updateUserHeight(userHeight: String) = wmRepository.updateUserHeight(userHeight)

    fun getUserInfo(){
        userInfoListResult.value = wmRepository.getUserInfo()
    }

    fun wipeData() = wmRepository.wipeData()
}