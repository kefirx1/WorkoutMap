package pl.dev.workoutmapcompose.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.dev.workoutmapcompose.ui.screenAddNewTrainingPlan.AddNewTrainingPlanViewModel
import pl.dev.workoutmapcompose.ui.screenDashboard.DashboardViewModel
import pl.dev.workoutmapcompose.ui.screenRegistration.RegistrationViewModel
import pl.dev.workoutmapcompose.ui.screenSettings.SettingsViewModel
import pl.dev.workoutmapcompose.ui.screenTrainingPlans.TrainingPlansViewModel
import pl.dev.workoutmapcompose.ui.screenWeightHistory.WeightHistoryViewModel
import pl.dev.workoutmapcompose.ui.screenWorkout.WorkoutViewModel

@Suppress("UNCHECKED_CAST")
class DashboardViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) = DashboardViewModel(application = application) as T
}
@Suppress("UNCHECKED_CAST")
class RegistrationViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = RegistrationViewModel(application = application) as T
}
@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingsViewModel(application = application) as T
}
@Suppress("UNCHECKED_CAST")
class TrainingPlansViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TrainingPlansViewModel(application = application) as T
}
@Suppress("UNCHECKED_CAST")
class WeightHistoryViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = WeightHistoryViewModel(application = application) as T
}
@Suppress("UNCHECKED_CAST")
class AddNewTrainingPlanViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = AddNewTrainingPlanViewModel(application = application) as T
}
@Suppress("UNCHECKED_CAST")
class WorkoutViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = WorkoutViewModel(application = application) as T
}