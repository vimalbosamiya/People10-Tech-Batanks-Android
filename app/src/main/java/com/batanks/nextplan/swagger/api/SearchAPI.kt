package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.InlineResponse2002
import com.batanks.nextplan.swagger.model.InlineResponse2004
import com.batanks.nextplan.swagger.model.Search
import com.batanks.nextplan.swagger.model.UserSearch
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface SearchAPI {

    /*@GET("api/search/")
    fun apiSearchList(*//*@Query("limit") limit: Int?, @Query("offset") offset: Int?*//*@Query("type") type:String, @Query("category") category:String?, @Query("keywords") keywords:String?): Observable<InlineResponse2002>*/

    @GET("api/search/events/")
    fun apiSearchList(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/@Query("type") type:String, @Query("category") category:String?, @Query("keywords") keywords:String?): Observable<InlineResponse2002>

    @GET("api/search/events/")
    fun apiSearchListWithType(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/@Query("type") type:String): Observable<InlineResponse2002>

    @GET("api/search/events/")
    fun apiSearchListWithTypeAndCategory(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/@Query("type") type:String, @Query("category") category:String?): Observable<InlineResponse2002>

    @GET("api/search/events/")
    fun apiSearchListWithTypeAndKeyword(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/@Query("type") type:String, @Query("keywords") keywords:String?): Observable<InlineResponse2002>

    @GET("api/search/users/")
    fun apiSearchUsers(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/@Query("search") search:String?): Observable<UserSearch>

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