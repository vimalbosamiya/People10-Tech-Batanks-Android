package com.batanks.newplan.swagger.model

data class APNSDevice(
        val id: Int,
        val name: String,
        val application_id: String,
        val registration_id: String,
        val device_id: String,
        val active: Boolean,
        val date_created: String)