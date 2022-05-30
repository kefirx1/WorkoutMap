package pl.dev.workoutmapcompose.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userInfo")
data class UserInfo (

    @ColumnInfo(name = "userFirebaseID")
    var userFirebaseID: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "surName")
    var surName: String,

    @ColumnInfo(name = "height")
    var height: String,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "age")
    var age: String,

    @ColumnInfo(name = "lastTrainingDate")
    var lastTrainingDate: String){

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_id")
    var user_id: Int = 1
}
