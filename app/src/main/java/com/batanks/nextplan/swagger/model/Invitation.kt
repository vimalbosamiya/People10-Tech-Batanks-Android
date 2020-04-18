package com.batanks.nextplan.swagger.model

data class Invitation(
        val id: Int,
        val status: StatusEnum,
        val amount: Int)

enum class StatusEnum {
    PD, AC, DN;
}