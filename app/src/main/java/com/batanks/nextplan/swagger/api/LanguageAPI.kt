package com.batanks.nextplan.swagger.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface LanguageAPI {

    @GET("api/languages/")
    fun apiLanguageList(): Observable<ArrayList<HashMap<String, String>>>
}