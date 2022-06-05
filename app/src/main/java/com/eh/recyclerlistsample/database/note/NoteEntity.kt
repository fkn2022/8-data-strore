package com.eh.recyclerlistsample.database.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eh.recyclerlistsample.NoteModel

@Entity(tableName = "notes")
data class NoteEntity(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id") var id: Int = 0,
  @ColumnInfo(name = "title") val title: String,
  @ColumnInfo(name = "text") val text: String,
  @ColumnInfo(name = "date") val date: Long,
  @ColumnInfo(name = "categoryId") val categoryId: Int
) {
  fun toModel(): NoteModel {
    return NoteModel(
      id,
      title,
      text,
      date,
      categoryId
    )
  }
}