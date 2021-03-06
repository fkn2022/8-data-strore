package com.eh.recyclerlistsample

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.ArrayAdapter
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import com.eh.recyclerlistsample.R.id
import com.eh.recyclerlistsample.R.layout
import com.eh.recyclerlistsample.database.AppDatabase
import com.eh.recyclerlistsample.database.note.NoteEntity
import java.util.ArrayList

class CreateNoteActivity : AppCompatActivity() {

  private lateinit var titleInput: EditText
  private lateinit var textInput: EditText
  private lateinit var spinner: Spinner

  private lateinit var dbHelper: AppDatabase


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_create_note)
    titleInput = findViewById<View>(id.et_title) as EditText
    textInput = findViewById<View>(id.et_text) as EditText
    dbHelper = BaseApplication.instance.database
    spinner = findViewById<View>(id.categories) as Spinner
    val categories = dbHelper.categoriesDao().getAll()
    val content = ArrayList<String?>()
    for (cat in categories) {
      content.add(cat.name)
    }
    val arrayAdapter = ArrayAdapter(
      this,
      android.R.layout.simple_spinner_dropdown_item,
      content
    )
    spinner.adapter = arrayAdapter
    if (content.isNotEmpty()) spinner.setSelection(0)
    findViewById<View>(id.confirm).setOnClickListener { saveNote() }
  }

  private fun saveNote() {
    val title = titleInput.text.toString()
    val text = textInput.text.toString()
    val category = spinner.selectedItemPosition
    val note = NoteEntity(
      title = title,
      text = text,
      date = System.currentTimeMillis(),
      categoryId = category
    )
    dbHelper.notesDao().insert(note)
    setResult(RESULT_OK)
    finish()
  }
}