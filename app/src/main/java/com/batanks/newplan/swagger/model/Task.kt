package com.batanks.newplan.swagger.model

data class Task(
        val id: Int,
        val price: String,
        val name: String,
        val description: String,
        val price_currency: String,
        val per_person: Boolean,
        val assignee: Int)