package com.binarycase.saudiassociation.ui.screens.fragments

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.binarycase.saudiassociation.service.ApiService
import com.binarycase.saudiassociation.service.request.StaticContentRequest
import com.binarycase.saudiassociation.service.response.StaticContentResponse
import com.binarycase.saudiassociation.utils.Constants
import com.binarycase.saudiassociation.utils.DeviceUtils
import com.binarycase.saudiassociation.utils.StateView
import com.binarycase.saudiassociation.utils.StateView.Errors
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.Success
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class StaticContentViewModel @Inject constructor(val apiService: ApiService) : ViewModel() {
 private val staticContent :MutableLiveData<StateView> = MutableLiveData()

  companion object {
    const val ABOUT_US = 1221
    const val CALL_US = 1222
    const val USAGE_POLICY = 1223
  }

  fun fetchData(contentType: Int) {
    var contentTypeString = ""
    when (contentType) {
      ABOUT_US -> {
        contentTypeString = "about-us"
      }
      CALL_US -> {
        contentTypeString = "contact-us"
      }
      USAGE_POLICY -> {
        contentTypeString = "usage-policy"
      }
    }
    staticContent.postValue(Loading)
    apiService.getStaticContent(Constants.APP_AUTH,Constants.CONTENT_TYPE, StaticContentRequest(DeviceUtils.getInstance()!!.deviceToken,contentTypeString))
        .enqueue(object :retrofit2.Callback<StaticContentResponse>{
          override fun onFailure(
            call: Call<StaticContentResponse>?,
            t: Throwable?
          ) {
            staticContent.postValue(Errors(t))
          }

          override fun onResponse(
            call: Call<StaticContentResponse>?,
            response: Response<StaticContentResponse>?
          ) {
            if (response==null)return
            if (response.body()==null) return
            if (response.isSuccessful){
              staticContent.postValue(Success(response.body()!!.content))
            }else{
              staticContent.postValue(Errors(Throwable("")))
            }
          }

        })
  }

  fun getStaticContent():LiveData<StateView>{
    return staticContent
  }
}