package com.example.penguinscouts.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.penguinscouts.*
import com.example.penguinscouts.admin.AdminIntent
import com.example.penguinscouts.coatch.CoachIntent
import com.example.penguinscouts.manager.ManagerIntent
import com.example.penguinscouts.scouter.ScouterIntent
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject

fun Context.LoginRouteIntent(): Intent {
    return Intent(this, LoginRouteActivity::class.java)
}

class LoginRouteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (prefs.token == "") {
            startActivity(LoginIntent())
            finish()
            return
        }

        doAsync {
            val res = getUserCredentials()
            uiThread {
                if (res != null) {
                    if (res.has("error")) {
                        toast(res["msg"].toString())
                        startActivity(LoginIntent())
                        finish()
                    } else {
                        prefs.username = res["username"].toString()
                        prefs.usertype = res["type"].toString().toInt()
                        when (res["type"] as Int) {
                            0 -> startActivity(ScouterIntent())
                            1 -> startActivity(ManagerIntent())
                            2 -> startActivity(CoachIntent())
                            3 -> startActivity(AdminIntent())
                            else -> {
                                toast("${res["username"]} unknown user type")
                                startActivity(LoginIntent())
                            }
                        }
                        finish()
                    }
                } else toast("No internet connection")
            }
        }
    }

    fun getUserCredentials() : JSONObject? {
        return if (prefs.token != "" && prefs.username != "" && prefs.usertype >= 0) // cached response
            JSONObject("{\"username\": \"${prefs.username}\", \"type\": ${prefs.usertype}}")
        else // get the response
            get(Urls.authenticate)?.jsonObject
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.root_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            doAsync {
                post(Urls.logout)
                prefs.logout()
            }
            startActivity(LoginRouteIntent())
            finish()
            return true
        }
        return false
    }
}
