package com.batanks.nextplan.network.cookie

import okhttp3.CookieJar

interface PersistableCookieJar : CookieJar {
  fun persist()
  fun clear()
}