package com.batanks.nextplan.swagger.model

data class PostPlaceInfo (

        val name : String?,
        val address : String?,
        val zipcode : String?,
        val city : String?,
        val country : String?,
        var map : Boolean?
)