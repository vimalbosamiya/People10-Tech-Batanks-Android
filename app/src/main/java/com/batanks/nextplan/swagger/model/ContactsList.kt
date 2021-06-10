package com.batanks.nextplan.swagger.model

import android.net.Uri
import retrofit2.http.Url

data class ContactsList(
                        val id : Int?,
                        val first_name : String?,
                        val last_name : String?,
                        val username : String?,
                        val email : String?,
                        val phone_number : String?,
                        val picture: String?,
                        var selection : Boolean = false){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContactsList

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id ?: 0
    }
}