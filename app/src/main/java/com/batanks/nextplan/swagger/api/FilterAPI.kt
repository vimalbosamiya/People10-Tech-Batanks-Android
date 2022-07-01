package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.AddFilter
import com.batanks.nextplan.swagger.model.EditFilter
import com.batanks.nextplan.swagger.model.FilterResultsList
import com.batanks.nextplan.swagger.model.Filters
import io.reactivex.Observable
import retrofit2.http.*

interface FilterAPI {
   @GET("api/filters")
   fun apiFiltersList(): Observable<Filters>

   @POST("api/filters/")
   fun apiCreateFilter(@Body data: AddFilter?): Observable<FilterResultsList>

   @DELETE("api/filters/{id}/")
   fun apiFilterDelete(@Path("id") id: Int?): Observable<String>

   @PUT("api/filters/{id}/")
   fun apiFilterUpdate(@Path("id") id:Int?,@Body data: AddFilter?) : Observable<FilterResultsList>
}