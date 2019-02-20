package com.example.penguinscouts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_match_list.*
import kotlinx.android.synthetic.main.content_scouter.*
import kotlinx.android.synthetic.main.scout_game_list_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

fun Context.MatchListIntent() : Intent {
    return Intent(this, MatchListActivity::class.java)
}

class MatchListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rv_list.layoutManager = LinearLayoutManager(this)

        val gamelist = arrayListOf(2, 3, 4, 555, 648, 64555)

        rv_list.adapter = ScouterGameListAdapter(gamelist, this)
    }

    class ScouterGameListAdapter(val items: ArrayList<Int>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
        override fun getItemCount(): Int {
            return items.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.scout_game_list_item, parent, false))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.gameNumber.text = items[position].toString()
            holder.root.onClick { context.startActivity(context.ScouterFormIntent(items[position])) }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view.root
        val gameNumber = view.gameText
    }

}
