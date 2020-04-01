package com.batanks.newplan.swagger.model

data class Place(
        val name: String,
        val address: String,
        val zipcode: String,
        val city: String,
        val country: String,
        val map: Boolean,
        val latitude: String,
        val longitude: String)