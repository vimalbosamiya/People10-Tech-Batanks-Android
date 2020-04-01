package com.batanks.newplan.swagger.model

import com.google.gson.annotations.SerializedName

data class Event(
        val id: Int,
        val title: String,
        val detail: String,
        @SerializedName("private")
        val _private: Boolean,
        val category: Int,
        val max_guests: Int,
        val draft: Boolean,
        val periodicity: Periodicity,
        val creator: Int,
        val dates: List<EventDate>,
        val places: List<EventPlace>,
        val tasks: List<Task>,
        val activities: List<Activity>,
        val guests: EventInvitation,
        val created: String,
        val modified: String,
        val vote_place_closed: Boolean,
        val vote_date_closed: Boolean
)