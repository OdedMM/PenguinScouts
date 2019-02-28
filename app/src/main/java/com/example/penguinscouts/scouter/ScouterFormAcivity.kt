package com.example.penguinscouts.scouter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.penguinscouts.R
import kotlinx.android.synthetic.main.activity_scouter_form.*
import kotlinx.android.synthetic.main.fragment_scouter_form.view.*

fun Context.ScouterFormIntent(gameId: Int): Intent {
    return Intent(this, ScouterFormActivity::class.java)
        .apply { putExtra(INTENT_GAME_ID, gameId) }
}

const val INTENT_GAME_ID = "game_id"

class ScouterFormActivity : AppCompatActivity() {

    private var currentScreen = 0
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private val formList = listOf(
        // todo: replace with real data entry fragments in order
        PlaceholderFragment.newInstance(1),
        PlaceholderFragment.newInstance(2),
        PlaceholderFragment.newInstance(3),
        PlaceholderFragment.newInstance(4)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scouter_form)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, formList)

        container.adapter = mSectionsPagerAdapter

        fab.setOnClickListener {
            currentScreen++
            if (currentScreen < formList.size)
                container.setCurrentItem(currentScreen, true)

            if (currentScreen == formList.size) {
                // todo send data to server and exit
                finish()
            }
        }
    }

    override fun onBackPressed() {
        if (--currentScreen < 0)
            super.onBackPressed()
        else
            container.setCurrentItem(currentScreen, true)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager, val fragmentList: List<Fragment>) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int = fragmentList.size
    }

    class PlaceholderFragment : Fragment() {
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_scouter_form, container, false)
            rootView.section_label.text = getString(R.string.section_format, arguments?.getInt(ARG_SECTION_NUMBER))
            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}