package com.batanks.nextplan.swagger.model

data class AddGroupContact(
        val contact: Int,
        val name: String = DEFAULT) {
    companion object {
        const val DEFAULT = "default"
    }
}