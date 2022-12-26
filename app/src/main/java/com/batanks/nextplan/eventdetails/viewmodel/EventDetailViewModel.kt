package com.batanks.nextplan.eventdetails.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batanks.nextplan.arch.response.ApiResponse
import com.batanks.nextplan.eventdetails.EventDetailView
import com.batanks.nextplan.swagger.api.EventAPI
import com.batanks.nextplan.swagger.model.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class EventDetailViewModel (private val eventApi: EventAPI /*, private val authApi: AuthenticationAPI*/) : ViewModel() {

    private val disposables = CompositeDisposable()
    val responseLiveData: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataEventDelete: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataDateDelete: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataPlaceDelete: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataActionDelete: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataActivityDelete: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataAccept: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataActivityAccept: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataAssignAction: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataActivityReject: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataGuest: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataVoteDate: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataDatePatch: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataVotePlace: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataTaskPatch: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataTaskPatchFull: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataTaskPatchWithoutAssignee: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataActivityPartialUpdate: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataActivityFullUpdate: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataActivityCreate: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataCreateTask: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataComment: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataEditComment: MutableLiveData<ApiResponse> = MutableLiveData()
    val responseLiveDataDeleteComment: MutableLiveData<ApiResponse> = MutableLiveData()
    var response : Event? = null

    fun getEventData (id: String?){

        disposables.add(eventApi.eventRead(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveData.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveData.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveData.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventDelete (id: String?){

        disposables.add(eventApi.apiEventDelete(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataEventDelete.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    println(it)
                }.subscribe({ result ->
                    responseLiveDataEventDelete.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataEventDelete.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventDateDelete (datePk: String?, eventPk: String?){

        disposables.add(eventApi.apiEventDateDelete(datePk, eventPk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataDateDelete.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    println(it)
                }.subscribe({ result ->
                    responseLiveDataDateDelete.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataDateDelete.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventPlaceDelete (eventPk: String?, placePk: String?){

        disposables.add(eventApi.apiEventPlaceDelete(eventPk, placePk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataPlaceDelete.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    println(it)
                }.subscribe({ result ->
                    responseLiveDataPlaceDelete.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataPlaceDelete.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventTaskDelete (eventPk: String?, taskPk: String?){

        disposables.add(eventApi.apiEventTaskDelete(eventPk, taskPk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataActionDelete.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    println(it)
                }.subscribe({ result ->
                    responseLiveDataActionDelete.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataActionDelete.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventActivityDelete (activityPk: String?, eventPk: String?){

        disposables.add(eventApi.apiEventActivityDelete(activityPk, eventPk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataActivityDelete.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    println(it)
                }.subscribe({ result ->
                    responseLiveDataActivityDelete.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataActivityDelete.setValue(ApiResponse.error(throwable))
                })
    }

    fun acceptEvent (id1: EventDetailView, id: String?, data: EventAccept?){

        disposables.add(eventApi.apiEventAnswerUpdate(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataAccept.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataAccept.setValue(ApiResponse.success(result))
                }) { throwable ->
                   //responseLiveDataAccept.value = ApiResponse.error(throwable)
                if (throwable is retrofit2.HttpException) {
                    val response = throwable.response()?.errorBody()?.string()
                    val responseBody = JSONObject(response)
                    val jArray: JSONArray = responseBody.getJSONArray("amount")
                    for (i in 0 until jArray.length()) {
                        val value = jArray.getString(i)
                        val amount = value.split(":".toRegex()).toTypedArray()
                        errorDialog(id1,amount)
                    }
                }
                })
    }

    fun assignAction (id: String?, data: AssignTask?){

        disposables.add(eventApi.apiEventAssignCreate(id,data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                responseLiveDataAssignAction.setValue(ApiResponse.loading())
            }
            .doOnNext {
                //println(it)
            }.subscribe({ result ->
                responseLiveDataAssignAction.setValue(ApiResponse.success(result))
            }) { throwable ->
                responseLiveDataAssignAction.setValue(ApiResponse.error(throwable))
            })
    }

    fun activityAccepted (activityPk: String?, eventPk: String?, data: Empty?){

        disposables.add(eventApi.apiEventActivitySubscribeCreate(activityPk,eventPk,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataActivityAccept.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataActivityAccept.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataActivityAccept.setValue(ApiResponse.error(throwable))
                })
    }

    fun activityRejected (activityPk: String?, eventPk: String?, data: Empty?){

        disposables.add(eventApi.apiEventActivityUnsubscribe(activityPk,eventPk,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataActivityReject.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataActivityReject.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataActivityReject.setValue(ApiResponse.error(throwable))
                })
    }

    fun eventInvitationAccepted(
        context: Context,
        id: String?,
        invitationPk: String?,
        data: GuestAmount?
    ){

        disposables.add(eventApi.apiEventInvitationUpdate(id, invitationPk, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataGuest.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataGuest.setValue(ApiResponse.success(result))
                }) { throwable ->
                    //responseLiveDataGuest.setValue(ApiResponse.error(throwable))
                if (throwable is retrofit2.HttpException) {
                    val response = throwable.response()?.errorBody()?.string()
                    val responseBody = JSONObject(response)
                    val jArray: JSONArray = responseBody.getJSONArray("amount")
                    for (i in 0 until jArray.length()) {
                        val value = jArray.getString(i)
                        val amount = value.split(":".toRegex()).toTypedArray()
                        errorDialog(context,amount)
                    }
                }
                })
    }

    private fun errorDialog(context: Context, value: Array<String>){
        MaterialAlertDialogBuilder(context)
            .setTitle(value[0])
            .setMessage(null)
            .setNegativeButton(context.getString(android.R.string.no)) { _, _ -> }
            .setPositiveButton(context.getString(android.R.string.yes)){_, _ ->}
            .setCancelable(false)
            .show()
    }

    fun dateVoteClicked(eventPk: String?, datePk: String?){

        disposables.add(eventApi.apiEventVoteDateCreate(eventPk, datePk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataVoteDate.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataVoteDate.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataVoteDate.setValue(ApiResponse.error(throwable))
                })

    }

    fun placeVoteClicked(eventPk: String?, placePk: String?){

        disposables.add(eventApi.apiEventVotePlaceCreate(eventPk, placePk)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataVotePlace.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataVotePlace.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataVotePlace.setValue(ApiResponse.error(throwable))
                })

    }

    fun apiEventDatePartialUpdate(datePk: String?, eventPk: String?, data: PostDates?){

        disposables.add(eventApi.apiEventDatePartialUpdate(datePk, eventPk, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataDatePatch.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataDatePatch.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataDatePatch.setValue(ApiResponse.error(throwable))
                })

    }

    fun apiEventTaskPatch(eventPk: String?, taskPk: String?, data: TaskPatch?){

        disposables.add(eventApi.apiEventTaskPatch(eventPk, taskPk, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataTaskPatch.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataTaskPatch.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataTaskPatch.setValue(ApiResponse.error(throwable))
                })

    }

    fun apiEventFullTaskPatch(eventPk: String?, taskPk: String?, data: TaskPost?){

        disposables.add(eventApi.apiEventFullTaskPatch(eventPk, taskPk, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataTaskPatchFull.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataTaskPatchFull.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataTaskPatchFull.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventTaskPatchWithoutAssignee(eventPk: String?, taskPk: String?, data: TaskPostWithoutAssignee?){

        disposables.add(eventApi.apiEventTaskPatchWithoutAssignee(eventPk, taskPk, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataTaskPatchWithoutAssignee.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataTaskPatchWithoutAssignee.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataTaskPatchWithoutAssignee.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventActivityPartialUpdate(activityPk: String?, eventPk: String?, data: PostActivities?){

        disposables.add(eventApi.apiEventActivityPartialUpdate(activityPk, eventPk, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataActivityPartialUpdate.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataActivityPartialUpdate.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataActivityPartialUpdate.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventActivityUpdate(activityPk: String?, eventPk: String?, data: PostActivities?){

        disposables.add(eventApi.apiEventActivityUpdate(activityPk, eventPk, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataActivityFullUpdate.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataActivityFullUpdate.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataActivityFullUpdate.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventActivityCreate(id: String?,  data: PostActivities?){

        disposables.add(eventApi.apiEventActivityCreate(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataActivityCreate.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataActivityCreate.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataActivityCreate.setValue(ApiResponse.error(throwable))
                })
    }

    fun apiEventTaskCreate(id: String?, data: TaskPost?){

        disposables.add(eventApi.apiEventTaskCreate(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataCreateTask.setValue(ApiResponse.loading())
                }.doOnNext {}.subscribe({ result ->
                    //RetrofitClient.cookieJar?.persist()
                    responseLiveDataCreateTask.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataCreateTask.setValue(ApiResponse.error(throwable))
                })
    }

    fun postComment (id: String?, data: PostComments?){

        disposables.add(eventApi.postComment(id, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataComment.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataComment.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataComment.setValue(ApiResponse.error(throwable))
                })
    }

    fun editComment (id: String?,  commentId: String?, data: PostComments?){

        disposables.add(eventApi.editComment(id, commentId, data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataEditComment.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataEditComment.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataEditComment.setValue(ApiResponse.error(throwable))
                })
    }

    fun deleteComment (id: String?,  commentId: String?){

        disposables.add(eventApi.deleteComment(id, commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    responseLiveDataDeleteComment.setValue(ApiResponse.loading())
                }
                .doOnNext {
                    //println(it)
                }.subscribe({ result ->
                    responseLiveDataDeleteComment.setValue(ApiResponse.success(result))
                }) { throwable ->
                    responseLiveDataDeleteComment.setValue(ApiResponse.error(throwable))
                })
    }

    override fun onCleared() {
        disposables.clear()
    }

 }
