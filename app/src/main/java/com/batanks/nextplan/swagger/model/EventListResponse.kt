package com.batanks.nextplan.swagger.model

data class EventListResponse (
                                val count: Int,
                                val next : String,
                                val previous : String,
                                val results : List<EventList>)