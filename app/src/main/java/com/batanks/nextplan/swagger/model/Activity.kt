package com.batanks.nextplan.swagger.model

data class Activity(
        val id: Int,
        val place: Place,
        val price : Double,
        val participants : ArrayList<ActivityParticipant>,
        val is_user_subscribe : Boolean,
        val title: String,
        val detail: String,
        val date: String,
        val max_participants: Int,
        val price_currency: String,
        val per_person: Boolean,
        val duration: Int)

