package com.testkrealogi.testgithubuser.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
  val isShowDialogLoading = MutableLiveData<Boolean>()
  var isLoadingData = false

  val toastMessage = MutableLiveData<String>()
}
