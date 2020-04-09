package com.batanks.newplan.network

import android.content.Context
import com.batanks.newplan.network.cookie.CookieJarImplementation
import com.batanks.newplan.network.cookie.JsonFileCookieStore
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient constructor() {

    companion object {

        private const val BASE_URL = "http://93.90.204.56/"
        private var retrofit: Retrofit? = null
        var cookieJar: CookieJarImplementation? = null

        fun getRetrofitInstance(context: Context): Retrofit? {

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
        }
    }
}