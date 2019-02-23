package com.example.penguinscouts.admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.penguinscouts.R
import com.example.penguinscouts.Urls
import com.example.penguinscouts.common.LoginRouteIntent
import com.example.penguinscouts.post
import com.example.penguinscouts.prefs
import kotlinx.android.synthetic.main.activity_admin.*
import org.jetbrains.anko.doAsync

fun Context.AdminIntent(): Intent {
    return Intent(this, AdminActivity::class.java)
}

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
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
