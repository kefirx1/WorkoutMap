package pl.dev.workoutmapcompose.datbase

import androidx.room.*
import pl.dev.workoutmapcompose.data.TrainingPlan


@Dao
interface TrainingPlanDao {

    @Insert
    fun insert(trainingPlan: TrainingPlan)

    @Update
    fun update(trainingPlan: TrainingPlan)

    @Delete
    fun delete(trainingPlan: TrainingPlan)

    @Query("SELECT * FROM trainingPlans")
    fun getTrainingPlansList(): List<TrainingPlan>

}