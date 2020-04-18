package com.batanks.nextplan.network.cookie

import okhttp3.Cookie
import okhttp3.HttpUrl

class CookieJarImplementation(private val cookieStore: CookieStore) : PersistableCookieJar {

    private val savedCookies = HashMap<String, Cookie>()

    init {
        cookieStore.getAll().forEach {
            savedCookies[it.domain() + it.name()] = it
        }
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return savedCookies.values.toMutableList()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookies.forEach {
            savedCookies[it.domain() + it.name()] = it
        }
    }

    override fun clear() {
        savedCookies.clear()
        persist()
    }

    override fun persist() {
        cookieStore.persist(savedCookies.values.toList())
    }
}