package com.batanks.newplan.network

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie
import okio.buffer
import okio.sink
import okio.source

class JsonFileCookieStore constructor(private val context: Context) : CookieStore {

    override fun persist(cookies: List<Cookie>): Boolean {
        return try {
            context.openFileOutput(COOKIE_FILE_NAME, Context.MODE_PRIVATE)
                    .sink()
                    .buffer()
                    .use { Gson().toJson(cookies) }
            true
        } catch (error: Throwable) {
            false
        }
    }

    override fun getAll(): List<Cookie> {
        return try {
            context.openFileInput(COOKIE_FILE_NAME)
                    .source()
                    .buffer()
                    .use { requireNotNull(Gson().fromJson(it.toString(), object : TypeToken<List<Cookie?>?>() {}.type)) }
        } catch (error: Throwable) {
            emptyList()
        }
    }

    companion object {
        const val COOKIE_FILE_NAME = "cookies.json"
    }
}