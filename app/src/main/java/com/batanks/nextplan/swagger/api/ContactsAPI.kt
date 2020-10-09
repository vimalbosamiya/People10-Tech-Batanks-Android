package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.InlineResponse2001
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactsAPI {

    @DELETE("api/contacts/{id}/")
    fun apiContactsDelete(@Path("id") id: String?): Observable<Void>

    @GET("api/contacts/")
    fun apiContactsList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Observable<InlineResponse2001>
}