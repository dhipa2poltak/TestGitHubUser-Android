package com.dpfht.testgithubuser.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dpfht.testgithubuser.model.History

@Dao
interface HistoryDao {
  @Query("SELECT * FROM tbl_history ORDER BY id DESC")
  fun getHistoriesLiveData(): LiveData<List<History>>

  @Query("SELECT * FROM tbl_history ORDER BY id DESC")
  suspend fun getHistories(): MutableList<History>

  @Query("SELECT * FROM tbl_history WHERE text = :text")
  suspend fun getHistory(text: String): History?

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertHistories(histories: List<History>)

  @Query("DELETE FROM tbl_history")
  suspend fun deleteHistories()
}
