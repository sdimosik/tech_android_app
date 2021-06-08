package android.technopolis.films.db

import android.technopolis.films.api.trakt.model.users.settings.*
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converter {
    @TypeConverter
    fun fromUserSettings(userSettings: UserSettings): String {
        return Json.encodeToString(userSettings)
    }

    @TypeConverter
    fun UserSettingsfromJsonString(string: String): UserSettings {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun fromUserAccountInfo(userAccountInfo: UserAccountInfo): String {
        return Json.encodeToString(userAccountInfo)
    }

    @TypeConverter
    fun UserAccountInfofromJsonString(string: String): UserAccountInfo {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun fromUserConnections(userConnections: UserConnections): String {
        return Json.encodeToString(userConnections)
    }

    @TypeConverter
    fun UserConnectionsfromJsonString(string: String): UserConnections {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun fromUserSharingText(userSharingText: UserSharingText): String {
        return Json.encodeToString(userSharingText)
    }

    @TypeConverter
    fun UserSharingTextfromJsonString(string: String): UserSharingText {
        return Json.decodeFromString(string)
    }

    @TypeConverter
    fun fromUserInfo(userInfo: UserInfo): String {
        return Json.encodeToString(userInfo)
    }

    @TypeConverter
    fun UserInfofromJsonString(string: String): UserInfo {
        return Json.decodeFromString(string)
    }
}