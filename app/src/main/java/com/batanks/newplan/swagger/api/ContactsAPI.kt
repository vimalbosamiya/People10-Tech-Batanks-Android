package com.batanks.newplan.swagger.api

import com.batanks.newplan.swagger.model.InlineResponse2001
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactsAPI {

    @DELETE("api/contacts/{id}/")
    fun apiContactsDelete(@Path("id") id: String?): Call<Void>

    @GET("api/contacts/")
    fun apiContactsList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse2001>
}