package com.dpfht.testgithubuser.rest

import com.dpfht.testgithubuser.Config
import com.dpfht.testgithubuser.model.response.SearchUsersResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestService {

  @GET("search/users")
  @Headers("Accept: application/vnd.github.v3+json", "Authorization: token ${Config.TOKEN_GITHUB}")
  suspend fun searchUsers(@Query("q") q: String, @Query("page") page: Int, @Query("per_page") perPage: Int): SearchUsersResponse
}
