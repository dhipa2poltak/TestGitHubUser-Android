package com.dpfht.testgithubuser.di.module

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import com.dpfht.testgithubuser.R
import com.dpfht.testgithubuser.ui.adapter.HistoryAdapter
import com.dpfht.testgithubuser.ui.adapter.UserAdapter
import com.dpfht.testgithubuser.ui.main.MainViewModel
import org.koin.dsl.module

val myActivityModule = module {
  factory { provideHistoryAdapter() }
  factory { provideUserAdapter(it[0], get()) }
  factory { provideLoadingDialog(it[0]) }
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
