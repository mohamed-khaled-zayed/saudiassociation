package com.binarycase.saudiassociation.service.response

import com.google.gson.annotations.SerializedName

data class StaticContentResponse(
  @SerializedName("title") var title: String = "",
  @SerializedName("content") var content: String = ""
)