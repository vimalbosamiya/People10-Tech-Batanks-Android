package com.batanks.nextplan.network

import android.app.Activity
import android.content.Context
import com.batanks.nextplan.network.cookie.CookieJarImplementation
import com.batanks.nextplan.network.cookie.JsonFileCookieStore
import com.batanks.nextplan.signing.SigninActivity
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import com.google.gson.GsonBuilder

import com.google.gson.Gson
import retrofit2.Converter


class RetrofitClient private constructor() {

    companion object {

        private const val BASE_URL = "http://93.90.204.56/"
        //var token : String? = null
        private var retrofit: Retrofit? = null
        const val USER_TOKEN_PREF = "login_token"
        //var cookieJar: CookieJarImplementation? = null

        /*fun getRetrofitInstance(context: Context): Retrofit? {

            if (cookieJar == null) {
                cookieJar = CookieJarImplementation(JsonFileCookieStore(context))
            }

            val okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .cookieJar(cookieJar)
                    .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
            return retrofit
        }*/

        fun getRetrofitInstance(context: Context): Retrofit? {

            context.getSharedPreferences(USER_TOKEN_PREF, Context.MODE_PRIVATE)

            var token : String? =  context.getSharedPreferences(USER_TOKEN_PREF, Context.MODE_PRIVATE).
                    getString("USER_LOGIN_TOKEN",null)

            /*val builder = GsonBuilder()
            builder.serializeNulls()
            val gson = builder.create()
            val gson = GsonBuilder().serializeNulls().create()
            val gson = GsonBuilder().create()
            gson.serializeNulls()*/

            val converterFactory: Converter.Factory = GsonConverterFactory.create(GsonBuilder().serializeNulls().create())

            val okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    /*.cookieJar(cookieJar)*/
                    .build()

            val okHttpClientWithToken = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(object:Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): Response {
                            val request = chain.request().newBuilder().addHeader("Authorization", "token"+" " + token).build()
                           // val request = chain.request().newBuilder().addHeader("X-Application-Platform", "ANDROID" ).build()
                           // println("Token from retrofit Shared preferences" + context.getSharedPreferences(USER_TOKEN_PREF, Context.MODE_PRIVATE).getString("USER_LOGIN_TOKEN",""))
                            return chain.proceed(request)
                        }
                    })
                    /*.cookieJar(cookieJar)*/
                    .build()

            if (retrofit == null && token == null) {

                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(/*GsonConverterFactory.create()*/converterFactory)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()

                //println("OkHttpClient without Header is being called")

            } else if (retrofit != null && token != null)  {

                    retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(okHttpClientWithToken)
                            .addConverterFactory(/*GsonConverterFactory.create()*/converterFactory)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build()

                //println("OkHttpClient with Header when we have both instance and token is being called")

            } else if (retrofit == null && token != null){

                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClientWithToken)
                        .addConverterFactory(/*GsonConverterFactory.create()*/converterFactory)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()

                //println("OkHttpClient with Header when we don't have instance but have token is being called")

            }

            /*else if (retrofit != null && token == null){


                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()

                println("OkHttpClient without Header when we have instance but don't have token is being called")
            }*/
            return retrofit
        }

        fun RetrofitInstance(context: Context): Retrofit? {

            val okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .addInterceptor(object:Interceptor {
                        @Throws(IOException::class)
                        override fun intercept(chain: Interceptor.Chain): Response {
//                            val request = chain.request().newBuilder().addHeader("Authorization", "token"+" " ).build()
                            val request = chain.request().newBuilder().addHeader("X-Application-Platform", "ANDROID" ).build()
                            //println("Token from retrofit" + token)
                            return chain.proceed(request)
                        }
                    })
                    /*.cookieJar(cookieJar)*/
                    .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
            return retrofit
        }
    }
}