package com.binarycase.saudiassociation.ui.screens.fragments.searchCategSubList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.binarycase.saudiassociation.service.ApiService
import com.binarycase.saudiassociation.service.request.FavouriteRequest
import com.binarycase.saudiassociation.service.request.SubCategorySearchRequest
import com.binarycase.saudiassociation.service.response.CategSubListResponse
import com.binarycase.saudiassociation.service.response.CategSubListResponse.CategData
import com.binarycase.saudiassociation.service.response.FavouriteResponse
import com.binarycase.saudiassociation.ui.screens.fragments.categorySubList.CategorySubListFragment
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

class SearchCategViewModel @Inject constructor(val serviceApi: ApiService) : ViewModel() {

  private val categSubListener: MutableLiveData<StateView> = MutableLiveData()
  var currentPage: Int = 0
  var wordsList :ArrayList<CategData> = ArrayList()


  fun toggleFavourite(wordItem: CategData): LiveData<StateView> {
    val addTofavourite = MutableLiveData<StateView>()
    addTofavourite.postValue(Loading)
    val serviceAction = if (wordItem.isFavourite) {
      "remove"
    } else {
      "add"
    }
    serviceApi.toggleFavourite(
        Constants.APP_AUTH,
        serviceAction, Constants.CONTENT_TYPE,
        FavouriteRequest(DeviceUtils.getInstance()!!.deviceToken, wordItem.id)
    )
        .enqueue(object : Callback<FavouriteResponse> {
          override fun onFailure(
            call: Call<FavouriteResponse>?,
            t: Throwable?
          ) {
            addTofavourite.postValue(Errors(t))
          }

          override fun onResponse(
            call: Call<FavouriteResponse>?,
            response: Response<FavouriteResponse>?
          ) {
            if (response == null) {
              addTofavourite.postValue(Errors(Throwable("response = null")))
              return
            }
            if (response.isSuccessful) {
              addTofavourite.postValue(Success(!wordItem.isFavourite))
            }else{
              addTofavourite.postValue(Errors(Throwable("response error code = ${response.code()}")))
            }
          }
        })
    return addTofavourite
  }

  fun getCategorySubListener(): LiveData<StateView> {
    return categSubListener
  }

}