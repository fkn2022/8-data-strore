package com.eh.recyclerlistsample

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import java.util.ArrayList
import java.util.Date

/**
 * Created by victor on 27.02.18.
 */
class DatabaseHelper(context: Context?) :
  SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
  /**
   * Вызывается если база данных еще не создана
   */
  override fun onCreate(database: SQLiteDatabase) {
    database.execSQL(
      "CREATE TABLE " + TABLE_NAME
          + "("
          + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
          + COLUMN_NOTE_TITLE + " TEXT,"
          + COLUMN_NOTE_TEXT + " INTEGER,"
          + COLUMN_NOTE_DATE + " INTEGER,"
          + COLUMN_NOTE_CATEGORY_ID + " INTEGER,"
          + "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES "
          + CATEGORY_TABLE + "(" + COLUMN_CATEGORY_ID + "));"
    )
    database.execSQL(
      "CREATE TABLE " + CATEGORY_TABLE
          + "("
          + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
          + COLUMN_CATEGORY_NAME + " TEXT);"
    )
    val date = Date().time
    database.execSQL(
      "INSERT INTO " + CATEGORY_TABLE + " (" + COLUMN_CATEGORY_NAME + ") " +
          " VALUES (\"заметки\");"
    )
    database.execSQL(
      "INSERT INTO " + CATEGORY_TABLE + " (" + COLUMN_CATEGORY_NAME + ") " +
          " VALUES (\"список покупок\");"
    )
    database.execSQL(
      "INSERT INTO "
          + TABLE_NAME
          + "("
          + COLUMN_NOTE_TITLE
          + ", "
          + COLUMN_NOTE_DATE
          + ", "
          + COLUMN_NOTE_TEXT
          + ")"
          + " VALUES (\"Добро пожаловать\", "
          + date
          + ",\"Нажмите + , чтобы добавить заметку\");"
    )
  }

  /**
   * Вызывается, если база данных уже создана
   */
  override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    // описание миграции базы данных
    // прим. во второй версии появилась еще одна таблица
  }

  /**
   * Возвращает все заметки из таблицы notes
   */
  val allNotes: ArrayList<NoteModel>
    get() {
      val database = this.readableDatabase
      val query = "SELECT * FROM $TABLE_NAME;"
      val cursor = database.rawQuery(query, null)
      val notes = ArrayList<NoteModel>()
      if (cursor.moveToFirst()) {
        do {
          val note = NoteModel(
            cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_ID)).toInt(),
            cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TITLE)),
            cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TEXT)),
            cursor.getLong(cursor.getColumnIndex(COLUMN_NOTE_DATE))
          )
          notes.add(note)
        } while (cursor.moveToNext())
      }
      return notes
    }

  val categories: ArrayList<CategoryModel>
    get() {
      val database = this.readableDatabase
      val query = "SELECT * FROM $CATEGORY_TABLE;"
      val cursor = database.rawQuery(query, null)
      val categories = ArrayList<CategoryModel>()
      if (cursor.moveToFirst()) {
        do {
          val category = CategoryModel()
          category.id = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID)).toInt()
          category.name = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME))
          categories.add(category)
        } while (cursor.moveToNext())
      }
      return categories
    }

  fun createNote(note: NoteModel) {
    val database = this.writableDatabase
    database.execSQL(
      "INSERT INTO " + TABLE_NAME +
          "(" + COLUMN_NOTE_TITLE + ", "
          + COLUMN_NOTE_DATE + ", "
          + COLUMN_NOTE_TEXT + ", "
          + COLUMN_NOTE_CATEGORY_ID + ")"
          + " VALUES ("
          + "\"" + note.title + "\", "
          + "\"" + note.date + "\","
          + "\"" + note.text + "\", "
          + note.categoryId + ");"
    )
  }

  companion object {
    /**
     * Версия базы данных
     */
    private const val DATABASE_VERSION = 1

    /**
     * Название файла базы данных
     */
    private const val DATABASE_NAME = "app.db"

    /**
     * Имя таблицы
     */
    private const val TABLE_NAME = "notes"

    /**
     * Колонки в таблице
     */
    private const val COLUMN_NOTE_ID = "id"
    private const val COLUMN_NOTE_TITLE = "title"
    private const val COLUMN_NOTE_TEXT = "text"
    private const val COLUMN_NOTE_DATE = "date"
    private const val COLUMN_NOTE_CATEGORY_ID = "category_id"
    private const val CATEGORY_TABLE = "categories"
    private const val COLUMN_CATEGORY_ID = "id"
    private const val COLUMN_CATEGORY_NAME = "name"
  }
}