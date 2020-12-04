package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.AddGroupContact
import com.batanks.nextplan.swagger.model.Contact
import com.batanks.nextplan.swagger.model.Group
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface GroupsAPI {

    @POST("api/groups/{id}/add/")
    fun apiGroupsAddCreate(@Path("id") id: String?, @Body data: Contact?): Call<Contact>

    @POST("api/groups/")
    fun apiGroupsCreate(@Body data: AddGroupContact?): Observable<AddGroupContact>

    @DELETE("api/groups/{id}/")
    fun apiGroupsDelete(@Path("id") id: String?): Observable<Void>

    @GET("api/groups/")
    fun apiGroupsList(): Observable<List<Group>>

    @PATCH("api/groups/{id}/")
    fun apiGroupsPartialUpdate(@Path("id") id: String?, @Body data: Group?): Call<Group>

    @GET("api/groups/{id}/")
    fun apiGroupsRead(@Path("id") id: String?): Observable<Group>

    @DELETE("api/groups/{id}/remove/{user_pk}/")
    fun apiGroupsRemoveDelete(@Path("id") id: String?, @Path("user_pk") userPk: String?): Call<Void>

    @PUT("api/groups/{id}/")
    fun apiGroupsUpdate(@Path("id") id: String?, @Body data: Group?): Observable<Group>
    //fun apiGroupsUpdate(@Path("id") id: String?, @Body data: Group?): Observable<Group>
}