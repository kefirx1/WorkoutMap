package pl.dev.workoutmapcompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "trainingPlans")
data class TrainingPlan (
    var planName: String,
    var exercise1: String,
    var exercise2: String,
    var exercise3: String,
    var exercise4: String,
    var exercise5: String,
    var exercise6: String,
    var exercise7: String,
    var exercise8: String,
    var exercise9: String,
    var exercise10: String
    ) {

    @PrimaryKey(autoGenerate = true)
    var training_id: Int = 1

}