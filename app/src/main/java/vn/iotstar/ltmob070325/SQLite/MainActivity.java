package vn.iotstar.ltmob070325.SQLite;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler dbHandler;
    ListView listView;
    ArrayList<NoteModel> noteList;
    NoteAdapter noteAdapter;


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
        noteAdapter = new NoteAdapter(this, R.layout.row_listitem_note, noteList);

        listView = findViewById(R.id.listView);
        listView.setAdapter(noteAdapter);
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
        while (cursor.moveToNext()) {
            String content = cursor.getString(1);
            //Log.d("Content", content);
            Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
        }
    }
}