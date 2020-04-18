package com.batanks.nextplan.swagger.model

data class GCMDevice(
        val id: Int,
        val name: String,
        val registration_id: String,
        val device_id: Int,
        val active: Boolean,
        val date_created: String,
        val cloud_message_type: CloudMessageTypeEnum)

enum class CloudMessageTypeEnum {
    FCM, GCM;
}