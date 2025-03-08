package vn.iotstar.ltmob070325.SQLite;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnNoteActionListener {
    DatabaseHandler dbHandler;
    ListView listView;
    ArrayList<NoteModel> noteList;
    NoteAdapter noteAdapter;
    ImageButton imgBtn_addNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, R.layout.row_listitem_note, noteList, this);

        InitDatabaseSQLite();
        //createDatabaseSQLite();
        databaseSQLite();

        listView = findViewById(R.id.listView);
        listView.setAdapter(noteAdapter);

        imgBtn_addNote = findViewById(R.id.imgBtn_addNote);
        imgBtn_addNote.setOnClickListener(v -> this.showNoteDialog(false, "", -1));
    }

    private void InitDatabaseSQLite() {
        dbHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        dbHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, Content VARCHAR(200))");
    }

    private void createDatabaseSQLite() {
        dbHandler.QueryData("INSERT INTO Notes VALUES(null, 'SQLite Example 1')");
        dbHandler.QueryData("INSERT INTO Notes VALUES(null, 'SQLite Example 2')");
        dbHandler.QueryData("INSERT INTO Notes VALUES(null, 'SQLite Example 3')");
    }

    private void databaseSQLite() {
        Cursor cursor = dbHandler.GetData("SELECT * FROM Notes");
        noteList.clear();
        while (cursor.moveToNext()) {
            String content = cursor.getString(1);
            noteList.add(new NoteModel(cursor.getInt(0), content));
            //Log.d("Content", content);
            //Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        }
        noteAdapter.notifyDataSetChanged();
    }

    private void showNoteDialog(boolean isEdit, String noteText, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_note, null);
        builder.setView(dialogView);

        TextView txt_tittle = dialogView.findViewById(R.id.txt_title);
        EditText editTxt_content = dialogView.findViewById(R.id.editTxt_content);
        Button btn_ok = dialogView.findViewById(R.id.btn_ok);
        ImageButton btn_close = dialogView.findViewById(R.id.btn_close);


        txt_tittle.setText(isEdit ? "Edit Note" : "New Note");

        if (isEdit) {
            editTxt_content.setText(noteText);
        }

        AlertDialog dialog = builder.create();

        btn_close.setOnClickListener(v -> dialog.dismiss());

        btn_ok.setOnClickListener(v -> {
            String enteredText = editTxt_content.getText().toString();

            if (enteredText.isEmpty()) {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isEdit) {
                if (position < 0) {
                    Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbHandler.QueryData("UPDATE Notes SET Content = '" + enteredText + "' WHERE Id = '" + noteList.get(position).getNid() + "'");
            } else {
                dbHandler.QueryData("INSERT INTO Notes VALUES(null, '" + enteredText + "')");
            }

            databaseSQLite();
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onEditNote(int position, NoteModel note) {
        showNoteDialog(true, note.getContent(), position);
    }

    @Override
    public void onRemoveNote(int position) {
        dbHandler.QueryData("DELETE FROM Notes WHERE Id = '" + noteList.get(position).getNid() + "'");
        databaseSQLite();
    }
}