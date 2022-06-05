package com.eh.recyclerlistsample.database.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
  @Query("SELECT * FROM notes")
  fun getAll(): List<NoteEntity>

  @Insert
  fun insert(note: NoteEntity): Unit

  @Delete
  fun delete(note: NoteEntity): Unit
}