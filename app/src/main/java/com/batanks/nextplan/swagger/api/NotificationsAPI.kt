package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.InlineResponse2003
import com.batanks.nextplan.swagger.model.Notification
import retrofit2.Call
import retrofit2.http.*

interface NotificationsAPI {

    @GET("api/notifications/")
    fun apiNotificationsList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse2003>

    @PATCH("api/notifications/{id}/")
    fun apiNotificationsPartialUpdate(@Path("id") id: String?, @Body data: Notification?): Call<Notification>

    @PUT("api/notifications/{id}/")
    fun apiNotificationsUpdate(@Path("id") id: String?, @Body data: Notification?): Call<Notification>
}