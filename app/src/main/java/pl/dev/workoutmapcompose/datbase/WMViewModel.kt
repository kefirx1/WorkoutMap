package pl.dev.workoutmapcompose.datbase

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class WMViewModel(application: Application) : AndroidViewModel(application) {

    private val wmRepository = WMRepository(application)

    fun userExist() = wmRepository.userExist()


}