package com.example.penguinscouts.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.example.penguinscouts.*
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

fun Context.LoginIntent() : Intent {
    return Intent(this, LoginActivity::class.java)
}

class LoginActivity : AppCompatActivity() {

    private var mAuthTask: UserLoginTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        sign_in_button.setOnClickListener { attemptLogin() }
    }

    private fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }

        tv_username.error = null
        password.error = null

        val usernameStr = tv_username.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        if (TextUtils.isEmpty(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        if (TextUtils.isEmpty(usernameStr)) {
            tv_username.error = getString(R.string.error_field_required)
            focusView = tv_username
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            showProgress(true)
            mAuthTask = UserLoginTask(usernameStr, passwordStr)
            mAuthTask!!.execute(null as Void?)
        }
    }

    private fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        login_form.visibility = if (show) View.GONE else View.VISIBLE
        login_form.animate()
            .setDuration(shortAnimTime)
            .alpha((if (show) 0 else 1).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_form.visibility = if (show) View.GONE else View.VISIBLE
                }
            })

        login_progress.visibility = if (show) View.VISIBLE else View.GONE
        login_progress.animate()
            .setDuration(shortAnimTime)
            .alpha((if (show) 1 else 0).toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
                }
            })
    }

    inner class UserLoginTask internal constructor(private val mUsername: String, private val mPassword: String) : AsyncTask<Void, Void, JSONObject?>() {
        override fun doInBackground(vararg params: Void): JSONObject? {
            val body = mapOf("username" to mUsername, "password" to mPassword)
            val res = post(Urls.login, body = body)
            return res?.jsonObject
        }

        override fun onPostExecute(result: JSONObject?) {
            mAuthTask = null
            showProgress(false)

            if (result != null && result["success"] as Boolean) {
                prefs.token = result["token"].toString()
                startActivity(LoginRouteIntent())
                finish()
            } else {
                password.error = getString(R.string.error_incorrect_password)
                password.requestFocus()
            }
        }

        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }
}
