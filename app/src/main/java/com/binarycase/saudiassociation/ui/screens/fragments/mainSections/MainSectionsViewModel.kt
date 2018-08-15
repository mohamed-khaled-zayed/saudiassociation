package com.binarycase.saudiassociation.ui.screens.fragments.mainSections

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Handler
import com.binarycase.saudiassociation.models.SectionsModel
import com.binarycase.saudiassociation.service.ApiService
import com.binarycase.saudiassociation.service.response.MainSectionsResponse
import com.binarycase.saudiassociation.utils.Constants
import com.binarycase.saudiassociation.utils.DeviceUtils
import com.binarycase.saudiassociation.utils.StateView
import com.binarycase.saudiassociation.utils.StateView.Errors
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.Success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainSectionsViewModel @Inject constructor(val apiService: ApiService) : ViewModel() {
  private var mainSections: MutableLiveData<StateView> = MutableLiveData()
  private var mainSectionsList: ArrayList<MainSectionsResponse> = ArrayList()

  fun fetchSectionsList() {
    mainSections.postValue(Loading)
    apiService.getMainSections(Constants.APP_AUTH,DeviceUtils.getInstance()!!.deviceToken).enqueue(object :Callback<List<MainSectionsResponse>>{
      override fun onFailure(
        call: Call<List<MainSectionsResponse>>?,
        t: Throwable?
      ) {
        mainSections.postValue(Errors(t))
      }

      override fun onResponse(
        call: Call<List<MainSectionsResponse>>?,
        response: Response<List<MainSectionsResponse>>?
      ) {
        if (response==null)onFailure(call,Throwable(""))
        if(response?.isSuccessful!!){
          mainSectionsList.clear()
          mainSectionsList.addAll(response.body()!!)
          mainSections.postValue(Success(mainSectionsList))
        }else{
          onFailure(call,Throwable(""))
        }
      }
    })

  }

  fun getMainSections(): LiveData<StateView> {
    return mainSections
  }

}