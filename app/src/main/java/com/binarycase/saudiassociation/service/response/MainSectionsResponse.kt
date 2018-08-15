package com.binarycase.saudiassociation.service.response

import com.google.gson.annotations.SerializedName

data class MainSectionsResponse(
  @SerializedName("id") var id: Int = 0,
  @SerializedName("name") var name: String = "",
  @SerializedName("icon") var icon: String = ""
)