package android.technopolis.films.db

import android.content.Context
import android.technopolis.films.model.SettingForUser
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SettingForUser::class], version = 1)
@TypeConverters(Converter::class)
abstract class UserSettingDatabase : RoomDatabase() {

    abstract fun getUserSettingDAO(): UserSettingDAO

    companion object {
        @Volatile
        private var instance: UserSettingDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserSettingDatabase::class.java,
                "user_setting_db"
            ).build()
    }
}