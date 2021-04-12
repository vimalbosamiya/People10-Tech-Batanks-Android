package com.batanks.nextplan.swagger.model

data class PostPlaces (

        val place : PostPlaceInfo,
        val name : String?,
        val address : String?,
        val zipcode : String?,
        val city : String?,
        val country : String?,
        val map : Boolean?,
        var visibility : Boolean = true
)