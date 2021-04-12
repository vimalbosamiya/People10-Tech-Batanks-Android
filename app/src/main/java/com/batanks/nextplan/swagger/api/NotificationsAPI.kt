package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.InlineResponse2003
import com.batanks.nextplan.swagger.model.Notification
import com.batanks.nextplan.swagger.model.NotificationRead
import com.batanks.nextplan.swagger.model.NotificationsResponse
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface NotificationsAPI {

    @GET("api/notifications/")
    fun apiNotificationsList(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/): Observable<NotificationsResponse>

    @POST("api/notifications/mark_as_read/")
    fun apiNotificationMarkAsRead(@Body data: NotificationRead?): Observable<NotificationRead>

    @PATCH("api/notifications/{id}/")
    fun apiNotificationsPartialUpdate(@Path("id") id: String?, @Body data: Notification?): Call<Notification>

    @PUT("api/notifications/{id}/")
    fun apiNotificationsUpdate(@Path("id") id: String?, @Body data: Notification?): Call<Notification>
}