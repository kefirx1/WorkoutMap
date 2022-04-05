package pl.dev.workoutmapcompose.datbase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.dev.workoutmapcompose.data.UserInfo

@Database(entities = [UserInfo::class], version = 1, exportSchema = false)
abstract class WMDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao

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