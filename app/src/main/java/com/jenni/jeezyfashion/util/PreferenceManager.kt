package com.jenni.jeezyfashion.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(var context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    private var privateMode = 0

    companion object {
        private const val PREFERENCE_MANAGER = "com.jenni.jeezyfashion"
        private const val WAITING_FOR_CODE = "waiting_for_code"
        private const val LOGGED_IN = "logged_in"
    }

    var waitingForCode: Boolean
        get() = pref.getBoolean(WAITING_FOR_CODE, false)
        set(isWaiting) {
            editor.putBoolean(WAITING_FOR_CODE, isWaiting)
            editor.commit()
        }

    var loggedIn: Boolean
        get() = pref.getBoolean(LOGGED_IN, false)
        set(isLoggedIn) {
            editor.putBoolean(LOGGED_IN, isLoggedIn)
            editor.commit()
        }

    init {
        pref = context.getSharedPreferences(PREFERENCE_MANAGER, privateMode)
        editor = pref.edit()
    }
}