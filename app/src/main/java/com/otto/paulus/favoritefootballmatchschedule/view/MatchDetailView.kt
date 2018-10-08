package com.otto.paulus.favoritefootballmatchschedule.view

import com.otto.paulus.favoritefootballmatchschedule.model.EventDetail
import com.otto.paulus.favoritefootballmatchschedule.model.Team

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetailEvent(data: EventDetail)
    fun showDetailTeam(data: Team, isHomeTeam: Boolean)
}