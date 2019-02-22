package com.example.penguinscouts.coatch

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.penguinscouts.*
import com.example.penguinscouts.common.LoginRouteIntent

import kotlinx.android.synthetic.main.activity_coach.*

fun Context.CoachIntent() : Intent {
    return Intent(this, CoachActivity::class.java)
}

class CoachActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coach)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.root_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.logout) {
            post(Urls.logout)
            prefs.logout()
            startActivity(LoginRouteIntent())
            finish()
            return true
        }
        return false
    }

}