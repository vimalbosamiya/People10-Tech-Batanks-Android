package com.batanks.newplan.network

import okhttp3.CookieJar

interface PersistableCookieJar : CookieJar {
  fun persist()
  fun clear()
}