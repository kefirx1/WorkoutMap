package pl.dev.workoutmapcompose.datbase

import androidx.room.*
import pl.dev.workoutmapcompose.data.UserInfo

@Dao
interface UserInfoDao {

    @Insert
    fun insert(userInfo: UserInfo)

    @Update
    fun update(userInfo: UserInfo)

    @Delete
    fun delete(userInfo: UserInfo)

    @Query("SELECT COUNT(name) FROM userInfo")
    fun userExist(): Int

    @Query("SELECT * FROM userInfo WHERE user_id=1")
    fun getUserInfo(): UserInfo

    @Query("DELETE FROM userInfo WHERE user_id=1")
    fun deleteUser()


}