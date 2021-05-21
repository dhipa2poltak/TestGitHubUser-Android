package com.testkrealogi.testgithubuser.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.testkrealogi.testgithubuser.model.User

@Suppress("unused")
class SearchUsersResponse {

  @SerializedName("total_count")
  @Expose
  var totalCount = -1

  @SerializedName("incomplete_results")
  @Expose
  var incompleteResults = false

  var items: List<User>? = null
}
