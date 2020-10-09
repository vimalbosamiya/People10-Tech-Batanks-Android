package com.batanks.nextplan.swagger.model

data class Periodicity(
        val unit: String,
        val amount: Int)

enum class UnitEnum(private val value: String) {
    D("d"),
    W("w"),
    M("m"),
    Y("y");
}