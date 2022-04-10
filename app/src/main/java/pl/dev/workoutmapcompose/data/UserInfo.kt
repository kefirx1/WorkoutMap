package pl.dev.workoutmapcompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userInfo")
data class UserInfo (
    var name: String,
    var surName: String,
    var height: String,
    var gender: String,
    var age: String,
    var monday: String,
    var tuesday: String,
    var wednesday: String,
    var thursday: String,
    var friday: String,
    var saturday: String,
    var sunday: String,
    var lastTrainingDate: String) {

    @PrimaryKey(autoGenerate = false)
    var user_id: Int = 1
}