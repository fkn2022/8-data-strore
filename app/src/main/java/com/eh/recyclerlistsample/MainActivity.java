package com.eh.recyclerlistsample;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private NoteAdapter adapter;
  private ArrayList<NoteModel> notes = new ArrayList<>();
  private ActivityResultLauncher<Intent> activityResultLauncher
      = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      result -> {
        if (result.getResultCode() == RESULT_OK) {
          loadFromDatabase();
        }
      }
  );

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    recyclerView.addItemDecoration(new DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL)); // разделители элементов списка

    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new NoteAdapter();
    recyclerView.setAdapter(adapter);

    findViewById(R.id.btn_add).setOnClickListener(v -> {
      launchCreateNoteScreen();
    });

    loadFromDatabase();
  }

  private void launchCreateNoteScreen() {
    Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);

    activityResultLauncher.launch(intent);
  }

  /**
   * Загрузка заметок из базы даннх
   */
  private void loadFromDatabase() {
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    notes = dbHelper.getAllNotes();
    adapter.updateNotes(notes);
  }
}
