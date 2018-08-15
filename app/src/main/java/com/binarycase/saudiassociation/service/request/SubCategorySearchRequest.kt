package com.binarycase.saudiassociation.service.request

import com.google.gson.annotations.SerializedName

data class SubCategorySearchRequest(
  @SerializedName("term") var term: String = "",
  @SerializedName("device_id") var deviceId: String = "",
  @SerializedName("page") var page: Int = 1

)