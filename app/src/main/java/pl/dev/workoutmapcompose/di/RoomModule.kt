package pl.dev.workoutmapcompose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.dev.workoutmapcompose.datbase.WMDatabase
import pl.dev.workoutmapcompose.datbase.dao.UserInfoDao
import pl.dev.workoutmapcompose.datbase.dao.WeightHistoryDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideWMDB(@ApplicationContext context: Context): WMDatabase {
        return Room.databaseBuilder(
            context,
            WMDatabase::class.java,
            WMDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideWeightHistoryDAO(wmDatabase: WMDatabase): WeightHistoryDao {
        return wmDatabase.weightHistoryDao()
    }

    @Singleton
    @Provides
    fun provideUserInfoDAO(wmDatabase: WMDatabase): UserInfoDao {
        return wmDatabase.userInfoDao()
    }
}