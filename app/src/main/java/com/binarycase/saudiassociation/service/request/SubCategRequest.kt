package com.binarycase.saudiassociation.service.request

import com.google.gson.annotations.SerializedName

data class SubCategRequest(
  @SerializedName("cat_id") var catId: Int = 0,
  @SerializedName("device_id") var deviceID: String = "",
  @SerializedName("page") var page: Int = 1
    )