package com.batanks.newplan.swagger.model

data class Notification(
        val id: Int,
        val event: EventList,
        var read: Boolean,
        val last_modified: String)