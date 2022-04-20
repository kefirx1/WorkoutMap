package pl.dev.workoutmapcompose.datbase

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.*
import pl.dev.workoutmapcompose.datbase.FirebaseListener.firebaseInfoResult
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
    fun wipeData(): Boolean{
        userInfoDao.deleteUser()
        //TODO
        return true
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

    private fun getUserInfo(): UserInfo {
        return userInfoDao.getUserInfo()
    }






//
//    fun addNewTrainingPlan(trainingPlan: TrainingPlan) {
//////        MainActivity.currentUser.trainingPlans.add(trainingPlan)
////        WMFirebase.getReference("User").setValue(MainActivity.currentUser)
//    }
//
//    fun  setDataListener() {
//        WMFirebase.getReference("User").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val listOfItems: ArrayList<Any> = ArrayList()
//                snapshot.children.forEach { customer ->
//                    customer.value?.let { listOfItems.add(it) }
//                }
//                if(setCurrentUserFromDatabase(listOfItems)){
//                    Log.e("TAG", "Data refreshed")
//                }else{
//                    Log.e("TAG", "Firebase Error")
//                }
//            }
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("TAG", "Failed to read value.", error.toException())
//            }
//        })
//    }
//
//    private fun setCurrentUserFromDatabase(listOfItems: ArrayList<Any>):Boolean {
//
//        val name = listOfItems[5] as String
//        val surName = listOfItems[8] as String
//        val height = listOfItems[3] as String
//        val gender = listOfItems[2] as String
//        val age = listOfItems[0] as String
//        val monday = listOfItems[4] as String
//        val tuesday = listOfItems[11] as String
//        val wednesday = listOfItems[12] as String
//        val thursday = listOfItems[9] as String
//        val friday = listOfItems[1] as String
//        val saturday = listOfItems[6] as String
//        val sunday = listOfItems[7] as String
//        val trainingPlan: ArrayList<TrainingPlan>
//        val weightHistory: ArrayList<Float>
//        val workoutHistory: ArrayList<WorkoutInfo>
//
//        try {
//            trainingPlan = listOfItems[10] as ArrayList<TrainingPlan>
//            weightHistory = listOfItems[13] as ArrayList<Float>
//            workoutHistory = listOfItems[14] as ArrayList<WorkoutInfo>
//        } catch (e: Exception) {
//            return false
//        }
//
//        return true
//    }
//

}