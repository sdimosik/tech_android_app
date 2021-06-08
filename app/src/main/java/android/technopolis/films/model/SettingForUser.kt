package android.technopolis.films.model

import android.technopolis.films.api.trakt.model.users.settings.UserSettings
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_setting")
data class SettingForUser(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @Embedded
    val userSettings: UserSettings
)
