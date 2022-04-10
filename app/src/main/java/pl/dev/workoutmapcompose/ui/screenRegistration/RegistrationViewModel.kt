package pl.dev.workoutmapcompose.ui.screenRegistration

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.datbase.WMRepository

class RegistrationViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    fun insertUser(userInfo: UserInfo) = wmRepository.insertUser(userInfo)

}