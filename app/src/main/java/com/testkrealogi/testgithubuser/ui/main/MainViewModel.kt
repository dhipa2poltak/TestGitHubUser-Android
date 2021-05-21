package com.testkrealogi.testgithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.testkrealogi.testgithubuser.Config
import com.testkrealogi.testgithubuser.model.History
import com.testkrealogi.testgithubuser.model.User
import com.testkrealogi.testgithubuser.model.response.SearchUsersResponse
import com.testkrealogi.testgithubuser.net.ResultWrapper.GenericError
import com.testkrealogi.testgithubuser.net.ResultWrapper.NetworkError
import com.testkrealogi.testgithubuser.net.ResultWrapper.Success
import com.testkrealogi.testgithubuser.repository.MainRepository
import com.testkrealogi.testgithubuser.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

  val users: ArrayList<User> = ArrayList()
  val userData = MutableLiveData<User>()
  var page = 1
  var searchText = ""
  var doNotifyUserDataSetChanged = MutableLiveData(false)
  var doShowResult = MutableLiveData(false)
  var doShowNoData = MutableLiveData(false)

  var isNewSearch = true
  var isAllDataLoaded = false

  fun getHistoriesLiveData(): LiveData<List<History>> {
    return mainRepository.getHistoriesLiveData()
  }

  private fun saveSearchText(searchText: String) {
    viewModelScope.launch {
      try {
        val history = mainRepository.getHistory(searchText)
        if (history == null) {
          val histories = mainRepository.getHistories()
          while (histories.size >= Config.MAX_HISTORY_COUNT) {
            histories.removeAt(histories.size - 1)
          }
          histories.add(History(text = searchText))

          mainRepository.deleteHistories()
          mainRepository.insertHistories(histories)
        }
      } catch (t: Throwable) {
        t.printStackTrace()
      }
    }
  }

  fun processInputUserName() {
    if (searchText.isEmpty()) {
      toastMessage.value = "please input the username"
      return
    }

    saveSearchText(searchText)

    page = 1
    isNewSearch = true
    isAllDataLoaded = false
    doSearchUsers(searchText, page)
  }

  fun doSearchUsers(q: String, page: Int, perPage: Int = Config.ROW_PER_PAGE) {
    if (isAllDataLoaded) {
      return
    }

    isShowDialogLoading.postValue(true)
    isLoadingData = true

    viewModelScope.launch {
      when (val searchUsersResponse = mainRepository.searchUsers(q, page, perPage)) {
        is NetworkError -> toastMessage.value = "network error"
        is GenericError -> toastMessage.value = "error ${searchUsersResponse.code} ${searchUsersResponse.error?.message}"
        is Success -> doSuccess(searchUsersResponse.value, page)
      }

      isShowDialogLoading.postValue(false)
      isLoadingData = false
    }
  }

  private fun doSuccess(response: SearchUsersResponse, page: Int) {
    if (response.items != null && response.items!!.isNotEmpty()) {

      if (isNewSearch) {
        users.clear()
        doNotifyUserDataSetChanged.value = true
        isNewSearch = false

        doShowResult.value = true
      }

      for (user in response.items!!) {
        users.add(user)
        userData.value = user
      }

      this@MainViewModel.page = page
      //toastMessage.value = "page: $page"

      if (users.size == response.totalCount) {
        isAllDataLoaded = true
      }
    } else {
      doShowNoData.value = users.size == 0
    }
  }
}
