package pl.dev.workoutmapcompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weightHistory")
data class WeightHistory(
    var weight: String,
    var weighingDate: String
) {
    @PrimaryKey(autoGenerate = true)
    var weight_id: Int = 0
}
