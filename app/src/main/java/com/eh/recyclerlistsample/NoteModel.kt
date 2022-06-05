package com.eh.recyclerlistsample

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NoteModel @JvmOverloads constructor(
  val id: Int = 0,
  val title: String,
  val text: String,
  val date: Long,
  val categoryId: Int = 0
) {
  val formattedDate: String
    get() {
      val date = Date(date)
      val dateFormat = SimpleDateFormat("dd MMMM HH:mm", Locale("RU"))
      return dateFormat.format(date)
    }
}