package com.batanks.nextplan.network.cookie

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie

class JsonFileCookieStore constructor(private val context: Context) : CookieStore {

    override fun persist(cookies: List<Cookie>): Boolean {
        return try {
            context.openFileOutput(COOKIE_FILE_NAME, Context.MODE_PRIVATE).use { output ->
                output.write(Gson().toJson(cookies).toByteArray())
            }
            true
        } catch (error: Throwable) {
            false
        }
    }

    override fun getAll(): List<Cookie> {
        return try {
            context.openFileInput(COOKIE_FILE_NAME).use { stream ->
                Gson().fromJson(stream.bufferedReader().use {
                    it.readText()
                }, object : TypeToken<List<Cookie?>?>() {}.type)
            }
        } catch (error: Throwable) {
            emptyList()
        }
    }

    companion object {
        const val COOKIE_FILE_NAME = "cookies.json"
    }
}