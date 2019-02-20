package com.example.penguinscouts

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class LoginRouteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doAsync {
            val res = get(Urls.authenticate)?.jsonObject
            if (res != null) {
                uiThread {
                    if (res.has("error")) {
                        toast(res["message"].toString())
                    } else {
                        prefs.username = res["username"].toString()
                        when (res["type"] as Int) {
                            0 -> { startActivity(ScouterIntent()); finish() }
                            1 -> { startActivity(ManagerIntent()); finish() }
                            2 -> { startActivity(CoachIntent()); finish() }
                            3 -> toast("${res["username"]} admin")
                            else -> toast("${res["username"]} unknown user type")
                        }
                    }
                }
            } else toast("No internet connection")
            startActivity(LoginIntent())
            finish()
        }
    }
}
