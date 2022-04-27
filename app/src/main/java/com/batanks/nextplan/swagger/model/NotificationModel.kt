package com.batanks.nextplan.swagger.model

data class NotificationModel (

        val id : Int,
        val message : String,
        val read : Boolean,
        val last_modified : String,
        val event_id : Int,
        val event_creator_id : Int,
        val event_is_private : Boolean,
        val event_is_draft : Boolean
)

