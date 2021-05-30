package com.dpfht.testgithubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dpfht.testgithubuser.dao.HistoryDao
import com.dpfht.testgithubuser.model.History

@Database(version = 1, entities = [History::class], exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {

  abstract fun historyDao(): HistoryDao

  companion object {
    private var INSTANCE: HistoryDatabase? = null

    fun getDatabase(context: Context): HistoryDatabase {
      val tempInstance = INSTANCE
      if (tempInstance != null) {
        return tempInstance
      }

      synchronized(this) {
        val instance = Room.databaseBuilder(context.applicationContext,
          HistoryDatabase::class.java,
          "history_database")
          .build()
        INSTANCE = instance
        return instance
      }
    }
  }
}
