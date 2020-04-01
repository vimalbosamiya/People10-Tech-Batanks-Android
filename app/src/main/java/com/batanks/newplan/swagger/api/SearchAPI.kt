package com.batanks.newplan.swagger.api

import com.batanks.newplan.swagger.model.InlineResponse2002
import com.batanks.newplan.swagger.model.InlineResponse2004
import com.batanks.newplan.swagger.model.Search
import retrofit2.Call
import retrofit2.http.*

interface SearchAPI {

    @GET("api/search/")
    fun apiSearchList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse2002>

    @POST("api/searches/")
    fun apiSearchesCreate(@Body data: Search?): Call<Search>

    @DELETE("api/searches/{id}/")
    fun apiSearchesDelete(@Path("id") id: String?): Call<Void>

    @GET("api/searches/")
    fun apiSearchesList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse2004>

    @PATCH("api/searches/{id}/")
    fun apiSearchesPartialUpdate(@Path("id") id: String?, @Body data: Search?): Call<Search>

    @GET("api/searches/{id}/")
    fun apiSearchesRead(@Path("id") id: String?, @Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse2002>

    @PUT("api/searches/{id}/")
    fun apiSearchesUpdate(@Path("id") id: String?, @Body data: Search?): Call<Search>
}