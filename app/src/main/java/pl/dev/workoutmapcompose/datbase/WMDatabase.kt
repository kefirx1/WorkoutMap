package pl.dev.workoutmapcompose.datbase

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.dev.workoutmapcompose.data.TrainingPlan
import pl.dev.workoutmapcompose.data.UserInfo
import pl.dev.workoutmapcompose.data.WeightHistory

@Database(entities = [UserInfo::class, WeightHistory::class, TrainingPlan::class], version = 7, exportSchema = false)
abstract class WMDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserInfoDao
    abstract fun weightHistoryDao(): WeightHistoryDao
    abstract fun trainingPlanDao(): TrainingPlanDao

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