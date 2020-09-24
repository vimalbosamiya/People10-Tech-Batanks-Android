package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.InlineResponse200
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoryAPI {

    @GET("api/category/")
    fun apiCategoryList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Observable<InlineResponse200>
}