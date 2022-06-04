package com.eh.recyclerlistsample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText titleInput, textInput;
    private DatabaseHelper dbHelper;
    private Spinner spinner;
    private ArrayList<CategoryModel> categories;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        titleInput = (EditText) findViewById(R.id.et_title);
        textInput = (EditText) findViewById(R.id.et_text);

        dbHelper = new DatabaseHelper(this);

        spinner = (Spinner) findViewById(R.id.categories);
        categories = dbHelper.getCategories();

        ArrayList<String> content = new ArrayList<>();
        for (CategoryModel cat : categories) {
            content.add(cat.getName());
        }

        arrayAdapter = new ArrayAdapter<>(this,
            android.R.layout.simple_spinner_dropdown_item, content);
        spinner.setAdapter(arrayAdapter);
        if (!content.isEmpty())
            spinner.setSelection(0);

        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = titleInput.getText().toString();
        String text = textInput.getText().toString();
        int category = spinner.getSelectedItemPosition();

        NoteModel note = new NoteModel(title, text, System.currentTimeMillis(), category);

        dbHelper.createNote(note);

        setResult(RESULT_OK);
        finish();
    }
}
