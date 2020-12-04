package com.batanks.nextplan.swagger.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    @GET("api/currencies/")
    fun apiCurrencyList(): Observable<ArrayList<String>>
}