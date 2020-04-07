package com.batanks.newplan.network.cookie

import okhttp3.CookieJar

interface PersistableCookieJar : CookieJar {
  fun persist()
  fun clear()
}