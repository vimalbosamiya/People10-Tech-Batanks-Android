package com.batanks.nextplan.swagger.model

data class PostActivities (

        val place : PostPlaceInfo?,
        val price : Int?,
        val participants : ArrayList<String>?,
        val title : String?,
        val detail : String?,
        val date : String?,
        val max_participants : Int?,
        val per_person : Boolean?,
        val duration : Int?
)