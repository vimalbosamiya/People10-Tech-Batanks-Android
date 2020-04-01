package com.batanks.newplan.swagger.model

import com.google.gson.annotations.SerializedName

data class EventList(
        val pk: Int,
        val title: String,
        val detail: String,
        @SerializedName("private")
        val _private: Boolean,
        val category: CategoryList,
        val place: Place,
        val date: EventDate,
        val periodicity: Periodicity)
