package com.batanks.nextplan.swagger.model

data class Periodicity(
        val unit: String?,
        val occurrences : String?,
        val end_date : String?)

/*
enum class UnitEnum(private val value: String) {
    D("d"),
    W("w"),
    M("m"),
    Y("y");
}*/
