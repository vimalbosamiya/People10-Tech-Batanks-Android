package com.batanks.nextplan.swagger.model

import com.google.gson.annotations.SerializedName

data class Event(
        val id: Int,
        val title: String,
        val detail: String?,
        @SerializedName("private")
        val _private: Boolean,
        val category: CategoryList?,
        val max_guests: Int?,
        val draft: Boolean,
        val periodicity: Periodicity?,
        val creator: Creator,
        val dates: ArrayList<EventDate>?,
        val places: ArrayList<EventPlace>?,
        val tasks: ArrayList<Task>?,
        val activities: ArrayList<Activity>?,
//        val guests: EventInvitation,
        val comments : ArrayList<Comment>?,
        val guests : ArrayList<Guests>?,
        val created: String?,
        val modified: String?,
        val vote_place_closed: Boolean,
        val vote_date_closed: Boolean,
        val comments_closed : Boolean,
        val price : Double?,
        val price_currency : String,
        val status : String
)