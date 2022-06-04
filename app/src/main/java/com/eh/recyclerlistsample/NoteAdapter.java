package com.eh.recyclerlistsample;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by IT SCHOOL on 14.02.2018.
 */
public class NoteAdapter extends
        RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final ArrayList<NoteModel> notes = new ArrayList<>();

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // описываем как создается NoteViewHolder
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_note_item, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        // описываем как заполняется NoteViewHolder
        NoteModel model = notes.get(position);
        holder.title.setText(model.getTitle());
        holder.text.setText(model.getText());
        holder.date.setText(model.getFormattedDate());
        Random rand = new Random();
        int color = Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        holder.category.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateNotes(ArrayList<NoteModel> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
        notifyDataSetChanged(); // обновляет все видимые элементы
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        // Один элемент списка
        TextView title, text, date;
        View category;

        public NoteViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            text = (TextView) itemView.findViewById(R.id.tv_text);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            category = itemView.findViewById(R.id.v_category);
        }

    }
}
