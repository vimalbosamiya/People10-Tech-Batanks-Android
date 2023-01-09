package com.batanks.nextplan.swagger.model

data class Comment(
        //val comment : String,
        val id : Int,
        val created : String,
        val author : String,
        val message  : String,
        val user_status : String
        //var visibility : Boolean = false
          )