package com.example.penguinscouts.scouter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast
import com.example.penguinscouts.*
import com.example.penguinscouts.common.LoginRouteIntent
import kotlinx.android.synthetic.main.activity_scouter.*
import kotlinx.android.synthetic.main.content_scouter.*
import kotlinx.android.synthetic.main.scout_game_list_item.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.image
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.uiThread
import org.json.JSONArray


fun Context.ScouterIntent(): Intent {
    return Intent(this, ScouterActivity::class.java)
}

class ScouterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scouter)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        tv_username.text = prefs.username

        rv_list.layoutManager = LinearLayoutManager(this)
        doAsync {
            val gamelist = get(Urls.match)?.jsonArray
            uiThread {
                rv_list.adapter =
                    ScouterGameListAdapter(gamelist ?: JSONArray(), this@ScouterActivity)
            }
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

    class ScouterGameListAdapter(val items: JSONArray, val context: Context) :
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
            holder.button.setOnClickListener {
                Toast.makeText(
                    context,
                    "Todo delete match ${items.getJSONObject(position).getInt("id")}",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
