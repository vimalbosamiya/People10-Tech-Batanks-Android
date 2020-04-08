
# GCMDevice

## Properties
Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **Integer** |  |  [optional]
**name** | **String** |  |  [optional]
**registrationId** | **String** |  | 
**deviceId** | **Integer** | ANDROID_ID / TelephonyManager.getDeviceId() (e.g: 0x01) |  [optional]
**active** | **Boolean** | Inactive devices will not be sent notifications |  [optional]
**dateCreated** | [**DateTime**](DateTime.md) |  |  [optional]
**cloudMessageType** | [**CloudMessageTypeEnum**](#CloudMessageTypeEnum) | You should choose FCM or GCM |  [optional]


<a name="CloudMessageTypeEnum"></a>
## Enum: CloudMessageTypeEnum
Name | Value
---- | -----
FCM | &quot;FCM&quot;
GCM | &quot;GCM&quot;



