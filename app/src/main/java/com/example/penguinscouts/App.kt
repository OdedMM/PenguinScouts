package com.example.penguinscouts

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import khttp.responses.Response

val prefs: Prefs by lazy {
    App.prefs!!
}

fun get(url: String): Response? {
    try {
        return khttp.get(url, cookies = mapOf("token" to (prefs.token ?: "")))
    } catch (e: Exception) {
        return null
    }
}

fun post(url: String, body: Map<String, String>): Response? {
    try {
        return khttp.post(url, cookies = mapOf("token" to (prefs.token ?: "")), data = body)
    } catch (e: Exception) {
        return null
    }
}

class App : Application() {
    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }
}

class Prefs(context: Context) {
    private val prefsFilename = "com.example.myapplication.prefs"
    private val tokenKey = "token"
    private val usernameKey = "username"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var token: String?
        get() = prefs.getString(tokenKey, "")
        set(value) = prefs.edit().putString(tokenKey, value).apply()

    var username: String?
        get() = prefs.getString(usernameKey, "")
        set(value) = prefs.edit().putString(usernameKey, value).apply()
}
