package anamapp.project.bean

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {

    val EMPTY = ""
    val UID = "UID"
    val EMAIL = "email"
    val CITY = "city"
    val STATE = "state"
    val ADDRESS = "address"
    val HOSPITALNAME = "NAME"
    val myPreferences = "myPrefs"
    val prefs: SharedPreferences = context.getSharedPreferences(myPreferences, 0)

    var uid: String
            get() = prefs.getString(UID, EMPTY)
            set(value) = prefs.edit().putString(UID, value).apply()

    var email: String
        get() = prefs.getString(EMAIL, EMPTY)
        set(value) = prefs.edit().putString(EMAIL, value).apply()

    var city: String
        get() = prefs.getString(CITY, EMPTY)
        set(value) = prefs.edit().putString(CITY, value).apply()

    var state: String
        get() = prefs.getString(STATE, EMPTY)
        set(value) = prefs.edit().putString(STATE, value).apply()

    var address: String
        get() = prefs.getString(ADDRESS, EMPTY)
        set(value) = prefs.edit().putString(ADDRESS, value).apply()

    var hospitalName: String
        get() = prefs.getString(HOSPITALNAME, EMPTY)
        set(value) = prefs.edit().putString(HOSPITALNAME, value).apply()
}