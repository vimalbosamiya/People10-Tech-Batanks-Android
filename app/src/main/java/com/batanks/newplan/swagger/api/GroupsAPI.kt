package com.batanks.newplan.swagger.api

import com.batanks.newplan.swagger.model.AddGroupContact
import com.batanks.newplan.swagger.model.Contact
import com.batanks.newplan.swagger.model.Group
import retrofit2.Call
import retrofit2.http.*

interface GroupsAPI {

    @POST("api/groups/{id}/add/")
    fun apiGroupsAddCreate(@Path("id") id: String?, @Body data: Contact?): Call<Contact>

    @POST("api/groups/")
    fun apiGroupsCreate(@Body data: AddGroupContact?): Call<AddGroupContact>

    @DELETE("api/groups/{id}/")
    fun apiGroupsDelete(@Path("id") id: String?): Call<Void>

    @GET("api/groups/")
    fun apiGroupsList(): Call<List<Group>>

    @PATCH("api/groups/{id}/")
    fun apiGroupsPartialUpdate(@Path("id") id: String?, @Body data: Group?): Call<Group>

    @GET("api/groups/{id}/")
    fun apiGroupsRead(@Path("id") id: String?): Call<Group>

    @DELETE("api/groups/{id}/remove/{user_pk}/")
    fun apiGroupsRemoveDelete(@Path("id") id: String?, @Path("user_pk") userPk: String?): Call<Void>

    @PUT("api/groups/{id}/")
    fun apiGroupsUpdate(@Path("id") id: String?, @Body data: Group?): Call<Group>
}