package com.dpfht.testgithubuser.di.module

import com.dpfht.testgithubuser.ui.details.DetailViewModel
import com.dpfht.testgithubuser.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel {
    MainViewModel(get())
  }

  viewModel {
    DetailViewModel()
  }
}