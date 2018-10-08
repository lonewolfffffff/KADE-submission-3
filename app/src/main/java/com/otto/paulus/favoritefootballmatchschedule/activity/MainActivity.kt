package com.otto.paulus.favoritefootballmatchschedule.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.otto.paulus.favoritefootballmatchschedule.R
import com.otto.paulus.favoritefootballmatchschedule.activity.fragment.MatchListFragment
import com.otto.paulus.favoritefootballmatchschedule.model.Event
import com.otto.paulus.favoritefootballmatchschedule.util.*
import org.jetbrains.anko.AnkoLogger
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), MatchListFragment.OnFragmentInteractionListener, AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navMatch.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigate_prev_match -> {
                    replaceFragment(MatchListFragment.newInstance(true), framelayout.id)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigate_next_match -> {
                    replaceFragment(MatchListFragment.newInstance(isPrevMatch = false), framelayout.id)
                    return@OnNavigationItemSelectedListener true
                }
            }
            return@OnNavigationItemSelectedListener false
        })

        addFragment(MatchListFragment(), framelayout.id)
    }

    override fun onMatchListItemClick(match: Event) {
        startActivity<DetailActivity>(
                "MATCH_ID" to match.eventId,
                "HOME_TEAM_ID" to match.homeTeamId,
                "AWAY_TEAM_ID" to match.awayTeamId
        )

    }

}
