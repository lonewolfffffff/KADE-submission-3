package com.otto.paulus.favoritefootballmatchschedule.view

import com.otto.paulus.favoritefootballmatchschedule.model.Event

interface MatchListView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}