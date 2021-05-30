package com.dpfht.testgithubuser.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_history")
data class History(
  @PrimaryKey(autoGenerate = true)
  @NonNull
  val id: Long? = null,
  @ColumnInfo(name = "text")
  val text: String,
)
