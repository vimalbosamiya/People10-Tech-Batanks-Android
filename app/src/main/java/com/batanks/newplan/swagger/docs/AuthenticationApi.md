# AuthenticationApi

All URIs are relative to *http://93.90.204.56/*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authenticationResetCreate**](AuthenticationApi.md#authenticationResetCreate) | **POST** authentication/reset/ | 


<a name="authenticationResetCreate"></a>
# **authenticationResetCreate**
> ResetPasswordConfirm authenticationResetCreate(data)



reset the user&#39;s password

### Example
```java
// Import classes:
//import io.swagger.client.ApiClient;
//import io.swagger.client.ApiException;
//import io.swagger.client.Configuration;
//import io.swagger.client.auth.*;
//import io.swagger.client.api.AuthenticationApi;

ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: DRF Token
ApiKeyAuth DRF Token = (ApiKeyAuth) defaultClient.getAuthentication("DRF Token");
DRF Token.setApiKey("YOUR API KEY");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//DRF Token.setApiKeyPrefix("Token");

AuthenticationApi apiInstance = new AuthenticationApi();
ResetPasswordConfirm data = new ResetPasswordConfirm(); // ResetPasswordConfirm | 
try {
    ResetPasswordConfirm result = apiInstance.authenticationResetCreate(data);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling AuthenticationApi#authenticationResetCreate");
    e.printStackTrace();
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **data** | [**ResetPasswordConfirm**](ResetPasswordConfirm.md)|  |

### Return type

[**ResetPasswordConfirm**](ResetPasswordConfirm.md)

### Authorization

[DRF Token](../README.md#DRF Token)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

