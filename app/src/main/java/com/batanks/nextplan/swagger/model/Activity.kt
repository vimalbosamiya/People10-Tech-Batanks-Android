package com.batanks.nextplan.swagger.model

data class Activity(
        val id: Int,
        val place: Place,
//        val price: String,
        val price : Double,
        //val participants: List<Int>,
        val participants : List<String>,
        val title: String,
        val detail: String,
        val date: String,
        val max_participants: Int,
        val price_currency: String,
        val per_person: Boolean,
        val duration: Int)