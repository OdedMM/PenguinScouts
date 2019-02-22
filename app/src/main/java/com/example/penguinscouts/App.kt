package com.example.penguinscouts

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import khttp.responses.Response

val prefs: Prefs by lazy {
    App.prefs!!
}

fun get(url: String): Response? {
    return try {
        khttp.get(url, cookies = mapOf("token" to (prefs.token ?: "")))
    } catch (e: Exception) { null }
}

fun post(url: String, body: Map<String, String>? = null): Response? {
    return try {
        khttp.post(url, cookies = mapOf("token" to (prefs.token ?: "")), data = body)
    } catch (e: Exception) { null }
}

class Match(
    val gameNumber: Int,
    val red1: String, val red2: String, val red3: String,
    val blue1: String, val blue2: String, val blue3: String
)

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
    private val usertypeKey = "usertype"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

    var token: String?
        get() = prefs.getString(tokenKey, "")
        set(value) = prefs.edit().putString(tokenKey, value).apply()

    var username: String?
        get() = prefs.getString(usernameKey, "")
        set(value) = prefs.edit().putString(usernameKey, value).apply()

    var usertype: Int
        get() = prefs.getInt(usertypeKey, -1)
        set(value) = prefs.edit().putInt(usertypeKey, value).apply()

    fun logout() {
        token = ""
        username = ""
        usertype = -1
    }
}
