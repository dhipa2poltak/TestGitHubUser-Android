package com.dpfht.testgithubuser.repository

import androidx.lifecycle.LiveData
import com.dpfht.testgithubuser.dao.HistoryDao
import com.dpfht.testgithubuser.model.History
import com.dpfht.testgithubuser.model.response.SearchUsersResponse
import com.dpfht.testgithubuser.net.ResultWrapper
import com.dpfht.testgithubuser.net.safeApiCall
import com.dpfht.testgithubuser.rest.RestService
import kotlinx.coroutines.Dispatchers

class MainRepositoryImpl(
  private val restService: RestService,
  private val historyDao: HistoryDao): MainRepository {

  override fun getHistoriesLiveData(): LiveData<List<History>> {
    return historyDao.getHistoriesLiveData()
  }

  override suspend fun getHistories(): MutableList<History> {
    return historyDao.getHistories()
  }

  override suspend fun getHistory(text: String): History? {
    return historyDao.getHistory(text)
  }

  override suspend fun insertHistories(histories: List<History>) {
    historyDao.insertHistories(histories)
  }

  override suspend fun deleteHistories() {
    historyDao.deleteHistories()
  }

  override suspend fun searchUsers(q: String, page: Int, perPage: Int): ResultWrapper<SearchUsersResponse> {
    return safeApiCall(Dispatchers.IO) { restService.searchUsers("$q in:login", page, perPage) }
  }
}
