package com.batanks.nextplan.swagger.api

import ActivitySubscribe
import com.batanks.nextplan.swagger.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface EventAPI {

    @GET("api/event/accepted/")
    fun apiEventAcceptedList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse2002>

    @DELETE("api/event/{event_pk}/activity/{activity_pk}/")
    fun apiEventActivityDelete(@Path("activity_pk") activityPk: String?, @Path("event_pk") eventPk: String?): Observable<String>

    @GET("api/event/{event_pk}/activity/{activity_pk}/")
    fun apiEventActivityRead(@Path("activity_pk") activityPk: String?, @Path("event_pk") eventPk: String?): Call<Activity>

    @POST("api/event/{event_pk}/activity/{activity_pk}/subscribe/")
    fun apiEventActivitySubscribeCreate(@Path("activity_pk") activityPk: String?, @Path("event_pk") eventPk: String?, @Body data: Empty?): Observable<ActivitySubscribe>

    @POST("api/event/{event_pk}/activity/{activity_pk}/unsubscribe/")
    fun apiEventActivityUnsubscribe(@Path("activity_pk") activityPk: String?, @Path("event_pk") eventPk: String?, @Body data: Empty?): Observable<ActivitySubscribe>

    @PATCH("api/event/{id}/answer/")
    fun apiEventAnswerPartialUpdate(@Path("id") id: String?, @Body data: Invitation): Call<Invitation>

    @GET("api/event/{id}/answer/")
    fun apiEventAnswerRead(@Path("id") id: String?): Call<Invitation>

    @PUT("api/event/{id}/answer/")
    fun apiEventAnswerUpdate(@Path("id") id: String?, @Body data: EventAccept?): Observable<Invitation>

    @POST("api/event/{id}/assign/")
    fun apiEventAssignCreate(@Path("id") id: String?, @Body data: AsssignTask?): Observable<AsssignTask>

    @POST("api/event/")
    fun apiEventCreate(@Body data: PostEvent?): Observable<GetEventListHome>

    @GET("api/event/created/")
    fun apiEventCreatedList(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/): Observable<InlineResponse2002>

                                                        //TODO DATE API's

    @POST("api/event/{id}/date/")
    fun apiEventDateCreate(@Path("id") id: String?, @Body data: EventDate?): Call<EventDate>

    @DELETE("api/event/{event_pk}/date/{date_pk}/")
    fun apiEventDateDelete(@Path("date_pk") datePk: String?, @Path("event_pk") eventPk: String?): Observable<String>

    @PATCH("api/event/{event_pk}/date/{date_pk}/")
    fun apiEventDatePartialUpdate(@Path("date_pk") datePk: String?, @Path("event_pk") eventPk: String?, @Body data: PostDates?): Observable<EventDate>

    @GET("api/event/{event_pk}/date/{date_pk}/")
    fun apiEventDateRead(@Path("date_pk") datePk: String?, @Path("event_pk") eventPk: String?): Call<EventDate>

    @PUT("api/event/{event_pk}/date/{date_pk}/")
    fun apiEventDateUpdate(@Path("date_pk") datePk: String?, @Path("event_pk") eventPk: String?, @Body data: EventDate?): Call<EventDate>

                                                    //TODO TASK API's

    @POST("api/event/{id}/task/")
    fun apiEventTaskCreate(@Path("id") id: String?, @Body data: TaskPost?): Observable<Task>

    @DELETE("api/event/{event_pk}/task/{task_pk}/")
    fun apiEventTaskDelete(@Path("event_pk") eventPk: String?, @Path("task_pk") taskPk: String?): Observable<String>

    @PATCH("api/event/{event_pk}/task/{task_pk}/")
    fun apiEventTaskPartialUpdate(@Path("event_pk") eventPk: String?, @Path("task_pk") taskPk: String?, @Body data: Task?): Call<Task>

    @GET("api/event/{event_pk}/task/{task_pk}/")
    fun apiEventTaskRead(@Path("event_pk") eventPk: String?, @Path("task_pk") taskPk: String?): Call<Task>

    @PUT("api/event/{event_pk}/task/{task_pk}/")
    fun apiEventTaskUpdate(@Path("event_pk") eventPk: String?, @Path("task_pk") taskPk: String?, @Body data: Task?): Call<Task>

    @PATCH("api/event/{event_pk}/task/{task_pk}/")
    fun apiEventTaskPatch(@Path("event_pk") eventPk: String?, @Path("task_pk") taskPk: String?, @Body data: TaskPatch?): Observable<TaskResponse>

    @PATCH("api/event/{event_pk}/task/{task_pk}/")
    fun apiEventFullTaskPatch(@Path("event_pk") eventPk: String?, @Path("task_pk") taskPk: String?, @Body data: TaskPost?): Observable<Task>

    @PATCH("api/event/{event_pk}/task/{task_pk}/")
    fun apiEventTaskPatchWithoutAssignee(@Path("event_pk") eventPk: String?, @Path("task_pk") taskPk: String?, @Body data: TaskPostWithoutAssignee?): Observable<Task>

    @DELETE("api/event/{id}/")
    fun apiEventDelete(@Path("id") id: String?): Observable<String>

    @DELETE("api/event/{id}/invitation/{invitation_pk}/")
    fun apiEventInvitationDelete(@Path("id") id: String?, @Path("invitation_pk") invitationPk: String?): Call<Void>

    @PATCH("api/event/{id}/invitation/{invitation_pk}/")
    fun apiEventInvitationPartialUpdate(@Path("id") id: String?, @Path("invitation_pk") invitationPk: String?, @Body data: Invitation?): Call<Invitation>

    @PUT("api/event/{id}/invitation/{invitation_pk}/")
    fun apiEventInvitationUpdate(@Path("id") id: String?, @Path("invitation_pk") invitationPk: String?, @Body data: GuestAmount?): Observable<Invitation>

    @GET("api/event/")
    fun apiEventList(/*@Query("limit") limit: Int?, @Query("offset") offset: Int?*/): Observable<InlineResponse2002>

    @PATCH("api/event/{id}/")
    fun apiEventPartialUpdate(@Path("id") id: String?, @Body data: PostEvent?): Observable<Event>

    @PUT("api/event/{id}/")
    fun apiEventUpdate(@Path("id") id: String?, @Body data: PostEvent?): Observable<Event>

    @GET("api/event/pending/")
    fun apiEventPendingList(@Query("limit") limit: Int?, @Query("offset") offset: Int?): Call<InlineResponse2002>

    @POST("api/event/{id}/place/")
    fun apiEventPlaceCreate(@Path("id") id: String?, @Body data: EventPlace?): Call<EventPlace>

    @DELETE("api/event/{event_pk}/place/{place_pk}/")
    fun apiEventPlaceDelete(@Path("event_pk") eventPk: String?, @Path("place_pk") placePk: String?): Observable<String>

    @PATCH("api/event/{event_pk}/place/{place_pk}/")
    fun apiEventPlacePartialUpdate(@Path("event_pk") eventPk: String?, @Path("place_pk") placePk: String?, @Body data: EventPlace?): Call<EventPlace>

    @GET("api/event/{event_pk}/place/{place_pk}/")
    fun apiEventPlaceRead(@Path("event_pk") eventPk: String?, @Path("place_pk") placePk: String?): Call<EventPlace>

    @PUT("api/event/{event_pk}/place/{place_pk}/")
    fun apiEventPlaceUpdate(@Path("event_pk") eventPk: String?, @Path("place_pk") placePk: String?, @Body data: EventPlace?): Call<EventPlace>

    @GET("api/event/{id}/")
    fun apiEventRead(@Path("id") id: String?): Call<Event>









    @PATCH("api/event/{event_pk}/activity/{activity_pk}/")
    fun apiEventActivityPartialUpdate(@Path("activity_pk") activityPk: String?, @Path("event_pk") eventPk: String?, @Body data: PostActivities?): Observable<Activity>

    @PUT("api/event/{event_pk}/activity/{activity_pk}/")
    fun apiEventActivityUpdate(@Path("activity_pk") activityPk: String?, @Path("event_pk") eventPk: String?, @Body data: PostActivities?): Observable<Activity>

    @POST("api/event/{id}/activity/")
    fun apiEventActivityCreate(@Path("id") id: String?, @Body data: PostActivities?): Observable<Activity>

    /*@POST("api/event/{id}/vote_date/")
    fun apiEventVoteDateCreate(@Path("id") id: String?, @Body data: VoteDate?): Observable<VoteDate>*/

   /* @POST("api/event/{id}/vote_place/")
    fun apiEventVotePlaceCreate(@Path("id") id: String?, @Body data: VotePlace?): Observable<VotePlace>*/

    @POST("api/event/{event_pk}/date/{date_pk}/vote/")
    fun apiEventVoteDateCreate( @Path("event_pk") eventPk: String?, @Path("date_pk") datePk: String?/*, @Body data: VoteDate?*/): Observable<Event>

    @POST("api/event/{event_pk}/place/{place_pk}/vote/")
    fun apiEventVotePlaceCreate( @Path("event_pk") eventPk: String?, @Path("place_pk") placePk: String?/*, @Body data: VoteDate?*/): Observable<Event>

    @DELETE("event/{id}/")
    fun eventDelete(@Path("id") id: String?): Call<Void>

    @PATCH("event/{id}/")
    fun eventPartialUpdate(@Path("id") id: String?, @Body data: Event?): Call<Event>

    @GET("event/{id}/")
    fun eventRead(@Path("id") id: String?): Observable<Event>

    @POST("api/event/{id}/comments/")
    fun postComment(@Path("id") id: String?, @Body data: PostComments?): Observable<Comment>

    @PUT("api/event/{id}/comments/{comment_id}/")
    fun editComment(@Path("id") id: String?, @Path("comment_id") commentId: String?, @Body data: PostComments?): Observable<Comment>

    @DELETE("api/event/{id}/comments/{comment_id}/")
    fun deleteComment(@Path("id") id: String?, @Path("comment_id") commentId: String?): Observable<String>

    @PUT("event/{id}/")
    fun eventUpdate(@Path("id") id: String?, @Body data: Event?): Call<Event>
}