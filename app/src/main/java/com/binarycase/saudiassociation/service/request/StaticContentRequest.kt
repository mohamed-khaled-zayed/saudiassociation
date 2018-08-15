package com.binarycase.saudiassociation.service.request

import com.google.gson.annotations.SerializedName

data class StaticContentRequest(
  @SerializedName("device_id") var deviceId: String = "",
  @SerializedName("content_slug") var contentSlug: String = ""
)