# EventApi

All URIs are relative to *http://93.90.204.56/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**eventDelete**](EventApi.md#eventDelete) | **DELETE** event/{id}/ | 
[**eventPartialUpdate**](EventApi.md#eventPartialUpdate) | **PATCH** event/{id}/ | 
[**eventRead**](EventApi.md#eventRead) | **GET** event/{id}/ | 
[**eventUpdate**](EventApi.md#eventUpdate) | **PUT** event/{id}/ | 


<a name="eventDelete"></a>
# **eventDelete**
> Void eventDelete(id)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EventApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

EventApi apiInstance = new EventApi();
String id = "id_example"; // String | 
try {
    Void result = apiInstance.eventDelete(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EventApi#eventDelete");
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

<a name="eventPartialUpdate"></a>
# **eventPartialUpdate**
> Event eventPartialUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EventApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

EventApi apiInstance = new EventApi();
String id = "id_example"; // String | 
Event data = new Event(); // Event | 
try {
    Event result = apiInstance.eventPartialUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EventApi#eventPartialUpdate");
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

<a name="eventRead"></a>
# **eventRead**
> Event eventRead(id)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EventApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

EventApi apiInstance = new EventApi();
String id = "id_example"; // String | 
try {
    Event result = apiInstance.eventRead(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EventApi#eventRead");
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

<a name="eventUpdate"></a>
# **eventUpdate**
> Event eventUpdate(id, data)



Event specific interaction

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.EventApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

EventApi apiInstance = new EventApi();
String id = "id_example"; // String | 
Event data = new Event(); // Event | 
try {
    Event result = apiInstance.eventUpdate(id, data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling EventApi#eventUpdate");
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

