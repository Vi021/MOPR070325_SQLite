package vn.iotstar.ltmob070325.SQLite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<NoteModel> noteList;

    public NoteAdapter(Context context, int layout, List<NoteModel> noteList) {
        this.context = context;
        this.layout = layout;
        this.noteList = noteList;
    }

    public class NoteViewHolder {
        TextView txt_content;
        ImageButton imgBtn_editNote;
        ImageButton imgBtn_removeNote;
    }


    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public NoteModel getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return noteList.get(position).getNid();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoteViewHolder noteViewHolder;

        if (convertView == null) {
            noteViewHolder = new NoteViewHolder();

            /***
            // less verbose
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            // unless using custom LayoutInflater or different parent
            convertView = View.inflate(context, layout, null);
            ***/
            // best practice?
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);

            noteViewHolder.txt_content = convertView.findViewById(R.id.txt_content);
            noteViewHolder.imgBtn_editNote = convertView.findViewById(R.id.imgBtn_editNote);
            noteViewHolder.imgBtn_removeNote = convertView.findViewById(R.id.imgBtn_removeNote);

            convertView.setTag(noteViewHolder);
        } else {
            noteViewHolder = (NoteViewHolder) convertView.getTag();
        }

        NoteModel note = noteList.get(position);
        noteViewHolder.txt_content.setText(note.getContent());

        return convertView;
    }
}
