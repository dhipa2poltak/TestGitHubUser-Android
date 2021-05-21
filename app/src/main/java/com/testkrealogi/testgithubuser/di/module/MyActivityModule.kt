package com.testkrealogi.testgithubuser.di.module

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import com.testkrealogi.testgithubuser.R
import com.testkrealogi.testgithubuser.dao.HistoryDao
import com.testkrealogi.testgithubuser.repository.MainRepository
import com.testkrealogi.testgithubuser.repository.MainRepositoryImpl
import com.testkrealogi.testgithubuser.rest.RestService
import com.testkrealogi.testgithubuser.ui.adapter.HistoryAdapter
import com.testkrealogi.testgithubuser.ui.adapter.UserAdapter
import com.testkrealogi.testgithubuser.ui.main.MainViewModel
import org.koin.dsl.module

val myActivityModule = module {
  factory { provideMainRepository(get(), get()) }
  factory { provideHistoryAdapter() }
  factory { provideUserAdapter(it[0], get()) }
  factory { provideLoadingDialog(it[0]) }
}

fun provideMainRepository(restService: RestService, historyDao: HistoryDao): MainRepository {
  return MainRepositoryImpl(restService, historyDao)
}

fun provideHistoryAdapter(): HistoryAdapter {
  return HistoryAdapter(mutableListOf())
}

fun provideUserAdapter(viewModel: MainViewModel, picasso: Picasso): UserAdapter {
  return UserAdapter(viewModel.users, picasso)
}

fun provideLoadingDialog(activity: Activity): AlertDialog {
  return AlertDialog.Builder(activity)
    .setCancelable(false)
    .setView(R.layout.dialog_loading)
    .create()
}
