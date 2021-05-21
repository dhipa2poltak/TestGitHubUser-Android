package com.testkrealogi.testgithubuser.di.module

import com.testkrealogi.testgithubuser.ui.details.DetailViewModel
import com.testkrealogi.testgithubuser.ui.main.MainViewModel
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