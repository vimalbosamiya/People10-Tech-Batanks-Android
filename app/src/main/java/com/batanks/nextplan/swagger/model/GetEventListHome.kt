package com.batanks.nextplan.swagger.model

data class GetEventListHome (val pk : Int,
                             val title : String,
                             val detail : String,
                             val private : Boolean,
                             val category : CategoryList,
                             val place : Place,
                             val date : EventDate,
                             val periodicity : Periodicity,
                             val draft : Boolean,
                             val creator : Creator,
                             val status : String)
