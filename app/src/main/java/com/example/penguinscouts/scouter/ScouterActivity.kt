package com.example.penguinscouts.scouter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_scouter.*
import kotlinx.android.synthetic.main.content_scouter.*
import kotlinx.android.synthetic.main.scout_game_list_item.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import android.view.*
import com.example.penguinscouts.*
import com.example.penguinscouts.common.LoginRouteIntent


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

        val gamelist = arrayListOf(
            Match(1, "rrr1", "rrr2", "rrr3", "bbb1", "bbb2", "bbb3"),
            Match(1, "r2rr1", "r123rr2", "rrr3", "b1", "bbb2", "2b3"),
            Match(1, "rr21r1", "rr132r2", "rr11r3", "b32b1", "bbb2", "113")
        )
        rv_list.adapter = ScouterGameListAdapter(gamelist, this)
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

    class ScouterGameListAdapter(val items: ArrayList<Match>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
        override fun getItemCount(): Int {
            return items.size
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
            holder.gameNumber.text = items[position].gameNumber.toString()
            holder.root.onClick { context.startActivity(context.ScouterFormIntent(items[position].gameNumber)) }
            holder.red1.text = items[position].red1
            holder.red2.text = items[position].red2
            holder.red3.text = items[position].red3
            holder.blue1.text = items[position].blue1
            holder.blue2.text = items[position].blue2
            holder.blue3.text = items[position].blue3
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
    }

}
