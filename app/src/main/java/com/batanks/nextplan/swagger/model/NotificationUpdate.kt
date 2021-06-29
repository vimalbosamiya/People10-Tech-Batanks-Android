package com.batanks.nextplan.swagger.model

data class NotificationUpdate(
        val id: Int,
        val event: EventList,
        var read: Boolean,
        val last_modified: String)