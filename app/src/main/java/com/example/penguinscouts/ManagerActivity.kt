package com.example.penguinscouts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

import kotlinx.android.synthetic.main.activity_manager.*
import kotlinx.android.synthetic.main.content_manager.*
import org.jetbrains.anko.sdk27.coroutines.onClick

fun Context.ManagerIntent() : Intent {
    return Intent(this, ManagerActivity::class.java)
}

class ManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        username.text = "Welcome ${prefs.username}!"
        btn_matchlist.onClick { startActivity(MatchListIntent()) }
    }

}
