package pl.dev.workoutmapcompose.datbase

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.dev.workoutmapcompose.data.*
import pl.dev.workoutmapcompose.datbase.dao.UserInfoDao
import pl.dev.workoutmapcompose.datbase.dao.WeightHistoryDao
import pl.dev.workoutmapcompose.json.GetJSONString
import pl.dev.workoutmapcompose.json.data.JSONExercisesData


class WMRepository (
    private val userInfoDao: UserInfoDao,
    private val weightHistoryDao: WeightHistoryDao
){
    private val gson = Gson()
    private val firebase = FirebaseDatabase.getInstance()

    var firebaseInfoResult: MutableState<DataSnapshot?> = mutableStateOf(null)

    fun userExist(): Boolean  {
        return userInfoDao.userExist()>0
    }

    fun insertUser(userInfo: UserInfo) = CoroutineScope(Dispatchers.IO).launch {
        userInfoDao.insert(userInfo)
    }

    fun updateUserPersonalInfo(userInfo: UserInfo) = CoroutineScope(Dispatchers.IO).launch {
        userInfoDao.update(userInfo)
    }
    fun wipeData(): Boolean{
        return try{
            wipeFirebase()
            userInfoDao.deleteUser()
            weightHistoryDao.deleteWeightHistory()
            true
        }catch (e: Exception){
            false
        }
    }
    fun wipeTrainingPlans(): Boolean{
        return try{
            deleteAllTrainingPlans()
            deleteAllWorkoutHistories()
            true
        }catch (e: Exception){
            false
        }
    }
    private fun deleteAllTrainingPlans(){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("trainingPlans").setValue(null)
    }
    private fun deleteAllWorkoutHistories(){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("workoutHistory").setValue(null)
    }
    private fun wipeFirebase(){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.setValue(null)
    }

    fun insertNewWeightHistory(weightHistory: WeightHistory) =  CoroutineScope(Dispatchers.IO).launch {
        weightHistoryDao.insert(weightHistory)
    }
    fun getUserWeightHistory(): ArrayList<WeightHistory> {
        return weightHistoryDao.getWeightHistory() as ArrayList<WeightHistory>
    }
    fun deleteWeightHistory(weightHistory: WeightHistory) =  CoroutineScope(Dispatchers.IO).launch {
        weightHistoryDao.delete(weightHistory)
    }

    fun getExercisesJSON(): JSONExercisesData {
        return gson.fromJson(
            GetJSONString().getJsonStringFromAssets(
                jsonFileName = "listOfExercises.json"
            ), JSONExercisesData::class.java
        )
    }
    fun getTrainingPlansList(): ArrayList<TrainingPlan>{

        val trainingPlansList: ArrayList<TrainingPlan> = ArrayList()

        firebaseInfoResult.value!!.child("trainingPlans").children.forEach{

            val trainingPlanTemp = it.getValue(TrainingPlan::class.java)
            val exercisesList: ArrayList<Exercise> = ArrayList()
            trainingPlanTemp!!.exercise.forEach{item ->
                val exercise = Exercise(
                    name = item.name,
                    type = item.type,
                    numberOfSets = item.numberOfSets
                )
                exercisesList.add(exercise)
            }
            val trainingPlan = TrainingPlan(
                planName = trainingPlanTemp.planName,
                exercise = exercisesList,
                assignedDay = trainingPlanTemp.assignedDay
            )
            trainingPlansList.add(trainingPlan)
        }

        return trainingPlansList
    }
    fun getWorkoutHistoryList(): MutableList<WorkoutHistory>{
        val workoutHistoryList: MutableList<WorkoutHistory> = ArrayList()

        firebaseInfoResult.value!!.child("workoutHistory").children.forEach {

            val workoutHistory = it.getValue(WorkoutHistory::class.java)

            if (workoutHistory != null) {
                workoutHistoryList.add(workoutHistory)
            }
        }

        return workoutHistoryList
    }
    fun getProgressHistory(): ProgressHistory? {

        var progressHistory: ProgressHistory? = null

        try{
            progressHistory = firebaseInfoResult.value!!.child("progressHistory")
                .getValue(ProgressHistory::class.java)
        }catch (e: Exception){ }

        return progressHistory

    }
    fun addNewTrainingPlan(trainingPlan: TrainingPlan){
        val trainingPlans = getTrainingPlansList()
        val workoutHistory = getWorkoutHistoryList()

        trainingPlans.add(trainingPlan)

        val workout = (WorkoutHistory(
            dateOfWorkout = arrayListOf(0),
            planName = trainingPlan.planName
        ))

        workoutHistory.add(workout)

        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("trainingPlans").setValue(trainingPlans)
        reference.child("workoutHistory").setValue(workoutHistory)
    }
    private fun updateTrainingPlan(trainingPlansList: ArrayList<TrainingPlan>){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("trainingPlans").setValue(trainingPlansList)
    }
    private fun updateWorkoutHistory(workoutHistoryList: MutableList<WorkoutHistory>){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("workoutHistory").setValue(workoutHistoryList)
    }
    private fun updateProgressHistory(progressHistory: ProgressHistory){
        val reference = firebase.getReference(getUserInfo().userFirebaseID)
        reference.child("progressHistory").setValue(progressHistory)
    }
    fun deleteTrainingPlan(trainingPlan: TrainingPlan){
        val trainingPlansList = getTrainingPlansList()
        val workoutHistoryList = getWorkoutHistoryList()

        val trainingPlanIndex = trainingPlansList.indexOf(trainingPlan)
        trainingPlansList.remove(trainingPlan)
        workoutHistoryList.removeAt(trainingPlanIndex)

        updateTrainingPlan(trainingPlansList)
        updateWorkoutHistory(workoutHistoryList)
    }

    private fun insertWorkoutHistory(workoutHistory: WorkoutHistory, workoutIndex: Int) {
        val workoutHistoryList = getWorkoutHistoryList()
        workoutHistoryList.removeAt(workoutIndex)
        workoutHistoryList.add(
            index = workoutIndex,
            workoutHistory
        )
        updateWorkoutHistory(workoutHistoryList = workoutHistoryList)
    }
    private fun insertProgressHistory(newProgressHistory: ProgressHistory, timestampInt: Int) {
        val progressHistory = getProgressHistory()

        if(progressHistory != null){
            val newProgressHistoryKeys = newProgressHistory.exercisesProgress.keys
            newProgressHistoryKeys.forEach{
                val newProgressHistoryMap: MutableMap<String, MutableList<String>> = HashMap()
                val oldProgressHistoryMap = progressHistory.exercisesProgress[it]

                if(oldProgressHistoryMap!=null){
                    oldProgressHistoryMap[timestampInt.toString()] = newProgressHistory.exercisesProgress[it]!![timestampInt.toString()]!!
                    progressHistory.exercisesProgress[it] = oldProgressHistoryMap
                }else{
                    newProgressHistoryMap[timestampInt.toString()] = newProgressHistory.exercisesProgress[it]!![timestampInt.toString()]!!
                    progressHistory.exercisesProgress[it] = newProgressHistoryMap
                }

            }
            updateProgressHistory(progressHistory)
        }else{
            updateProgressHistory(newProgressHistory)
        }

    }
    fun insertNewTrainingInfo(workoutHistory: WorkoutHistory, workoutIndex: Int, newProgressHistory: ProgressHistory, timestampInt: Int){

        insertWorkoutHistory(
            workoutHistory = workoutHistory,
            workoutIndex = workoutIndex
        )
        insertProgressHistory(
            newProgressHistory = newProgressHistory,
            timestampInt = timestampInt
        )

    }

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

    fun getUserInfo(): UserInfo {
        return userInfoDao.getUserInfo()
    }

}