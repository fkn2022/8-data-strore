package com.eh.recyclerlistsample.database.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
  @Query("SELECT * FROM categories")
  fun getAll(): List<CategoryEntity>

  @Insert
  fun insert(entity: CategoryEntity)
}