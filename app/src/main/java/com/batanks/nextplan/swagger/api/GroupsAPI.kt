package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

interface GroupsAPI {

   /* @POST("api/groups/{id}/add/")
    fun apiGroupsAddCreate(@Path("id") id: String?, @Body data: Contact?): Call<Contact>*/

    @POST("api/groups/{id}/add/")
    fun apiGroupsAddContact(@Path("id") id: String?, @Body data: AddContactToGroup?): Observable<AddContactToGroup>

    @POST("api/groups/")
    fun apiGroupsCreate(@Body data: AddGroupContact?): Observable<CreateGroupResponse>

    @POST("api/groups/")
    fun apiGroupsCreateWithoutId(@Body data: GroupWithoutId?): Observable<CreateGroupResponse>

    @POST("api/groups/")
    fun apiAddContact(@Body data: AddContact?): Observable<AddContact>

    @DELETE("api/groups/{id}/")
    fun apiGroupsDelete(@Path("id") id: String?): Observable<String>

    @GET("api/groups/")
    fun apiGroupsList(): Observable<List<Group>>

    @PATCH("api/groups/{id}/")
    fun apiGroupsPartialUpdate(@Path("id") id: String?, @Body data: Group?): Call<Group>

    @GET("api/groups/{id}/")
    fun apiGroupsRead(@Path("id") id: String?): Observable<Group>

    @DELETE("api/groups/{id}/remove/{user_pk}/")
    fun apiGroupsRemoveDelete(@Path("id") id: String?, @Path("user_pk") userPk: String?): Call<Void>

    @PUT("api/groups/{id}/")
    fun apiGroupsUpdate(@Path("id") id: String?, @Body data: GroupEdit?): Observable<GroupEdit>
    //fun apiGroupsUpdate(@Path("id") id: String?, @Body data: Group?): Observable<Group>
}