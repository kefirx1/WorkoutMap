package pl.dev.workoutmapcompose.ui.screenRegistration

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.datbase.WMRepository
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel
@Inject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

    fun insertUser(userInfo: UserInfo) = wmRepository.insertUser(userInfo)

}