package com.mobdeve.s11.bueno.jasonpatrick.cryptobattle

import android.content.Context
import android.content.SharedPreferences

class ShareprefUtility {

    private var appPreferences: SharedPreferences? = null
    private val PREFS = "appPreferences"

    constructor(context: Context) {
        appPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
    }

    fun saveStringPreferences(key: String?, value: String?) {
        appPreferences!!.edit().putString(key, value).commit()
    }

    fun saveIntPreferences(key: String?, value: Int) {
        appPreferences!!.edit().putInt(key, value).commit()
    }

    fun getStringPreferences(key: String?): String? = appPreferences!!.getString(key, "1")
    fun getIntPreferences(key: String?): Int = appPreferences!!.getInt(key, 1)

    fun removePreferences(key: String?) {
        appPreferences!!.edit().remove(key).commit()
    }

    fun removeAllPreferences() {
        appPreferences!!.edit().clear().commit()
    }
}
