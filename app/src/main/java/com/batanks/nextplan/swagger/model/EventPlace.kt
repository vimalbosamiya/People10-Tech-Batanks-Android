package com.batanks.nextplan.swagger.model

data class EventPlace(
        val id: Int,
        val place: Place,
        val name: String,
        val address: String,
        val zipcode: String,
        val city: String,
        val country: String,
        val map: Boolean,
        val votes: List<String>,
        var visibility : Boolean = true)