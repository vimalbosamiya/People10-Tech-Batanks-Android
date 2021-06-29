# ApiApi

All URIs are relative to *http://93.90.204.56/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**apiAuthenticationAndroidCreate**](ApiApi.md#apiAuthenticationAndroidCreate) | **POST** api/authentication/android/ | 
[**apiAuthenticationForgotCreate**](ApiApi.md#apiAuthenticationForgotCreate) | **POST** api/authentication/forgot/ | 
[**apiAuthenticationIosCreate**](ApiApi.md#apiAuthenticationIosCreate) | **POST** api/authentication/ios/ | 
[**apiAuthenticationList**](ApiApi.md#apiAuthenticationList) | **GET** api/authentication/ | this is the authentication endpoint here you can
[**apiAuthenticationLoginCreate**](ApiApi.md#apiAuthenticationLoginCreate) | **POST** api/authentication/login/ | 
[**apiAuthenticationLogoutCreate**](ApiApi.md#apiAuthenticationLogoutCreate) | **POST** api/authentication/logout/ | 
[**apiAuthenticationPasswordCreate**](ApiApi.md#apiAuthenticationPasswordCreate) | **POST** api/authentication/password/ | 
[**apiAuthenticationProfilePartialUpdate**](ApiApi.md#apiAuthenticationProfilePartialUpdate) | **PATCH** api/authentication/profile/ | 
[**apiAuthenticationProfileRead**](ApiApi.md#apiAuthenticationProfileRead) | **GET** api/authentication/profile/ | 
[**apiAuthenticationProfileUpdate**](ApiApi.md#apiAuthenticationProfileUpdate) | **PUT** api/authentication/profile/ | 
[**apiAuthenticationRegisterCreate**](ApiApi.md#apiAuthenticationRegisterCreate) | **POST** api/authentication/register/ | 
[**apiCategoryList**](ApiApi.md#apiCategoryList) | **GET** api/category/ | 
[**apiContactsDelete**](ApiApi.md#apiContactsDelete) | **DELETE** api/contacts/{id}/ | 
[**apiContactsList**](ApiApi.md#apiContactsList) | **GET** api/contacts/ | 
[**apiEventAcceptedList**](ApiApi.md#apiEventAcceptedList) | **GET** api/event/accepted/ | 
[**apiEventActivityCreate**](ApiApi.md#apiEventActivityCreate) | **POST** api/event/{id}/activity/ | 
[**apiEventActivityDelete**](ApiApi.md#apiEventActivityDelete) | **DELETE** api/event/{event_pk}/activity/{activity_pk}/ | 
[**apiEventActivityPartialUpdate**](ApiApi.md#apiEventActivityPartialUpdate) | **PATCH** api/event/{event_pk}/activity/{activity_pk}/ | 
[**apiEventActivityRead**](ApiApi.md#apiEventActivityRead) | **GET** api/event/{event_pk}/activity/{activity_pk}/ | 
[**apiEventActivitySubscribeCreate**](ApiApi.md#apiEventActivitySubscribeCreate) | **POST** api/event/{event_pk}/activity/{activity_pk}/subscribe/ | 
[**apiEventActivityUpdate**](ApiApi.md#apiEventActivityUpdate) | **PUT** api/event/{event_pk}/activity/{activity_pk}/ | 
[**apiEventAnswerPartialUpdate**](ApiApi.md#apiEventAnswerPartialUpdate) | **PATCH** api/event/{id}/answer/ | 
[**apiEventAnswerRead**](ApiApi.md#apiEventAnswerRead) | **GET** api/event/{id}/answer/ | 
[**apiEventAnswerUpdate**](ApiApi.md#apiEventAnswerUpdate) | **PUT** api/event/{id}/answer/ | 
[**apiEventAssignCreate**](ApiApi.md#apiEventAssignCreate) | **POST** api/event/{id}/assign/ | 
[**apiEventCreate**](ApiApi.md#apiEventCreate) | **POST** api/event/ | Endpoint to list events or create a new one
[**apiEventCreatedList**](ApiApi.md#apiEventCreatedList) | **GET** api/event/created/ | 
[**apiEventDateCreate**](ApiApi.md#apiEventDateCreate) | **POST** api/event/{id}/date/ | 
[**apiEventDateDelete**](ApiApi.md#apiEventDateDelete) | **DELETE** api/event/{event_pk}/date/{date_pk}/ | 
[**apiEventDatePartialUpdate**](ApiApi.md#apiEventDatePartialUpdate) | **PATCH** api/event/{event_pk}/date/{date_pk}/ | 
[**apiEventDateRead**](ApiApi.md#apiEventDateRead) | **GET** api/event/{event_pk}/date/{date_pk}/ | 
[**apiEventDateUpdate**](ApiApi.md#apiEventDateUpdate) | **PUT** api/event/{event_pk}/date/{date_pk}/ | 
[**apiEventDelete**](ApiApi.md#apiEventDelete) | **DELETE** api/event/{id}/ | 
[**apiEventInvitationDelete**](ApiApi.md#apiEventInvitationDelete) | **DELETE** api/event/{id}/invitation/{invitation_pk}/ | 
[**apiEventInvitationPartialUpdate**](ApiApi.md#apiEventInvitationPartialUpdate) | **PATCH** api/event/{id}/invitation/{invitation_pk}/ | 
[**apiEventInvitationUpdate**](ApiApi.md#apiEventInvitationUpdate) | **PUT** api/event/{id}/invitation/{invitation_pk}/ | 
[**apiEventList**](ApiApi.md#apiEventList) | **GET** api/event/ | Endpoint to list events or create a new one
[**apiEventPartialUpdate**](ApiApi.md#apiEventPartialUpdate) | **PATCH** api/event/{id}/ | 
[**apiEventPendingList**](ApiApi.md#apiEventPendingList) | **GET** api/event/pending/ | 
[**apiEventPlaceCreate**](ApiApi.md#apiEventPlaceCreate) | **POST** api/event/{id}/place/ | 
[**apiEventPlaceDelete**](ApiApi.md#apiEventPlaceDelete) | **DELETE** api/event/{event_pk}/place/{place_pk}/ | 
[**apiEventPlacePartialUpdate**](ApiApi.md#apiEventPlacePartialUpdate) | **PATCH** api/event/{event_pk}/place/{place_pk}/ | 
[**apiEventPlaceRead**](ApiApi.md#apiEventPlaceRead) | **GET** api/event/{event_pk}/place/{place_pk}/ | 
[**apiEventPlaceUpdate**](ApiApi.md#apiEventPlaceUpdate) | **PUT** api/event/{event_pk}/place/{place_pk}/ | 
[**apiEventRead**](ApiApi.md#apiEventRead) | **GET** api/event/{id}/ | 
[**apiEventTaskCreate**](ApiApi.md#apiEventTaskCreate) | **POST** api/event/{id}/task/ | 
[**apiEventTaskDelete**](ApiApi.md#apiEventTaskDelete) | **DELETE** api/event/{event_pk}/task/{task_pk}/ | 
[**apiEventTaskPartialUpdate**](ApiApi.md#apiEventTaskPartialUpdate) | **PATCH** api/event/{event_pk}/task/{task_pk}/ | 
[**apiEventTaskRead**](ApiApi.md#apiEventTaskRead) | **GET** api/event/{event_pk}/task/{task_pk}/ | 
[**apiEventTaskUpdate**](ApiApi.md#apiEventTaskUpdate) | **PUT** api/event/{event_pk}/task/{task_pk}/ | 
[**apiEventUpdate**](ApiApi.md#apiEventUpdate) | **PUT** api/event/{id}/ | 
[**apiEventVoteDateCreate**](ApiApi.md#apiEventVoteDateCreate) | **POST** api/event/{id}/vote_date/ | 
[**apiEventVotePlaceCreate**](ApiApi.md#apiEventVotePlaceCreate) | **POST** api/event/{id}/vote_place/ | 
[**apiGroupsAddCreate**](ApiApi.md#apiGroupsAddCreate) | **POST** api/groups/{id}/add/ | 
[**apiGroupsCreate**](ApiApi.md#apiGroupsCreate) | **POST** api/groups/ | 
[**apiGroupsDelete**](ApiApi.md#apiGroupsDelete) | **DELETE** api/groups/{id}/ | 
[**apiGroupsList**](ApiApi.md#apiGroupsList) | **GET** api/groups/ | 
[**apiGroupsPartialUpdate**](ApiApi.md#apiGroupsPartialUpdate) | **PATCH** api/groups/{id}/ | 
[**apiGroupsRead**](ApiApi.md#apiGroupsRead) | **GET** api/groups/{id}/ | 
[**apiGroupsRemoveDelete**](ApiApi.md#apiGroupsRemoveDelete) | **DELETE** api/groups/{id}/remove/{user_pk}/ | 
[**apiGroupsUpdate**](ApiApi.md#apiGroupsUpdate) | **PUT** api/groups/{id}/ | 
[**apiList**](ApiApi.md#apiList) | **GET** api/ | This the main endpoint here there is not much to do here.
[**apiNotificationsList**](ApiApi.md#apiNotificationsList) | **GET** api/notifications/ | Endpoint to list events or create a new one
[**apiNotificationsPartialUpdate**](ApiApi.md#apiNotificationsPartialUpdate) | **PATCH** api/notifications/{id}/ | 
[**apiNotificationsUpdate**](ApiApi.md#apiNotificationsUpdate) | **PUT** api/notifications/{id}/ | 
[**apiSearchList**](ApiApi.md#apiSearchList) | **GET** api/search/ | 
[**apiSearchesCreate**](ApiApi.md#apiSearchesCreate) | **POST** api/searches/ | Endpoint to list events or create a new one
[**apiSearchesDelete**](ApiApi.md#apiSearchesDelete) | **DELETE** api/searches/{id}/ | 
[**apiSearchesList**](ApiApi.md#apiSearchesList) | **GET** api/searches/ | Endpoint to list events or create a new one
[**apiSearchesPartialUpdate**](ApiApi.md#apiSearchesPartialUpdate) | **PATCH** api/searches/{id}/ | 
[**apiSearchesRead**](ApiApi.md#apiSearchesRead) | **GET** api/searches/{id}/ | 
[**apiSearchesUpdate**](ApiApi.md#apiSearchesUpdate) | **PUT** api/searches/{id}/ | 


<a name="apiAuthenticationAndroidCreate"></a>
# **apiAuthenticationAndroidCreate**
> GCMDevice apiAuthenticationAndroidCreate(data)





### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
GCMDevice data = new GCMDevice(); // GCMDevice | 
try {
    GCMDevice result = apiInstance.apiAuthenticationAndroidCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationAndroidCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**GCMDevice**](GCMDevice.md)|  |

### Return type

[**GCMDevice**](GCMDevice.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationForgotCreate"></a>
# **apiAuthenticationForgotCreate**
> PasswordLost apiAuthenticationForgotCreate(data)



Forgot password view

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
PasswordLost data = new PasswordLost(); // PasswordLost | 
try {
    PasswordLost result = apiInstance.apiAuthenticationForgotCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationForgotCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**PasswordLost**](PasswordLost.md)|  |

### Return type

[**PasswordLost**](PasswordLost.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationIosCreate"></a>
# **apiAuthenticationIosCreate**
> APNSDevice apiAuthenticationIosCreate(data)





### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
APNSDevice data = new APNSDevice(); // APNSDevice | 
try {
    APNSDevice result = apiInstance.apiAuthenticationIosCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationIosCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**APNSDevice**](APNSDevice.md)|  |

### Return type

[**APNSDevice**](APNSDevice.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationList"></a>
# **apiAuthenticationList**
> Void apiAuthenticationList()

this is the authentication endpoint here you can

- login as a user - logout if your logged in - ask to reset password for a specified user - reset password - update your password if your logged in - update your profile info - validate user token  ---

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
try {
    Void result = apiInstance.apiAuthenticationList();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationList");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationLoginCreate"></a>
# **apiAuthenticationLoginCreate**
> Login apiAuthenticationLoginCreate(data)



log user and send them the user&#39;s info + token

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Login data = new Login(); // Login | 
try {
    Login result = apiInstance.apiAuthenticationLoginCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationLoginCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**Login**](Login.md)|  |

### Return type

[**Login**](Login.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationLogoutCreate"></a>
# **apiAuthenticationLogoutCreate**
> Void apiAuthenticationLogoutCreate()



logout the user

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
try {
    Void result = apiInstance.apiAuthenticationLogoutCreate();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationLogoutCreate");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationPasswordCreate"></a>
# **apiAuthenticationPasswordCreate**
> ChangePassword apiAuthenticationPasswordCreate(data)



Change password view

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
ChangePassword data = new ChangePassword(); // ChangePassword | 
try {
    ChangePassword result = apiInstance.apiAuthenticationPasswordCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationPasswordCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**ChangePassword**](ChangePassword.md)|  |

### Return type

[**ChangePassword**](ChangePassword.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationProfilePartialUpdate"></a>
# **apiAuthenticationProfilePartialUpdate**
> User apiAuthenticationProfilePartialUpdate(data)



User profile interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
User data = new User(); // User | 
try {
    User result = apiInstance.apiAuthenticationProfilePartialUpdate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationProfilePartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**User**](User.md)|  |

### Return type

[**User**](User.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationProfileRead"></a>
# **apiAuthenticationProfileRead**
> User apiAuthenticationProfileRead()



User profile interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
try {
    User result = apiInstance.apiAuthenticationProfileRead();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationProfileRead");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**User**](User.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationProfileUpdate"></a>
# **apiAuthenticationProfileUpdate**
> User apiAuthenticationProfileUpdate(data)



User profile interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
User data = new User(); // User | 
try {
    User result = apiInstance.apiAuthenticationProfileUpdate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationProfileUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**User**](User.md)|  |

### Return type

[**User**](User.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiAuthenticationRegisterCreate"></a>
# **apiAuthenticationRegisterCreate**
> RegisterUser apiAuthenticationRegisterCreate(data)



register a new user this will send him an email with it&#39;s change password token

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
RegisterUser data = new RegisterUser(); // RegisterUser | 
try {
    RegisterUser result = apiInstance.apiAuthenticationRegisterCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiAuthenticationRegisterCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**RegisterUser**](RegisterUser.md)|  |

### Return type

[**RegisterUser**](RegisterUser.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiCategoryList"></a>
# **apiCategoryList**
> InlineResponse200 apiCategoryList(limit, offset)



List categories

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse200 result = apiInstance.apiCategoryList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiCategoryList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse200**](InlineResponse200.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiContactsDelete"></a>
# **apiContactsDelete**
> Void apiContactsDelete(id)



View to remove a user from all groups

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
try {
    Void result = apiInstance.apiContactsDelete(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiContactsDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiContactsList"></a>
# **apiContactsList**
> InlineResponse2001 apiContactsList(limit, offset)



View to get all contacts

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2001 result = apiInstance.apiContactsList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiContactsList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2001**](InlineResponse2001.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventAcceptedList"></a>
# **apiEventAcceptedList**
> InlineResponse2002 apiEventAcceptedList(limit, offset)



View to list events created by user

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2002 result = apiInstance.apiEventAcceptedList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventAcceptedList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventActivityCreate"></a>
# **apiEventActivityCreate**
> Activity apiEventActivityCreate(id, data)



View to add an activity

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Activity data = new Activity(); // Activity | 
try {
    Activity result = apiInstance.apiEventActivityCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventActivityCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Activity**](Activity.md)|  |

### Return type

[**Activity**](Activity.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventActivityDelete"></a>
# **apiEventActivityDelete**
> Void apiEventActivityDelete(activityPk, eventPk)



Activity view

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String activityPk = "activityPk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
try {
    Void result = apiInstance.apiEventActivityDelete(activityPk, eventPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventActivityDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **activityPk** | **String**|  |
 **eventPk** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventActivityPartialUpdate"></a>
# **apiEventActivityPartialUpdate**
> Activity apiEventActivityPartialUpdate(activityPk, eventPk, data)



Activity view

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String activityPk = "activityPk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
Activity data = new Activity(); // Activity | 
try {
    Activity result = apiInstance.apiEventActivityPartialUpdate(activityPk, eventPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventActivityPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **activityPk** | **String**|  |
 **eventPk** | **String**|  |
 **data** | [**Activity**](Activity.md)|  |

### Return type

[**Activity**](Activity.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventActivityRead"></a>
# **apiEventActivityRead**
> Activity apiEventActivityRead(activityPk, eventPk)



Activity view

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String activityPk = "activityPk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
try {
    Activity result = apiInstance.apiEventActivityRead(activityPk, eventPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventActivityRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **activityPk** | **String**|  |
 **eventPk** | **String**|  |

### Return type

[**Activity**](Activity.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventActivitySubscribeCreate"></a>
# **apiEventActivitySubscribeCreate**
> ActivitySubscribe apiEventActivitySubscribeCreate(activityPk, eventPk, data)



View to subscribe to an activity

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String activityPk = "activityPk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
ActivitySubscribe data = new ActivitySubscribe(); // ActivitySubscribe | 
try {
    ActivitySubscribe result = apiInstance.apiEventActivitySubscribeCreate(activityPk, eventPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventActivitySubscribeCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **activityPk** | **String**|  |
 **eventPk** | **String**|  |
 **data** | [**ActivitySubscribe**](ActivitySubscribe.md)|  |

### Return type

[**ActivitySubscribe**](ActivitySubscribe.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventActivityUpdate"></a>
# **apiEventActivityUpdate**
> Activity apiEventActivityUpdate(activityPk, eventPk, data)



Activity view

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String activityPk = "activityPk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
Activity data = new Activity(); // Activity | 
try {
    Activity result = apiInstance.apiEventActivityUpdate(activityPk, eventPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventActivityUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **activityPk** | **String**|  |
 **eventPk** | **String**|  |
 **data** | [**Activity**](Activity.md)|  |

### Return type

[**Activity**](Activity.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventAnswerPartialUpdate"></a>
# **apiEventAnswerPartialUpdate**
> Invitation apiEventAnswerPartialUpdate(id, data)



View to respond to an invitation

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Invitation data = new Invitation(); // Invitation | 
try {
    Invitation result = apiInstance.apiEventAnswerPartialUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventAnswerPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Invitation**](Invitation.md)|  |

### Return type

[**Invitation**](Invitation.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventAnswerRead"></a>
# **apiEventAnswerRead**
> Invitation apiEventAnswerRead(id)



View to respond to an invitation

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
try {
    Invitation result = apiInstance.apiEventAnswerRead(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventAnswerRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Invitation**](Invitation.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventAnswerUpdate"></a>
# **apiEventAnswerUpdate**
> Invitation apiEventAnswerUpdate(id, data)



View to respond to an invitation

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Invitation data = new Invitation(); // Invitation | 
try {
    Invitation result = apiInstance.apiEventAnswerUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventAnswerUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Invitation**](Invitation.md)|  |

### Return type

[**Invitation**](Invitation.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventAssignCreate"></a>
# **apiEventAssignCreate**
> AsssignTask apiEventAssignCreate(id, data)



View to assign a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
AsssignTask data = new AsssignTask(); // AsssignTask | 
try {
    AsssignTask result = apiInstance.apiEventAssignCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventAssignCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**AsssignTask**](AsssignTask.md)|  |

### Return type

[**AsssignTask**](AsssignTask.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventCreate"></a>
# **apiEventCreate**
> Event apiEventCreate(data)

Endpoint to list events or create a new one

To have details on a specific event use: /event/[pk]/

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Event data = new Event(); // Event | 
try {
    Event result = apiInstance.apiEventCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**Event**](Event.md)|  |

### Return type

[**Event**](Event.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventCreatedList"></a>
# **apiEventCreatedList**
> InlineResponse2002 apiEventCreatedList(limit, offset)



View to list events created by user

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2002 result = apiInstance.apiEventCreatedList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventCreatedList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventDateCreate"></a>
# **apiEventDateCreate**
> EventDate apiEventDateCreate(id, data)



View to add a task to an event

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
EventDate data = new EventDate(); // EventDate | 
try {
    EventDate result = apiInstance.apiEventDateCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventDateCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**EventDate**](EventDate.md)|  |

### Return type

[**EventDate**](EventDate.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventDateDelete"></a>
# **apiEventDateDelete**
> Void apiEventDateDelete(datePk, eventPk)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String datePk = "datePk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
try {
    Void result = apiInstance.apiEventDateDelete(datePk, eventPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventDateDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datePk** | **String**|  |
 **eventPk** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventDatePartialUpdate"></a>
# **apiEventDatePartialUpdate**
> EventDate apiEventDatePartialUpdate(datePk, eventPk, data)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String datePk = "datePk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
EventDate data = new EventDate(); // EventDate | 
try {
    EventDate result = apiInstance.apiEventDatePartialUpdate(datePk, eventPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventDatePartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datePk** | **String**|  |
 **eventPk** | **String**|  |
 **data** | [**EventDate**](EventDate.md)|  |

### Return type

[**EventDate**](EventDate.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventDateRead"></a>
# **apiEventDateRead**
> EventDate apiEventDateRead(datePk, eventPk)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String datePk = "datePk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
try {
    EventDate result = apiInstance.apiEventDateRead(datePk, eventPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventDateRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datePk** | **String**|  |
 **eventPk** | **String**|  |

### Return type

[**EventDate**](EventDate.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventDateUpdate"></a>
# **apiEventDateUpdate**
> EventDate apiEventDateUpdate(datePk, eventPk, data)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String datePk = "datePk_example"; // String | 
String eventPk = "eventPk_example"; // String | 
EventDate data = new EventDate(); // EventDate | 
try {
    EventDate result = apiInstance.apiEventDateUpdate(datePk, eventPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventDateUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **datePk** | **String**|  |
 **eventPk** | **String**|  |
 **data** | [**EventDate**](EventDate.md)|  |

### Return type

[**EventDate**](EventDate.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventDelete"></a>
# **apiEventDelete**
> Void apiEventDelete(id)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
try {
    Void result = apiInstance.apiEventDelete(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventInvitationDelete"></a>
# **apiEventInvitationDelete**
> Void apiEventInvitationDelete(id, invitationPk)



Lalala

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
String invitationPk = "invitationPk_example"; // String | 
try {
    Void result = apiInstance.apiEventInvitationDelete(id, invitationPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventInvitationDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **invitationPk** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventInvitationPartialUpdate"></a>
# **apiEventInvitationPartialUpdate**
> Invitation apiEventInvitationPartialUpdate(id, invitationPk, data)



Lalala

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
String invitationPk = "invitationPk_example"; // String | 
Invitation data = new Invitation(); // Invitation | 
try {
    Invitation result = apiInstance.apiEventInvitationPartialUpdate(id, invitationPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventInvitationPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **invitationPk** | **String**|  |
 **data** | [**Invitation**](Invitation.md)|  |

### Return type

[**Invitation**](Invitation.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventInvitationUpdate"></a>
# **apiEventInvitationUpdate**
> Invitation apiEventInvitationUpdate(id, invitationPk, data)



Lalala

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
String invitationPk = "invitationPk_example"; // String | 
Invitation data = new Invitation(); // Invitation | 
try {
    Invitation result = apiInstance.apiEventInvitationUpdate(id, invitationPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventInvitationUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **invitationPk** | **String**|  |
 **data** | [**Invitation**](Invitation.md)|  |

### Return type

[**Invitation**](Invitation.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventList"></a>
# **apiEventList**
> InlineResponse2002 apiEventList(limit, offset)

Endpoint to list events or create a new one

To have details on a specific event use: /event/[pk]/

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2002 result = apiInstance.apiEventList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventPartialUpdate"></a>
# **apiEventPartialUpdate**
> Event apiEventPartialUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Event data = new Event(); // Event | 
try {
    Event result = apiInstance.apiEventPartialUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Event**](Event.md)|  |

### Return type

[**Event**](Event.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventPendingList"></a>
# **apiEventPendingList**
> InlineResponse2002 apiEventPendingList(limit, offset)



View to list events created by user

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2002 result = apiInstance.apiEventPendingList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventPendingList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventPlaceCreate"></a>
# **apiEventPlaceCreate**
> EventPlace apiEventPlaceCreate(id, data)



View to add a task to an event

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
EventPlace data = new EventPlace(); // EventPlace | 
try {
    EventPlace result = apiInstance.apiEventPlaceCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventPlaceCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**EventPlace**](EventPlace.md)|  |

### Return type

[**EventPlace**](EventPlace.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventPlaceDelete"></a>
# **apiEventPlaceDelete**
> Void apiEventPlaceDelete(eventPk, placePk)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String placePk = "placePk_example"; // String | 
try {
    Void result = apiInstance.apiEventPlaceDelete(eventPk, placePk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventPlaceDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **placePk** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventPlacePartialUpdate"></a>
# **apiEventPlacePartialUpdate**
> EventPlace apiEventPlacePartialUpdate(eventPk, placePk, data)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String placePk = "placePk_example"; // String | 
EventPlace data = new EventPlace(); // EventPlace | 
try {
    EventPlace result = apiInstance.apiEventPlacePartialUpdate(eventPk, placePk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventPlacePartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **placePk** | **String**|  |
 **data** | [**EventPlace**](EventPlace.md)|  |

### Return type

[**EventPlace**](EventPlace.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventPlaceRead"></a>
# **apiEventPlaceRead**
> EventPlace apiEventPlaceRead(eventPk, placePk)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String placePk = "placePk_example"; // String | 
try {
    EventPlace result = apiInstance.apiEventPlaceRead(eventPk, placePk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventPlaceRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **placePk** | **String**|  |

### Return type

[**EventPlace**](EventPlace.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventPlaceUpdate"></a>
# **apiEventPlaceUpdate**
> EventPlace apiEventPlaceUpdate(eventPk, placePk, data)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String placePk = "placePk_example"; // String | 
EventPlace data = new EventPlace(); // EventPlace | 
try {
    EventPlace result = apiInstance.apiEventPlaceUpdate(eventPk, placePk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventPlaceUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **placePk** | **String**|  |
 **data** | [**EventPlace**](EventPlace.md)|  |

### Return type

[**EventPlace**](EventPlace.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventRead"></a>
# **apiEventRead**
> Event apiEventRead(id)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
try {
    Event result = apiInstance.apiEventRead(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Event**](Event.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventTaskCreate"></a>
# **apiEventTaskCreate**
> Task apiEventTaskCreate(id, data)



View to add a task to an event

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Task data = new Task(); // Task | 
try {
    Task result = apiInstance.apiEventTaskCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventTaskCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Task**](Task.md)|  |

### Return type

[**Task**](Task.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventTaskDelete"></a>
# **apiEventTaskDelete**
> Void apiEventTaskDelete(eventPk, taskPk)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String taskPk = "taskPk_example"; // String | 
try {
    Void result = apiInstance.apiEventTaskDelete(eventPk, taskPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventTaskDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **taskPk** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventTaskPartialUpdate"></a>
# **apiEventTaskPartialUpdate**
> Task apiEventTaskPartialUpdate(eventPk, taskPk, data)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String taskPk = "taskPk_example"; // String | 
Task data = new Task(); // Task | 
try {
    Task result = apiInstance.apiEventTaskPartialUpdate(eventPk, taskPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventTaskPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **taskPk** | **String**|  |
 **data** | [**Task**](Task.md)|  |

### Return type

[**Task**](Task.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventTaskRead"></a>
# **apiEventTaskRead**
> Task apiEventTaskRead(eventPk, taskPk)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String taskPk = "taskPk_example"; // String | 
try {
    Task result = apiInstance.apiEventTaskRead(eventPk, taskPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventTaskRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **taskPk** | **String**|  |

### Return type

[**Task**](Task.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventTaskUpdate"></a>
# **apiEventTaskUpdate**
> Task apiEventTaskUpdate(eventPk, taskPk, data)



View to edit a task

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String eventPk = "eventPk_example"; // String | 
String taskPk = "taskPk_example"; // String | 
Task data = new Task(); // Task | 
try {
    Task result = apiInstance.apiEventTaskUpdate(eventPk, taskPk, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventTaskUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **eventPk** | **String**|  |
 **taskPk** | **String**|  |
 **data** | [**Task**](Task.md)|  |

### Return type

[**Task**](Task.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventUpdate"></a>
# **apiEventUpdate**
> Event apiEventUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Event data = new Event(); // Event | 
try {
    Event result = apiInstance.apiEventUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Event**](Event.md)|  |

### Return type

[**Event**](Event.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventVoteDateCreate"></a>
# **apiEventVoteDateCreate**
> VoteDate apiEventVoteDateCreate(id, data)



View to vote for a date

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
VoteDate data = new VoteDate(); // VoteDate | 
try {
    VoteDate result = apiInstance.apiEventVoteDateCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventVoteDateCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**VoteDate**](VoteDate.md)|  |

### Return type

[**VoteDate**](VoteDate.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiEventVotePlaceCreate"></a>
# **apiEventVotePlaceCreate**
> VotePlace apiEventVotePlaceCreate(id, data)



View to vote for a place

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
VotePlace data = new VotePlace(); // VotePlace | 
try {
    VotePlace result = apiInstance.apiEventVotePlaceCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiEventVotePlaceCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**VotePlace**](VotePlace.md)|  |

### Return type

[**VotePlace**](VotePlace.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsAddCreate"></a>
# **apiGroupsAddCreate**
> Contact apiGroupsAddCreate(id, data)



View to add a contact to a list

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Contact data = new Contact(); // Contact | 
try {
    Contact result = apiInstance.apiGroupsAddCreate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsAddCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Contact**](Contact.md)|  |

### Return type

[**Contact**](Contact.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsCreate"></a>
# **apiGroupsCreate**
> AddGroupContact apiGroupsCreate(data)



Contacts serializer

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
AddGroupContact data = new AddGroupContact(); // AddGroupContact | 
try {
    AddGroupContact result = apiInstance.apiGroupsCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**AddGroupContact**](AddGroupContact.md)|  |

### Return type

[**AddGroupContact**](AddGroupContact.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsDelete"></a>
# **apiGroupsDelete**
> Void apiGroupsDelete(id)



Contact serializer

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
try {
    Void result = apiInstance.apiGroupsDelete(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsList"></a>
# **apiGroupsList**
> List&lt;Group&gt; apiGroupsList()



Contacts serializer

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
try {
    List<Group> result = apiInstance.apiGroupsList();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsList");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**List&lt;Group&gt;**](Group.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsPartialUpdate"></a>
# **apiGroupsPartialUpdate**
> Group apiGroupsPartialUpdate(id, data)



Contact serializer

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Group data = new Group(); // Group | 
try {
    Group result = apiInstance.apiGroupsPartialUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Group**](Group.md)|  |

### Return type

[**Group**](Group.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsRead"></a>
# **apiGroupsRead**
> Group apiGroupsRead(id)



Contact serializer

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
try {
    Group result = apiInstance.apiGroupsRead(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Group**](Group.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsRemoveDelete"></a>
# **apiGroupsRemoveDelete**
> Void apiGroupsRemoveDelete(id, userPk)



View to add a contact to a list

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
String userPk = "userPk_example"; // String | 
try {
    Void result = apiInstance.apiGroupsRemoveDelete(id, userPk);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsRemoveDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **userPk** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiGroupsUpdate"></a>
# **apiGroupsUpdate**
> Group apiGroupsUpdate(id, data)



Contact serializer

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Group data = new Group(); // Group | 
try {
    Group result = apiInstance.apiGroupsUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiGroupsUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Group**](Group.md)|  |

### Return type

[**Group**](Group.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiList"></a>
# **apiList**
> Void apiList()

This the main endpoint here there is not much to do here.

# event endpoint   /event/ [GET, POST]  /event/created/ [GET]  /event/accepted/ [GET]  /event/pending/ [GET]  /event/[pk]/ [GET, PUT, PATCH, DELETE]  /event/[pk]/vote_date/ [POST]  /event/[pk]/vote_place/ [POST]  /event/[pk]/assign/ [POST]  /event/[pk]/answer/ [PUT, PATCH]  /event/pk/place/ [POST]  /event/[event_pk]/place/[place_pk]/ [GET, PUT, PATCH, DELETE]  /event/pk/date/ [POST]  /event/[event_pk]/task/[date_pk]/ [GET, PUT, PATCH, DELETE]  /event/pk/task/ [POST]  /event/[event_pk]/task/[task_pk]/ [GET, PUT, PATCH, DELETE]  /event/pk/activity/ [POST]  /event/[event_pk]/activity/[activity_pk]/ [GET, PUT, PATCH, DELETE]  /event/[event_pk]/activity/[activity_pk]/subscribe/ [POST]  # category endpoint   /category/ [GET]  # search endpoint   /search/ [GET]  # contact endpoint   /groups/ [GET, POST]  /groups/[pk]/ [GET, PUT, PATCH, DELETE]  /groups/[pk]/add/ [POST]  /groups/[pk]/remove/[user_pk]/ [DELETE]  /contacts/ [GET]  /contacts/[pk] [DELETE]  # contact endpoint   /notifications/ [GET]  /notifications/[pk]/ [PUT, PATCH]  # searches endpoint   /searches/ [GET, POST]  /searches/[pk]/ [GET, PUT, PATCH, DELETE]  # Languages   /languages/ [GET]  # Currencies   /currencies/ [GET]

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
try {
    Void result = apiInstance.apiList();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiList");
    e.printStackTrace();
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiNotificationsList"></a>
# **apiNotificationsList**
> InlineResponse2003 apiNotificationsList(limit, offset)

Endpoint to list events or create a new one

To have details on a specific event use: /event/[pk]/

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2003 result = apiInstance.apiNotificationsList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiNotificationsList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2003**](InlineResponse2003.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiNotificationsPartialUpdate"></a>
# **apiNotificationsPartialUpdate**
> Notification apiNotificationsPartialUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Notification data = new Notification(); // NotificationUpdate | 
try {
    Notification result = apiInstance.apiNotificationsPartialUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiNotificationsPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Notification**](Notification.md)|  |

### Return type

[**Notification**](Notification.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiNotificationsUpdate"></a>
# **apiNotificationsUpdate**
> Notification apiNotificationsUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Notification data = new Notification(); // NotificationUpdate | 
try {
    Notification result = apiInstance.apiNotificationsUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiNotificationsUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Notification**](Notification.md)|  |

### Return type

[**Notification**](Notification.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiSearchList"></a>
# **apiSearchList**
> InlineResponse2002 apiSearchList(limit, offset)



Search events

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2002 result = apiInstance.apiSearchList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiSearchList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiSearchesCreate"></a>
# **apiSearchesCreate**
> Search apiSearchesCreate(data)

Endpoint to list events or create a new one

To have details on a specific event use: /event/[pk]/

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Search data = new Search(); // Search | 
try {
    Search result = apiInstance.apiSearchesCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiSearchesCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**Search**](Search.md)|  |

### Return type

[**Search**](Search.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiSearchesDelete"></a>
# **apiSearchesDelete**
> Void apiSearchesDelete(id)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
try {
    Void result = apiInstance.apiSearchesDelete(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiSearchesDelete");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |

### Return type

[**Void**](.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiSearchesList"></a>
# **apiSearchesList**
> InlineResponse2004 apiSearchesList(limit, offset)

Endpoint to list events or create a new one

To have details on a specific event use: /event/[pk]/

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2004 result = apiInstance.apiSearchesList(limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiSearchesList");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2004**](InlineResponse2004.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiSearchesPartialUpdate"></a>
# **apiSearchesPartialUpdate**
> Search apiSearchesPartialUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Search data = new Search(); // Search | 
try {
    Search result = apiInstance.apiSearchesPartialUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiSearchesPartialUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Search**](Search.md)|  |

### Return type

[**Search**](Search.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiSearchesRead"></a>
# **apiSearchesRead**
> InlineResponse2002 apiSearchesRead(id, limit, offset)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Integer limit = 56; // Integer | Number of results to return per page.
Integer offset = 56; // Integer | The initial index from which to return the results.
try {
    InlineResponse2002 result = apiInstance.apiSearchesRead(id, limit, offset);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiSearchesRead");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **limit** | **Integer**| Number of results to return per page. | [optional]
 **offset** | **Integer**| The initial index from which to return the results. | [optional]

### Return type

[**InlineResponse2002**](InlineResponse2002.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

<a name="apiSearchesUpdate"></a>
# **apiSearchesUpdate**
> Search apiSearchesUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.ApiApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

ApiApi apiInstance = new ApiApi();
String id = "id_example"; // String | 
Search data = new Search(); // Search | 
try {
    Search result = apiInstance.apiSearchesUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling ApiApi#apiSearchesUpdate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **String**|  |
 **data** | [**Search**](Search.md)|  |

### Return type

[**Search**](Search.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

