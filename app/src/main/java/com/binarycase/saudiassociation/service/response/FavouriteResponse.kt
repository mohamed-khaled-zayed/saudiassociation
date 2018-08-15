package com.binarycase.saudiassociation.service.response

import com.google.gson.annotations.SerializedName

data class FavouriteResponse(
  @SerializedName("message") var message: String = ""
)