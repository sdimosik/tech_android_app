package android.technopolis.films.utils

import android.content.Context
import android.net.ConnectivityManager

object Utils {
    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (connectivityManager.activeNetworkInfo == null) {
            return false
        }
        return true;
    }
}