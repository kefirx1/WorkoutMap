package pl.dev.workoutmap.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.UserBasic
import pl.dev.workoutmapcompose.datbase.WMRepository

class WMViewModel(application: Application) : AndroidViewModel(application) {

    private val wmRepository = WMRepository()

    fun setNewUser(userBasic: UserBasic){
        wmRepository.setNewUser(userBasic)
    }

    fun addNewTrainingPlan(trainingPlan: TrainingPlan){
        wmRepository.addNewTrainingPlan(trainingPlan)
    }

    fun setDataListener(){
        wmRepository.setDataListener()
    }

}