package com.batanks.nextplan.swagger.model

data class NotificationsResponse (

        val count : Int,
        val next : String,
        val previous : String,
        val results : ArrayList<NotificationModel>
)