package com.batanks.newplan.signing

import com.batanks.newplan.swagger.api.AuthenticationAPI
import com.batanks.newplan.swagger.model.Login

class Repository(val auth: AuthenticationAPI?) {

    fun executeLogin(login: Login) = auth?.apiAuthenticationLoginCreateSingle(login)
}