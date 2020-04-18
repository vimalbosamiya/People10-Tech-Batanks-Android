package com.batanks.nextplan.swagger.model

data class InlineResponse2004(
        val count: Int,
        val next: String,
        val previous: String,
        val results: List<Search>)