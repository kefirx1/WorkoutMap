package pl.dev.workoutmapcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dev.workoutmapcompose.datbase.WMRepository
import pl.dev.workoutmapcompose.datbase.dao.UserInfoDao
import pl.dev.workoutmapcompose.datbase.dao.WeightHistoryDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideWMRepository(
        userInfoDao: UserInfoDao,
        weightHistoryDao: WeightHistoryDao
    ): WMRepository {
        return WMRepository(userInfoDao, weightHistoryDao)
    }

}