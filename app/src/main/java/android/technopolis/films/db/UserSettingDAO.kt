package android.technopolis.films.db

import android.technopolis.films.model.SettingForUser
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface UserSettingDAO {
    @Query("SELECT * FROM user_setting")
    fun getUserSetting(): Flow<List<SettingForUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUserSetting(settingForUser: SettingForUser)
}
