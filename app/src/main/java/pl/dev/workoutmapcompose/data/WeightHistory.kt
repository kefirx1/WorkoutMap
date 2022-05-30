package pl.dev.workoutmapcompose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weightHistory")
data class WeightHistory(

    @ColumnInfo(name = "weight")
    var weight: String,

    @ColumnInfo(name = "weighingDate")
    var weighingDate: Int

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weight_id")
    var weight_id: Int = 0
}
