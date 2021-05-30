package com.dpfht.testgithubuser.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
  val mIsShowDialogLoading = MutableLiveData<Boolean>()
  var isLoadingData = false

  val mToastMessage = MutableLiveData<String>()

  val isShowDialogLoading: LiveData<Boolean> = mIsShowDialogLoading
  val toastMessage: LiveData<String> get() = mToastMessage

  fun clearToastMessage() {
    this.mToastMessage.value = null
  }
}
