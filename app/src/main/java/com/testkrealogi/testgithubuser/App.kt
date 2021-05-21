package com.testkrealogi.testgithubuser

import android.app.Application
import com.testkrealogi.testgithubuser.di.module.appModule
import com.testkrealogi.testgithubuser.di.module.myActivityModule
import com.testkrealogi.testgithubuser.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@App)
      modules(listOf(appModule, viewModelModule, myActivityModule))
    }
  }
}
