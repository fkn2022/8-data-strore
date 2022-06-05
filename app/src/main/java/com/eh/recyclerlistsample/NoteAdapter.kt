package com.eh.recyclerlistsample

import android.graphics.Color
import com.eh.recyclerlistsample.NoteAdapter.NoteViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.eh.recyclerlistsample.R.id
import com.eh.recyclerlistsample.R.layout
import java.util.ArrayList
import java.util.Random

class NoteAdapter : Adapter<NoteViewHolder>() {
  private val notes = ArrayList<NoteModel>()
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    // описываем как создается NoteViewHolder
    val view = LayoutInflater.from(parent.context)
      .inflate(layout.rv_note_item, parent, false)
    return NoteViewHolder(view)
  }

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    // описываем как заполняется NoteViewHolder
    val model = notes[position]
    holder.title.text = model.title
    holder.text.text = model.text
    holder.date.text = model.formattedDate
    val rand = Random()
    val color = Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
    holder.category.setBackgroundColor(color)
  }

  override fun getItemCount(): Int {
    return notes.size
  }

  fun updateNotes(notes: ArrayList<NoteModel>?) {
    this.notes.clear()
    this.notes.addAll(notes!!)
    notifyDataSetChanged() // обновляет все видимые элементы
  }

  class NoteViewHolder(itemView: View) : ViewHolder(itemView) {
    // Один элемент списка
    var title: TextView
    var text: TextView
    var date: TextView
    var category: View

    init {
      title = itemView.findViewById<View>(id.tv_title) as TextView
      text = itemView.findViewById<View>(id.tv_text) as TextView
      date = itemView.findViewById<View>(id.tv_date) as TextView
      category = itemView.findViewById(id.v_category)
    }
  }
}