package com.batanks.nextplan.swagger.api

import retrofit2.Call
import retrofit2.http.GET

interface API {

    @GET("api/")
    fun apiList(): Call<Void>
}