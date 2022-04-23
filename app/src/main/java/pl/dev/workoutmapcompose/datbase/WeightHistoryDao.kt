package pl.dev.workoutmapcompose.datbase

import androidx.room.*
import pl.dev.workoutmapcompose.data.WeightHistory

@Dao
interface WeightHistoryDao {

    @Insert
    fun insert(weightHistory: WeightHistory)

    @Update
    fun update(weightHistory: WeightHistory)

    @Delete
    fun delete(weightHistory: WeightHistory)

    @Query("SELECT * FROM weightHistory")
    fun getWeightHistory(): List<WeightHistory>

    @Query("DELETE FROM weightHistory")
    fun deleteWeightHistory()

}