package com.batanks.nextplan.swagger.api

import com.batanks.nextplan.swagger.model.*
import com.batanks.nextplan.swagger.model.mode.ResetPasswordConfirm
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface AuthenticationAPI {

    @POST("api/authentication/android/")
    fun apiAuthenticationAndroidCreate(@Body device: GCMDevice): Call<GCMDevice>

    @POST("api/authentication/forgot/")
    fun apiAuthenticationForgotCreate(@Body password: PasswordLost): Observable<PasswordLost>

    @POST("api/authentication/ios/")
    fun apiAuthenticationIosCreate(@Body device: APNSDevice): Call<APNSDevice>

    @GET("api/authentication/")
    fun apiAuthenticationList(): Observable<Void>

    @POST("api/authentication/login/")
    fun apiAuthenticationLoginCreate(@Body login: Login): Call<Login>

    @POST("api/authentication/login/")
    fun apiAuthenticationLoginCreateSingle(@Body login: Login): Observable<User>

    @POST("api/authentication/logout/")
    fun apiAuthenticationLogoutCreate(): Call<Void>

    @POST("api/authentication/password/")
    fun apiAuthenticationPasswordCreate(@Body password: ChangePassword): Call<ChangePassword>

    @PATCH("api/authentication/profile/")
    fun apiAuthenticationProfilePartialUpdate(@Body user: User): Call<User>

    @GET("api/authentication/profile/")
    fun apiAuthenticationProfileRead(): Call<User>

    @GET("api/authentication/profile/")
    fun apiAuthenticationProfileReadObservable(): Observable<User>

    @Multipart
    @PUT("api/authentication/profile/")
    fun apiAuthenticationProfileUpdate(@Part ("email") email : RequestBody, @Part ("first_name") first_name : RequestBody,
                                                               @Part ("last_name") last_name : RequestBody,
                                                               @Part ("phone_number") phone_number : RequestBody,
                                                               @Part  picture : MultipartBody.Part?): Observable<UpdatedUser>

    @POST("api/authentication/register/")
    fun apiAuthenticationRegisterCreate(@Body user: RegisterUser): Call<RegisterUser>

    @POST("api/authentication/register/")
    fun apiAuthenticationRegisterCreateObservable(@Body user: RegisterUser): Observable<User>

    @POST("api/authentication/reset/")
    fun apiAuthenticationResetPassword(@Body resetPassword: ResetPasswordConfirm): Observable<ResetPassword>

    @GET("api/authentication/settings")
    fun apiAuthenticationGetSetings(): Observable<SettingsGet>

    @PUT("api/authentication/settings")
    fun apiAuthenticationPutSetings(@Body data: SettingsGet): Observable<SettingsGet>

    @PATCH("api/authentication/settings")
    fun apiAuthenticationPatchSetings(@Body data: SettingsGet): Observable<SettingsGet>
}