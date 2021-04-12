package com.batanks.nextplan.swagger.model

data class InlineResponse2002(
        val count: Int,
        val next: String?,
        val previous: String?,
        val results: ArrayList<GetEventListHome>,
        val unread_notifications : Int)