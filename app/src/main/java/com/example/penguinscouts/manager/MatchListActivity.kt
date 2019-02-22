package com.example.penguinscouts.manager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.penguinscouts.*
import com.example.penguinscouts.scouter.ScouterFormIntent

import kotlinx.android.synthetic.main.activity_match_list.*
import kotlinx.android.synthetic.main.content_scouter.*
import kotlinx.android.synthetic.main.scout_game_list_item.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.uiThread
import org.json.JSONArray

fun Context.MatchListIntent(): Intent {
    return Intent(this, MatchListActivity::class.java)
}

class MatchListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { startActivity(AddMatchIntent()) }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rv_list.layoutManager = LinearLayoutManager(this)
        doAsync {
            val gamelist = get(Urls.match)?.jsonArray
            uiThread {
                rv_list.adapter = ScouterGameListAdapter(gamelist ?: JSONArray(), this@MatchListActivity)
            }
        }
    }

    class ScouterGameListAdapter(private val items: JSONArray, val context: Context) :
        RecyclerView.Adapter<ViewHolder>() {
        override fun getItemCount(): Int {
            return items.length()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.scout_game_list_item,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.gameNumber.text = items.getJSONObject(position).getInt("id").toString()
            holder.root.onClick { context.startActivity(context.ScouterFormIntent(items.getJSONObject(position).getInt("id"))) }
            holder.red1.text = items.getJSONObject(position).getString("red1")
            holder.red2.text = items.getJSONObject(position).getString("red2")
            holder.red3.text = items.getJSONObject(position).getString("red3")
            holder.blue1.text = items.getJSONObject(position).getString("blue1")
            holder.blue2.text = items.getJSONObject(position).getString("blue2")
            holder.blue3.text = items.getJSONObject(position).getString("blue3")
            holder.button.image = ContextCompat.getDrawable(context, R.drawable.ic_delete_black_24dp)
            holder.button.setOnClickListener{ Toast.makeText(context, "Todo delete match ${items.getJSONObject(position).getInt("id")}", Toast.LENGTH_SHORT).show() }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val root = view.root!!
        val gameNumber = view.gameText!!
        val red1 = view.red1!!
        val red2 = view.red2!!
        val red3 = view.red3!!
        val blue1 = view.blue1!!
        val blue2 = view.blue2!!
        val blue3 = view.blue3!!
        val button = view.button!!
    }

}
