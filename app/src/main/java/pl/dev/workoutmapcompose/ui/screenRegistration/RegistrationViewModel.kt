package pl.dev.workoutmapcompose.ui.screenRegistration

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.datbase.WMRepository
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel
@Inject
constructor(
    private val wmRepository: WMRepository
): ViewModel(){

    fun insertUser(userInfo: UserInfo) = wmRepository.insertUser(userInfo)

}