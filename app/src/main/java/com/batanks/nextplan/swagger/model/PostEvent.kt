package com.batanks.nextplan.swagger.model

data class PostEvent (
        val title : String,
        val detail : String,
        val private : Boolean,
        val category : Int,
        val max_guests : Int,
        val draft : Boolean,
        val periodicity : Periodicity,
        val creator : PostCreator,
        val dates : ArrayList<PostDates>,
        val places : ArrayList<PostPlaces>,
        val tasks : ArrayList<PostTasks>,
        val activities : ArrayList<PostActivities>,
        val guests : PostGuests,
        val comments : ArrayList<PostComments>,
        val vote_place_closed : Boolean,
        val vote_date_closed : Boolean,
        val comments_closed : Boolean
)