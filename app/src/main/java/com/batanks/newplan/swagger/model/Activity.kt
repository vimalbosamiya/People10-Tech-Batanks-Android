package com.batanks.newplan.swagger.model

data class Activity(
        val id: Int,
        val place: Place,
        val price: String,
        val title: String,
        val detail: String,
        val date: String,
        val max_participants: Int,
        val price_currency: String,
        val per_person: Boolean,
        val duration: Long,
        val participants: List<Int>)