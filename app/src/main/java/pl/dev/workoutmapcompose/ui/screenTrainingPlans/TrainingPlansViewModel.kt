package pl.dev.workoutmapcompose.ui.screenTrainingPlans

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.dev.workoutmapcompose.datbase.WMRepository

class TrainingPlansViewModel
@ViewModelInject
constructor(
    application: Application
): ViewModel(){

    private val wmRepository = WMRepository(application = application)

}