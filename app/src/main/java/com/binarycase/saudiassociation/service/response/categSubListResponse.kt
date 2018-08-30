package com.binarycase.saudiassociation.service.response

import android.databinding.ObservableBoolean
import com.google.gson.annotations.SerializedName

data class CategSubListResponse(
  @SerializedName("current_page") var currentPage: Int = 0,
  @SerializedName("data") var data: ArrayList<CategData> = ArrayList(),
  @SerializedName("first_page_url") var firstPageUrl: String = "",
  @SerializedName("from") var from: Int = 0,
  @SerializedName("last_page") var lastPage: Int = 0,
  @SerializedName("last_page_url") var lastPageUrl: String = "",
  @SerializedName("next_page_url") var nextPageUrl: Any = Any(),
  @SerializedName("path") var path: String = "",
  @SerializedName("per_page") var perPage: Int = 0,
  @SerializedName("prev_page_url") var prevPageUrl: Any = Any(),
  @SerializedName("to") var to: Int = 0,
  @SerializedName("total") var total: Int = 0
) {

  data class CategData(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("word_ar") var wordAr: String = "",
    @SerializedName("word_en") var wordEn: String = "",
    @SerializedName("video_file") var videoFile: String = "",
    @SerializedName("category_name") var categoryName: String = "",
    @SerializedName("share_url") var shareUrl: String = "",
    @SerializedName("in_fav") var isFavourite: Boolean = false
  ){
    var isExpanded :ObservableBoolean = ObservableBoolean(false)
    var isFavLoading :ObservableBoolean = ObservableBoolean(false)
  }
}