package anamapp.project.bean

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {

    val EMPTY = ""
    val UID = "UID"
    val EMAIL = "email"
    val myPreferences = "myPrefs"
    val prefs: SharedPreferences = context.getSharedPreferences(myPreferences, 0)

    var uid: String
            get() = prefs.getString(UID, EMPTY)
            set(value) = prefs.edit().putString(UID, value).apply()

    var email: String
        get() = prefs.getString(EMAIL, EMPTY)
        set(value) = prefs.edit().putString(EMAIL, value).apply()
}