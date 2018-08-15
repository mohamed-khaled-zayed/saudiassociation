package com.binarycase.saudiassociation.service.request

import com.google.gson.annotations.SerializedName

data class FavouriteRequest(
  @SerializedName("device_id") var deviceId: String = "",
  @SerializedName("word_id") var wordId: Int = 0
)