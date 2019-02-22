package com.example.penguinscouts.scouter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.example.penguinscouts.R

import kotlinx.android.synthetic.main.activity_scouter_form.*

fun Context.ScouterFormIntent(gameId : Int): Intent {
    return Intent(this, ScouterFormActivity::class.java).apply {
        putExtra(INTENT_GAME_ID, gameId)
    }
}

val INTENT_GAME_ID = "game_id"

class ScouterFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scouter_form)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gameId = intent.getIntExtra(INTENT_GAME_ID, 0)
        require(gameId > 0) { "no game_id provided from scout form activity" }
    }

}
