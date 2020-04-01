package com.batanks.newplan.swagger.model

data class NotificationsList(
        val id: Int,
        val event: EventList,
        val read: Boolean,
        val last_modified: String)