package com.eh.recyclerlistsample.database.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
class CategoryEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id") var id: Int = 0,
  @ColumnInfo(name = "name") val name: String
)