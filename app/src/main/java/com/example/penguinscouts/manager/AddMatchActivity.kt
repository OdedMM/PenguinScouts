package com.example.penguinscouts.manager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.penguinscouts.R
import com.example.penguinscouts.Urls
import com.example.penguinscouts.post
import kotlinx.android.synthetic.main.activity_add_match.*
import kotlinx.android.synthetic.main.content_add_match.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

fun Context.AddMatchIntent(): Intent {
    return Intent(this, AddMatchActivity::class.java)
}

class AddMatchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_match)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            doAsync {
                val res = post(
                    Urls.match, hashMapOf(
                        "gameNumber" to et_gameNumber.text.toString(),
                        "red1" to red1.text.toString(),
                        "red2" to red2.text.toString(),
                        "red3" to red3.text.toString(),
                        "blue1" to blue1.text.toString(),
                        "blue2" to blue2.text.toString(),
                        "blue3" to blue3.text.toString()
                    )
                )
                uiThread { toast(res?.text.toString()) }
            }
        }
    }

}
