package com.batanks.nextplan.swagger.model

data class Guests (

        val invitation_id : Int?,
        val status : String?,
        val user : GuestUser,
        /*val username : String?,
        val user_id : Int?,*/
        val is_current_user : Boolean?,
        val price : String?,
        val price_currency : String?,
        val email : String?,
        val phone_number : String?,
        val people_coming : Int?,
        var selection : Boolean = false

        /*val invitation_id : Int,
        val status : String,
        val user : GuestUser,
        val is_current_user : Boolean,
        val price : Double,
        val price_currency : String,
        val email : String,
        val phone_number : String,
        val people_coming : Int*/) {

        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as Guests

                if (user.id != other.user.id) return false

                return true
        }

        override fun hashCode(): Int {
                return user.id ?: 0
        }
}