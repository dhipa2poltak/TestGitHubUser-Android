package com.dpfht.testgithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dpfht.testgithubuser.Config
import com.dpfht.testgithubuser.model.History
import com.dpfht.testgithubuser.model.User
import com.dpfht.testgithubuser.model.response.SearchUsersResponse
import com.dpfht.testgithubuser.net.ResultWrapper.GenericError
import com.dpfht.testgithubuser.net.ResultWrapper.NetworkError
import com.dpfht.testgithubuser.net.ResultWrapper.Success
import com.dpfht.testgithubuser.repository.MainRepository
import com.dpfht.testgithubuser.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

  val users: ArrayList<User> = ArrayList()

  private val mUserData = MutableLiveData<User>()
  private var mDoNotifyUserDataSetChanged = MutableLiveData(false)
  private var mDoShowResult = MutableLiveData(false)
  private var mDoShowNoData = MutableLiveData(false)

  var isNewSearch = true
  var isAllDataLoaded = false
  var page = 1
  var searchText = ""

  val userData: LiveData<User> get() = mUserData
  val doNotifyUserDataSetChanged: LiveData<Boolean> get() = mDoNotifyUserDataSetChanged
  val doShowResult: LiveData<Boolean> get() = mDoShowResult
  val doShowNoData: LiveData<Boolean> get() = mDoShowNoData

  fun clearNotifyUserDataSetChanged() {
    mDoNotifyUserDataSetChanged.value = null
  }

  fun clearShowResult() {
    mDoShowResult.value = null
  }

  fun clearShowNoData() {
    mDoShowNoData.value = null
  }

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
      mToastMessage.value = "please input the username"
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

    mIsShowDialogLoading.postValue(true)
    isLoadingData = true

    viewModelScope.launch {
      when (val searchUsersResponse = mainRepository.searchUsers(q, page, perPage)) {
        is NetworkError -> mToastMessage.value = "network error"
        is GenericError -> mToastMessage.value = "error ${searchUsersResponse.code} ${searchUsersResponse.error?.message}"
        is Success -> doSuccess(searchUsersResponse.value, page)
      }

      mIsShowDialogLoading.postValue(false)
      isLoadingData = false
    }
  }

  private fun doSuccess(response: SearchUsersResponse, page: Int) {
    if (response.items != null && response.items!!.isNotEmpty()) {

      if (isNewSearch) {
        users.clear()
        mDoNotifyUserDataSetChanged.value = true
        isNewSearch = false

        mDoShowResult.value = true
      }

      for (user in response.items!!) {
        users.add(user)
        mUserData.value = user
      }

      this@MainViewModel.page = page
      //toastMessage.value = "page: $page"

      if (users.size == response.totalCount) {
        isAllDataLoaded = true
      }
    } else {
      mDoShowNoData.value = users.size == 0
    }
  }
}
