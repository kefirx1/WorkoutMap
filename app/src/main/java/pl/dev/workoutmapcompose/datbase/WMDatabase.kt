package pl.dev.workoutmapcompose.datbase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.data.WeightHistory
import pl.dev.workoutmapcompose.datbase.dao.UserInfoDao
import pl.dev.workoutmapcompose.datbase.dao.WeightHistoryDao

@Database(entities = [UserInfo::class, WeightHistory::class], version = 8, exportSchema = false)
abstract class WMDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao
    abstract fun weightHistoryDao(): WeightHistoryDao

    companion object{

        private var instance: WMDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: getInstance(context).also { instance = it}
        }

        fun getInstance(context: Context) : WMDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    WMDatabase::class.java,
                    "workoutMapDB.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

    }

}