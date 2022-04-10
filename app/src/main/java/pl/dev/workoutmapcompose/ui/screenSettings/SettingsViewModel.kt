package pl.dev.workoutmapcompose.ui.screenSettings

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.datbase.WMRepository

class SettingsViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    fun updateUserName(userName: String) = wmRepository.updateUserName(userName)

    fun updateUserSurname(userSurname: String) = wmRepository.updateUserSurname(userSurname)

    fun wipeData() = wmRepository.wipeData()
}