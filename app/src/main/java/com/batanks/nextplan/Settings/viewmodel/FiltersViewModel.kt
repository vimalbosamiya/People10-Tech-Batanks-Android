package com.batanks.nextplan.Settings.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.swagger.api.FilterAPI
import com.batanks.nextplan.swagger.model.AddFilter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class FiltersViewModel(private val filterAPI: FilterAPI):ViewModel() {
    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val postresponseLiveData:MutableLiveData<ApiResponse> = MutableLiveData()
    val deleteresponseLiveData:MutableLiveData<ApiResponse> = MutableLiveData()
    val updateresponseLiveData:MutableLiveData<ApiResponse> = MutableLiveData()

    fun getFiltersList() {

        disposables.add(filterAPI.apiFiltersList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                responseLiveData.setValue(ApiResponse.loading())
            }.doOnNext { }.subscribe({ result ->
                //RetrofitClient.cookieJar?.persist()
                responseLiveData.setValue(ApiResponse.success(result))
                println(result)
            }) { throwable ->
                responseLiveData.setValue(ApiResponse.error(throwable))
            })
    }

    fun addFilter(addFilter: AddFilter) {

        disposables.add(filterAPI.apiCreateFilter(addFilter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                postresponseLiveData.setValue(ApiResponse.loading())
            }.doOnNext { }.subscribe({ result ->
                //RetrofitClient.cookieJar?.persist()
                postresponseLiveData.setValue(ApiResponse.success(result))
                println(result)
            }) { throwable ->
                if (throwable is retrofit2.HttpException) {
                    val response = throwable.response()?.errorBody()?.string()
                    val responseBody = JSONObject(response)
                    val jArray: JSONArray = responseBody.getJSONArray("name")
                    for (i in 0 until jArray.length()) {
                        val value = jArray.getString(i)
                        Log.e("json", "$i=$value")
                        postresponseLiveData.value = ApiResponse.failure(value)
                    }
                }
            })
    }

    fun deleteFilter(id: Int?) {

        disposables.add(filterAPI.apiFilterDelete(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                deleteresponseLiveData.setValue(ApiResponse.loading())
            }.doOnNext { }.subscribe({ result ->
                //RetrofitClient.cookieJar?.persist()
                deleteresponseLiveData.setValue(ApiResponse.success(result))
            }) { throwable ->
                deleteresponseLiveData.setValue(ApiResponse.error(throwable))
            })
    }

    fun updateFilter(addFilter: AddFilter,id:Int?) {

        disposables.add(filterAPI.apiFilterUpdate(id,addFilter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                updateresponseLiveData.setValue(ApiResponse.loading())
            }.doOnNext { }.subscribe({ result ->
                //RetrofitClient.cookieJar?.persist()
                updateresponseLiveData.setValue(ApiResponse.success(result))
                println(result)
            }) { throwable ->
                if (throwable is retrofit2.HttpException) {
                    val response = throwable.response()?.errorBody()?.string()
                    val responseBody = JSONObject(response)
                    val jArray: JSONArray = responseBody.getJSONArray("name")
                    for (i in 0 until jArray.length()) {
                        val value = jArray.getString(i)
                        Log.e("json", "$i=$value")
                        updateresponseLiveData.value = ApiResponse.failure(value)
                    }
                }
            })
    }

    override fun onCleared() {
        disposables.clear()
    }


}