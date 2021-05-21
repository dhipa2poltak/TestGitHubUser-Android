package com.testkrealogi.testgithubuser.net

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

  val message: String? = null,
  @SerializedName("documentation_url")
  val documentationUrl: String? = null
)
