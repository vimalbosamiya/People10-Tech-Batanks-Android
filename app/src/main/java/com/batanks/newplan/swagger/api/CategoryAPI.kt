package com.batanks.newplan.swagger.api

import com.batanks.newplan.swagger.model.InlineResponse200
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryAPI {

    @GET("api/category/")
    fun apiCategoryList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse200>
}