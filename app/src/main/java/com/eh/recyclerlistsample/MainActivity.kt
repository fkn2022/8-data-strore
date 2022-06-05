package com.eh.recyclerlistsample

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eh.recyclerlistsample.R.id
import com.eh.recyclerlistsample.R.layout

class MainActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var adapter: NoteAdapter

  private val activityResultLauncher = registerForActivityResult(
    StartActivityForResult()
  ) { result: ActivityResult ->
    if (result.resultCode == RESULT_OK) {
      loadFromDatabase()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    recyclerView = findViewById<View>(id.recycler_view) as RecyclerView
    recyclerView.addItemDecoration(
      DividerItemDecoration(
        this,
        DividerItemDecoration.VERTICAL
      )
    ) // разделители элементов списка
    recyclerView.layoutManager = LinearLayoutManager(this)
    adapter = NoteAdapter()
    recyclerView.adapter = adapter
    findViewById<View>(id.btn_add).setOnClickListener { launchCreateNoteScreen() }
    loadFromDatabase()
  }

  private fun launchCreateNoteScreen() {
    val intent = Intent(this@MainActivity, CreateNoteActivity::class.java)
    activityResultLauncher.launch(intent)
  }

  /**
   * Загрузка заметок из базы даннх
   */
  private fun loadFromDatabase() {
    val dbHelper = BaseApplication.instance.database
    val notes = dbHelper.notesDao().getAll()
    adapter.updateNotes(notes.map {
      it.toModel()
    })
  }
}