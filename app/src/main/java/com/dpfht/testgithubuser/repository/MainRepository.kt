package com.dpfht.testgithubuser.repository

import androidx.lifecycle.LiveData
import com.dpfht.testgithubuser.model.History
import com.dpfht.testgithubuser.model.response.SearchUsersResponse
import com.dpfht.testgithubuser.net.ResultWrapper

interface MainRepository {

  fun getHistoriesLiveData(): LiveData<List<History>>
  suspend fun getHistories(): MutableList<History>
  suspend fun getHistory(text: String): History?
  suspend fun insertHistories(histories: List<History>)
  suspend fun deleteHistories()
  suspend fun searchUsers(q: String, page: Int, perPage: Int): ResultWrapper<SearchUsersResponse>
}