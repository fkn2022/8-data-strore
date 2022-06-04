package com.eh.recyclerlistsample;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by victor on 27.02.18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Версия базы данных
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Название файла базы данных
     */
    private static final String DATABASE_NAME = "app.db";

    /**
     * Имя таблицы
     */
    private static final String TABLE_NAME = "notes";

    /**
     * Колонки в таблице
     */
    private static final String COLUMN_NOTE_ID = "id";
    private static final String COLUMN_NOTE_TITLE = "title";
    private static final String COLUMN_NOTE_TEXT = "text";
    private static final String COLUMN_NOTE_DATE = "date";
    private static final String COLUMN_NOTE_CATEGORY_ID = "category_id";


    private static final String CATEGORY_TABLE = "categories";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается если база данных еще не создана
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + TABLE_NAME
                + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOTE_TITLE + " TEXT,"
                + COLUMN_NOTE_TEXT + " INTEGER,"
                + COLUMN_NOTE_DATE + " INTEGER,"
                + COLUMN_NOTE_CATEGORY_ID + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_CATEGORY_ID + ") REFERENCES "
                + CATEGORY_TABLE + "(" + COLUMN_CATEGORY_ID + "));");

        database.execSQL("CREATE TABLE " + CATEGORY_TABLE
                + "("
                + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CATEGORY_NAME + " TEXT);");

        long date = new Date().getTime();
        database.execSQL("INSERT INTO " + CATEGORY_TABLE + " (" + COLUMN_CATEGORY_NAME + ") " +
            " VALUES (\"заметки\");");
        database.execSQL("INSERT INTO " + CATEGORY_TABLE + " (" + COLUMN_CATEGORY_NAME + ") " +
                " VALUES (\"список покупок\");");

        database.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_NOTE_TITLE + ", " + COLUMN_NOTE_DATE + ", " + COLUMN_NOTE_TEXT + ")"
                + " VALUES (\"Добро пожаловать\", " + date + ",\"Нажмите + , чтобы добавить заметку\");");
    }

    /**
     * Вызывается, если база данных уже создана
     * @param sqLiteDatabase
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // описание миграции базы данных
        // прим. во второй версии появилась еще одна таблица
    }


    /**
     * Возвращает все заметки из таблицы notes
     * @return
     */
    public ArrayList<NoteModel> getAllNotes () {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + ";";
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<NoteModel> notes = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                NoteModel note = new NoteModel();
                note.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_ID))));
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TITLE)));
                note.setDate(cursor.getLong(cursor.getColumnIndex(COLUMN_NOTE_DATE)));
                note.setText(cursor.getString(cursor.getColumnIndex(COLUMN_NOTE_TEXT)));
                notes.add(note);
            } while (cursor.moveToNext());
        }
        return notes;
    }

    public ArrayList<CategoryModel> getCategories() {

        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + CATEGORY_TABLE + ";";
        Cursor cursor = database.rawQuery(query, null);
        ArrayList<CategoryModel> categories = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                CategoryModel category = new CategoryModel();
                category.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID))));
                category.setName(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME)));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        return categories;

    }

    public void createNote(NoteModel note) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.execSQL("INSERT INTO " + TABLE_NAME +
                "(" + COLUMN_NOTE_TITLE + ", "
                    + COLUMN_NOTE_DATE + ", "
                    + COLUMN_NOTE_TEXT + ", "
                    + COLUMN_NOTE_CATEGORY_ID + ")"
                + " VALUES ("
                + "\"" + note.getTitle() + "\", "
                + "\"" + note.getDate() + "\","
                + "\"" + note.getText() + "\", "
                + note.getCategoryId() + ");");
    }
}
