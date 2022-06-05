package com.eh.recyclerlistsample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eh.recyclerlistsample.database.category.CategoryDao
import com.eh.recyclerlistsample.database.category.CategoryEntity
import com.eh.recyclerlistsample.database.note.NoteDao
import com.eh.recyclerlistsample.database.note.NoteEntity

@Database(entities = [NoteEntity::class, CategoryEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
  abstract fun notesDao(): NoteDao

  abstract fun categoriesDao(): CategoryDao
}