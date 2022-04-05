package pl.dev.workoutmapcompose.datbase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.UserInfo

class WMViewModel(application: Application) : AndroidViewModel(application) {

    private val wmRepository = WMRepository(application)

    fun insertUser(userInfo: UserInfo){
        wmRepository.insertUser(userInfo)
    }

    fun userExist() = wmRepository.userExist()

    fun getUserFirstPageInfo() = wmRepository.getUserFirstPageInfo()

    fun getUserInfo() = wmRepository.getUserInfo()


    fun addNewTrainingPlan(trainingPlan: TrainingPlan){
        wmRepository.addNewTrainingPlan(trainingPlan)
    }


    fun setDataListener(): Boolean{
        wmRepository.setDataListener()
        return true
    }

}