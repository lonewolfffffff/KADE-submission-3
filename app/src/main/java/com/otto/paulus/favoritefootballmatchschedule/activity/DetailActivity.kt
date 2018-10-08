package com.otto.paulus.favoritefootballmatchschedule.activity

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.otto.paulus.favoritefootballmatchschedule.R
import com.otto.paulus.favoritefootballmatchschedule.api.ApiRepository
import com.otto.paulus.favoritefootballmatchschedule.model.EventDetail
import com.otto.paulus.favoritefootballmatchschedule.model.Team
import com.otto.paulus.favoritefootballmatchschedule.presenter.MatchDetailPresenter
import com.otto.paulus.favoritefootballmatchschedule.util.formatDate
import com.otto.paulus.favoritefootballmatchschedule.util.invisible
import com.otto.paulus.favoritefootballmatchschedule.util.parse
import com.otto.paulus.favoritefootballmatchschedule.util.visible
import com.otto.paulus.favoritefootballmatchschedule.view.MatchDetailView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoLogger
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.info

class DetailActivity: AppCompatActivity(), MatchDetailView, AppBarLayout.OnOffsetChangedListener, AnkoLogger {
    private val presenter: MatchDetailPresenter = MatchDetailPresenter(this, ApiRepository(), Gson())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val matchId = intent.getStringExtra("MATCH_ID")
        val homeTeamId = intent.getStringExtra("HOME_TEAM_ID")
        val awayTeamId = intent.getStringExtra("AWAY_TEAM_ID")

        presenter.getEventDetail(matchId)

        presenter.getTeamDetail(homeTeamId)
        presenter.getTeamDetail(awayTeamId,false)

        appBar.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        info("Offset: "+verticalOffset+" -> "+(verticalOffset+200)/-40f)
        animateToolbar(verticalOffset)
    }

    private fun animateToolbar(verticalOffset: Int) {
        when(verticalOffset) {
            // fade ins
            in -240..-200 -> {
                tvMatchDateToolbar.visible()
                tvMatchDateToolbar.animate().alpha((200 - verticalOffset)/40f)
                tvMatchDateToolbar.animate().scaleX((verticalOffset+200)/-40f)
                tvMatchDateToolbar.animate().scaleY((verticalOffset+200)/-40f)
                ivHomeBadgeToolbar.visible()
                ivHomeBadgeToolbar.animate().alpha((200 - verticalOffset)/40f)
                ivHomeBadgeToolbar.animate().scaleX((verticalOffset+200)/-40f)
                ivHomeBadgeToolbar.animate().scaleY((verticalOffset+200)/-40f)
                tvScoreToolbar.visible()
                tvScoreToolbar.animate().alpha((200 - verticalOffset)/40f)
                tvScoreToolbar.animate().scaleX((verticalOffset+200)/-40f)
                tvScoreToolbar.animate().scaleY((verticalOffset+200)/-40f)
                ivAwayBadgeToolbar.visible()
                ivAwayBadgeToolbar.animate().alpha((200 - verticalOffset)/40f)
                ivAwayBadgeToolbar.animate().scaleX((verticalOffset+200)/-40f)
                ivAwayBadgeToolbar.animate().scaleY((verticalOffset+200)/-40f)
            }
            // disappear
            in -119..0 -> {
                tvMatchDateToolbar.alpha = 0f
                tvMatchDateToolbar.invisible()
                ivHomeBadgeToolbar.alpha = 0f
                ivHomeBadgeToolbar.invisible()
                tvScoreToolbar.alpha = 0f
                tvScoreToolbar.invisible()
                ivAwayBadgeToolbar.alpha = 0f
                ivAwayBadgeToolbar.invisible()
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailEvent(match: EventDetail) {
        tvMatchDate.text = match.eventDate?.formatDate()
        tvMatchDateToolbar.text = match.eventDate?.formatDate()

        tvHomeTeamName.text = match.homeTeam

        if (match.homeScore === null) {
            tvScore.text = "vs"
            tvScoreToolbar.text = " vs "
        }
        else {
            tvScore.text = match.homeScore + " - " + match.awayScore
            tvScoreToolbar.text = match.homeScore + " - " + match.awayScore
        }

        tvAwayTeamName.text = match.awayTeam

        tvHomeFormation.text = match.homeFormation
        tvAwayFormation.text = match.awayFormation

        tvHomeGoals.text = match.homeGoalDetails?.parse()
        tvAwayGoals.text = match.awayGoalDetails?.parse()

        tvHomeShots.text = match.homeShots
        tvAwayShots.text = match.awayShots

        tvHomeGoalKeeper.text = match.homeGoalKeeper
        tvAwayGoalKeeper.text = match.awayGoalKeeper

        tvHomeDefenders.text = match.homeDefense?.parse()
        tvAwayDefenders.text = match.awayDefense?.parse()

        tvHomeMidfielders.text = match.homeMidfield?.parse()
        tvAwayMidfielders.text = match.awayMidfield?.parse()

        tvHomeForwards.text = match.homeForward?.parse()
        tvAwayForwards.text = match.awayForward?.parse()

        tvHomeSubstitutes.text = match.homeSubstitutes?.parse()
        tvAwaySubstitutes.text = match.awaySubstitutes?.parse()
    }

    override fun showDetailTeam(data: Team, isHomeTeam: Boolean) {
        Picasso.get().load(data.teamBadge).into(if(isHomeTeam) ivHomeBadge else ivAwayBadge)
        Picasso.get().load(data.teamBadge).into(if(isHomeTeam) ivHomeBadgeToolbar else ivAwayBadgeToolbar)
    }
}