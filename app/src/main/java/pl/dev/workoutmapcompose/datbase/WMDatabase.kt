package pl.dev.workoutmapcompose.datbase

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.datbase.dao.UserInfoDao
import pl.dev.workoutmapcompose.datbase.dao.WeightHistoryDao

@Database(entities = [UserInfo::class, WeightHistory::class], version = 9, exportSchema = false)
abstract class WMDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao
    abstract fun weightHistoryDao(): WeightHistoryDao

    companion object{
        val DATABASE_NAME: String = "workoutMapDB.db"
    }

}