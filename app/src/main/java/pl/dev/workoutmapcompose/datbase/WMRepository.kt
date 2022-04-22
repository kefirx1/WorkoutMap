package pl.dev.workoutmapcompose.datbase

import android.app.Application
import android.content.Context
import android.util.Log
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.*
import pl.dev.workoutmapcompose.datbase.FirebaseListenerResult.firebaseInfoResult
import pl.dev.workoutmapcompose.json.GetJSONString
import pl.dev.workoutmapcompose.json.data.JSONExercisesData
import javax.inject.Singleton


@Singleton
class WMRepository (application: Application){

    private var userInfoDao: UserInfoDao
    private var weightHistoryDao: WeightHistoryDao

    private val gson = Gson()

    private val firebase = FirebaseDatabase.getInstance()



    init{
        val database = WMDatabase
            .getInstance(application.applicationContext)
        userInfoDao = database!!.userInfoDao()
        weightHistoryDao = database.weightHistoryDao()
    }

    //WMViewModel
    fun userExist(): Boolean{
        return userInfoDao.userExist()>0
    }

    //RegistrationViewModel
    fun insertUser(userInfo: UserInfo) = CoroutineScope(Dispatchers.IO).launch {
        userInfoDao.insert(userInfo)
    }

    //SettingsViewModel
    fun updateUserName(newName: String) = CoroutineScope(Dispatchers.IO).launch {
        val userInfo = getUserInfo()
        userInfo.name = newName
        userInfoDao.update(userInfo)
    }
    fun updateUserSurname(newSurname: String) = CoroutineScope(Dispatchers.IO).launch {
        val userInfo = getUserInfo()
        userInfo.surName = newSurname
        userInfoDao.update(userInfo)
    }
    fun updateUserAge(userAge: String) = CoroutineScope(Dispatchers.IO).launch {
        val userInfo = getUserInfo()
        userInfo.age = userAge
        userInfoDao.update(userInfo)
    }
    fun updateUserGender(userGender: String) = CoroutineScope(Dispatchers.IO).launch {
        val userInfo = getUserInfo()
        userInfo.gender = userGender
        userInfoDao.update(userInfo)
    }
    fun updateUserHeight(userHeight: String) = CoroutineScope(Dispatchers.IO).launch {
        val userInfo = getUserInfo()
        userInfo.height = userHeight
        userInfoDao.update(userInfo)
    }
    fun wipeData(): Boolean{
        wipeFirebase()
        userInfoDao.deleteUser()
        return true
    }
    private fun wipeFirebase(){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.setValue(null)
    }


    //WeightHistory
    fun insertNewWeightHistory(weightHistory: WeightHistory) =  CoroutineScope(Dispatchers.IO).launch {
        weightHistoryDao.insert(weightHistory)
    }
    fun getUserWeightHistory(): List<WeightHistory> {
        return weightHistoryDao.getWeightHistory()
    }
    fun deleteWeightHistory(weightHistory: WeightHistory) =  CoroutineScope(Dispatchers.IO).launch {
        weightHistoryDao.delete(weightHistory)
    }

    //TrainingPlans
    fun getExercisesJSON(context: Context): JSONExercisesData {
        return gson.fromJson(
            GetJSONString().getJsonStringFromAssets(
                context = context,
                "listOfExercises.json"
            ), JSONExercisesData::class.java
        )
    }
    fun getTrainingPlansList(): ArrayList<TrainingPlan>{

        val trainingPlansList: ArrayList<TrainingPlan> = ArrayList()

        firebaseInfoResult.value!!.child("trainingPlans").children.forEach{ it ->

            val trainingPlanTemp = it.getValue(TrainingPlanTemp::class.java)

            val exercisesList: ArrayList<Exercise> = ArrayList()

            trainingPlanTemp!!.exercise.forEach{item ->
                val exercise = Exercise(
                    name = item.name,
                    type = item.type,
                    numberOfSets = item.numberOfSets
                )
                exercisesList.add(exercise)
            }
            val trainingPlan = TrainingPlan(trainingPlanTemp.planName, exercisesList as List<Exercise>)
            trainingPlansList.add(trainingPlan)
        }

        return trainingPlansList
    }
    fun addNewTrainingPlan(trainingPlan: TrainingPlan){
        val trainingPlans = getTrainingPlansList()
        trainingPlans.add(trainingPlan)
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("trainingPlans").setValue(trainingPlans)
    }
    fun updateTrainingPlan(trainingPlansList: ArrayList<TrainingPlan>){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("trainingPlans").setValue(trainingPlansList)
    }
    fun deleteTrainingPlan(trainingPlan: TrainingPlan){
        val trainingPlansList = getTrainingPlansList()
        trainingPlansList.remove(trainingPlan)
        updateTrainingPlan(trainingPlansList)
    }


    //DashboardViewModel

    fun setFirebaseListener(){

        val reference = firebase.getReference(getUserInfo().userFirebaseID)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                firebaseInfoResult.value = snapshot
                Log.e("TAG", "Firebase")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })

    }

    fun getUserFirstPageInfo(): MainViewInfo {
        val userInfo = getUserInfo()

        println(userInfo)

        val workoutGraphicState = 0 //TODO
        val userName = userInfo.name
        var userWeight = "-"

        if(getUserWeightHistory().isNotEmpty()){

            val sortedWeightHistoryList = getUserWeightHistory().sortedBy {
                it.weighingDate
            }

            userWeight = sortedWeightHistoryList[sortedWeightHistoryList.lastIndex].weight
        }

        return MainViewInfo(
            workoutGraphicState = workoutGraphicState,
            userName = userName,
            userWeight = userWeight
        )
    }

    fun getUserInfo(): UserInfo {
        return userInfoDao.getUserInfo()
    }

}