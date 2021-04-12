package com.batanks.nextplan.swagger.model

data class SettingsPost (

        val language : String?,
        val currency : String?,
        val notify_email : Boolean?,
        val notify_invitations : Boolean?,
        val notify_searches : Boolean?,
        val notify_comment : Boolean?
)