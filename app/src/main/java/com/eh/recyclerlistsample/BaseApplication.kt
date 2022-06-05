package com.eh.recyclerlistsample

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eh.recyclerlistsample.database.AppDatabase

class BaseApplication: Application() {

  lateinit var database: AppDatabase

  private val clb = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
      super.onCreate(db)
      initializeWithPreperadData(db)
    }
  }

  private fun initializeWithPreperadData(database: SupportSQLiteDatabase) {
    database.execSQL(
      "INSERT INTO " + "categories" + " (" + "name" + ") " +
          " VALUES (\"заметки\");"
    )
    database.execSQL(
      "INSERT INTO " + "categories" + " (" + "name" + ") " +
          " VALUES (\"список покупок\");"
    )
    database.execSQL(
      "INSERT INTO "
          + "notes"
          + "("
          + "title"
          + ", "
          + "date"
          + ", "
          + "text"
          + ", "
          + "categoryId"
          + ")"
          + " VALUES (\"Добро пожаловать\", "
          + System.currentTimeMillis()
          + ",\"Нажмите + , чтобы добавить заметку\"" +
          ", 0);"
    )
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
    database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "notes_db")
      .addCallback(clb)
      .allowMainThreadQueries()
      .build()
  }

  companion object {
    @JvmStatic lateinit var instance: BaseApplication
  }

}