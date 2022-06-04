package com.eh.recyclerlistsample;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IT SCHOOL on 14.02.2018.
 */
public class NoteModel {

  private int id;
  private String title;
  private String text;
  private int categoryId;
  private long date;

  public NoteModel(String title, String text, long date, int categoryId) {
    this.title = title;
    this.text = text;
    this.date = date;
    this.categoryId = categoryId;
  }

  public NoteModel() {

  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  public String getFormattedDate() {
    Date date = new Date(getDate());
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM HH:mm", new Locale("RU"));
    return dateFormat.format(date);
  }
}
