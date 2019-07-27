package ru.netologia.sharinm_diplom_notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends BaseAdapter {

    private List<Note> items;

    private LayoutInflater inflater;

    NoteAdapter(Context context, List<Note> items) {
        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    void addItem(Note item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Note getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_list_view, parent, false);
        }

        Note Note = items.get(position);

        TextView headline = view.findViewById(R.id.headline);
        TextView textNote = view.findViewById(R.id.textNote);
        TextView dateDeadline = view.findViewById(R.id.dateDeadline);
        TextView dateUpdateNote = view.findViewById(R.id.dateUpdateNote);

        headline.setText(Note.getHeadline());
        textNote.setText(Note.getTextNote());
        dateDeadline.setText(Note.getDateDeadline().toString());
        dateUpdateNote.setText(Note.getDateUpdateNote().toString());

        return view;
    }
}
