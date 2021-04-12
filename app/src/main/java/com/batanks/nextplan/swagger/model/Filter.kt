package com.batanks.nextplan.swagger.model

data class Filter (

        val filter : String?,
        val imgUrl : String?,
        val filterType: FilterType?,
        var selection : Boolean?)

enum class FilterType{

    CATEGORY,
    EVENTTYPE,
    FOLLOWUP
}