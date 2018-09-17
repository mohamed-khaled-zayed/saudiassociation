package com.binarycase.saudiassociation.ui.screens.fragments.categorySubList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.binarycase.saudiassociation.service.ApiService
import com.binarycase.saudiassociation.service.request.FavouriteRequest
import com.binarycase.saudiassociation.service.request.SubCategRequest
import com.binarycase.saudiassociation.service.request.SubCategorySearchRequest
import com.binarycase.saudiassociation.service.response.CategSubListResponse
import com.binarycase.saudiassociation.service.response.CategSubListResponse.CategData
import com.binarycase.saudiassociation.service.response.FavouriteResponse
import com.binarycase.saudiassociation.utils.Constants
import com.binarycase.saudiassociation.utils.DeviceUtils
import com.binarycase.saudiassociation.utils.StateView
import com.binarycase.saudiassociation.utils.StateView.Empty
import com.binarycase.saudiassociation.utils.StateView.Errors
import com.binarycase.saudiassociation.utils.StateView.Loading
import com.binarycase.saudiassociation.utils.StateView.PaginationError
import com.binarycase.saudiassociation.utils.StateView.PaginationFinished
import com.binarycase.saudiassociation.utils.StateView.PaginationLoading
import com.binarycase.saudiassociation.utils.StateView.PaginationSuccess
import com.binarycase.saudiassociation.utils.StateView.Success
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

class CategorySubListViewModel @Inject constructor(val serviceApi: ApiService) : ViewModel() {
  val categSubList: ArrayList<CategData> = ArrayList()
  private var categSubListener: MutableLiveData<StateView> = MutableLiveData()
  var currentPage = 1

  private lateinit var sectionsCall: Call<CategSubListResponse>
  private lateinit var searchCall: Call<CategSubListResponse>

  fun fetchSectionsList(
    categId: Int
  ) {
    if (!inPagination())
      categSubListener.postValue(Loading)
    else
      categSubListener.postValue(PaginationLoading)

   sectionsCall = serviceApi.getSubCategList(
        if (categId==0){"favorites/list"}else {"list/words"},
        Constants.CONTENT_TYPE, Constants.APP_AUTH,
        SubCategRequest(categId, DeviceUtils.getInstance()!!.deviceToken, currentPage)
    )
       sectionsCall.enqueue(object : Callback<CategSubListResponse> {
          override fun onFailure(
            call: Call<CategSubListResponse>?,
            t: Throwable?
          ) {
            if (!inPagination()){
              categSubListener.postValue(Errors(t))
            }
            else{
              categSubListener.postValue(PaginationError(t))

            }
          }

          override fun onResponse(
            call: Call<CategSubListResponse>?,
            response: Response<CategSubListResponse>?
          ) {
            if (response == null) onFailure(call, Throwable(""))
            if (response!!.isSuccessful) {
              if (response.code() == 204 || response.body()!!.data.isEmpty()) {
                if (!inPagination()) {
                  categSubListener.postValue(Empty)
                } else {
                  categSubListener.postValue(PaginationFinished)
                }
              } else {
                val wordsList = response.body()!!
                if (!inPagination()) {
                  categSubListener.postValue(Success(wordsList))
                } else {
                  categSubListener.postValue(PaginationSuccess(wordsList))
                }
              }
            } else {
              onFailure(call, Throwable(""))
            }
          }

        })
  }

  fun getCategorySubListener(): LiveData<StateView> {
    return categSubListener
  }

  fun resetPagination() {
    currentPage = 1

  }

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
            } else {
              addTofavourite.postValue(
                  Errors(Throwable("response error code = ${response.code()}"))
              )
            }
          }
        })
    return addTofavourite
  }

  fun searchForExamsList(
    query: String?,
    categId: Int
  ) {
    if (!inPagination())
      categSubListener.postValue(Loading)
    else
      categSubListener.postValue(PaginationLoading)

  searchCall =    serviceApi.searchInSubCategories(
        Constants.CONTENT_TYPE, Constants.APP_AUTH,
        SubCategorySearchRequest(query ?: "", DeviceUtils.getInstance()!!.deviceToken, currentPage,categId)
    )

    searchCall.enqueue(object : Callback<CategSubListResponse> {
          override fun onFailure(
            call: Call<CategSubListResponse>?,
            t: Throwable?
          ) {
            if (!inPagination())
              categSubListener.postValue(Errors(t))
            else
              categSubListener.postValue(PaginationError(t))
          }

          override fun onResponse(
            call: Call<CategSubListResponse>?,
            response: Response<CategSubListResponse>?
          ) {
            if (response == null) onFailure(call, Throwable(""))
            if (response!!.isSuccessful) {
              if (response.code() == 204 || response.body()!!.data.isEmpty()) {
                if (inPagination()) {
                  categSubListener.postValue(PaginationFinished)
                } else {
                  categSubListener.postValue(Empty)
                }
              } else {
                val wordsList = response.body()!!
                if (!inPagination()) {
                  categSubListener.postValue(Success(wordsList))
                } else {
                  categSubListener.postValue(PaginationSuccess(wordsList))
                }
              }
            } else {
              onFailure(call, Throwable(""))
            }
          }
        })
  }

  fun inPagination() = currentPage > 1

  fun stopSearchCall(){
    if (this::searchCall.isInitialized)
    searchCall.cancel()
  }
  fun stopFetchingCall(){
    if (this::sectionsCall.isInitialized)
      sectionsCall.cancel()
  }
}