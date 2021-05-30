package com.dpfht.testgithubuser.di.module

import android.content.Context
import com.squareup.picasso.Picasso
import com.dpfht.testgithubuser.Config
import com.dpfht.testgithubuser.dao.HistoryDao
import com.dpfht.testgithubuser.database.HistoryDatabase
import com.dpfht.testgithubuser.repository.MainRepository
import com.dpfht.testgithubuser.repository.MainRepositoryImpl
import com.dpfht.testgithubuser.rest.RestService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

  single { providePicasso(androidContext()) }

  single { provideDatabase(androidContext()) }
  single { provideHistoryDao(get()) }
  single { provideMainRepository(get(), get()) }

  single { provideOkHttpClient() }
  single { provideRetrofit(get(), Config.API_BASE_URL) }
  single { provideRestService(get()) }
}

fun providePicasso(context: Context): Picasso {
  return Picasso.Builder(context).build()
}

fun provideMainRepository(restService: RestService, historyDao: HistoryDao): MainRepository {
  return MainRepositoryImpl(restService, historyDao)
}

fun provideDatabase(context: Context): HistoryDatabase {
  return HistoryDatabase.getDatabase(context)
}

fun provideHistoryDao(toolTrackingDatabase: HistoryDatabase): HistoryDao {
  return toolTrackingDatabase.historyDao()
}

fun provideOkHttpClient(): OkHttpClient {
  return OkHttpClient.Builder()
    .build()
}

fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
  return Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
}

fun provideRestService(retrofit: Retrofit): RestService {
  return retrofit.create(RestService::class.java)
}
