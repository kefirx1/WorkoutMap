package pl.dev.workoutmapcompose.datbase

import android.app.Application
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.MainActivity
import pl.dev.workoutmapcompose.data.*
import java.lang.Exception

class WMRepository (application: Application){

    private var userInfoDao: UserInfoDao

    init{
        val database = WMDatabase
            .getInstance(application.applicationContext)
        userInfoDao = database!!.userInfoDao()
    }

    fun insertUser(userInfo: UserInfo) = CoroutineScope(Dispatchers.IO).launch {
        userInfoDao.insert(userInfo)
    }

    fun userExist(): Boolean{
        return userInfoDao.userExist()>0
    }

    fun addNewTrainingPlan(trainingPlan: TrainingPlan) {
////        MainActivity.currentUser.trainingPlans.add(trainingPlan)
//        WMFirebase.getReference("User").setValue(MainActivity.currentUser)
    }

    fun  setDataListener() {
        WMFirebase.getReference("User").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listOfItems: ArrayList<Any> = ArrayList()
                snapshot.children.forEach { customer ->
                    customer.value?.let { listOfItems.add(it) }
                }
                if(setCurrentUserFromDatabase(listOfItems)){
                    Log.e("TAG", "Data refreshed")
                }else{
                    Log.e("TAG", "Firebase Error")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun setCurrentUserFromDatabase(listOfItems: ArrayList<Any>):Boolean {

        val name = listOfItems[5] as String
        val surName = listOfItems[8] as String
        val height = listOfItems[3] as String
        val gender = listOfItems[2] as String
        val age = listOfItems[0] as String
        val monday = listOfItems[4] as String
        val tuesday = listOfItems[11] as String
        val wednesday = listOfItems[12] as String
        val thursday = listOfItems[9] as String
        val friday = listOfItems[1] as String
        val saturday = listOfItems[6] as String
        val sunday = listOfItems[7] as String
        val trainingPlan: ArrayList<TrainingPlan>
        val weightHistory: ArrayList<Float>
        val workoutHistory: ArrayList<WorkoutInfo>

        try {
            trainingPlan = listOfItems[10] as ArrayList<TrainingPlan>
            weightHistory = listOfItems[13] as ArrayList<Float>
            workoutHistory = listOfItems[14] as ArrayList<WorkoutInfo>
        } catch (e: Exception) {
            return false
        }

        return true
    }


}