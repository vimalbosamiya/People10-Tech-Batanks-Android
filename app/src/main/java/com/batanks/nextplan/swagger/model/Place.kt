package com.batanks.nextplan.swagger.model

data class Place(
        val name: String,
        val address: String,
        val zipcode: String,
        val city: String,
        val country: String,
        var map: Boolean,
        var latitude: Double,
        var longitude: Double)