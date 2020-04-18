package com.batanks.nextplan.network.cookie

import okhttp3.Cookie

interface CookieStore {
  fun persist(cookies: List<Cookie>): Boolean
  fun getAll(): List<Cookie>
}