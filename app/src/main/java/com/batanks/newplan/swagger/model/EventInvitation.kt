package com.batanks.newplan.swagger.model

data class EventInvitation(
        val users: MutableList<Int>,
        val phones: MutableList<EventInvitationPhone>,
        val emails: MutableList<EventInvitationEmail>
)