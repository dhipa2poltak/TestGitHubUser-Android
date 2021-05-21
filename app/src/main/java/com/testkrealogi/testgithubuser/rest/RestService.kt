package com.testkrealogi.testgithubuser.rest

import com.testkrealogi.testgithubuser.model.response.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestService {

  @GET("search/users")
  @Headers("Accept: application/vnd.github.v3+json", "Authorization: token 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")
  suspend fun searchUsers(@Query("q") q: String, @Query("page") page: Int, @Query("per_page") perPage: Int): SearchUsersResponse
}
