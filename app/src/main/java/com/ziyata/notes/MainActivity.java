package com.ziyata.notes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.ziyata.notes.Adapter.NotesAdapter;
import com.ziyata.notes.model.NotesModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvMain)
    RecyclerView rvMain;
    @BindView(R.id.fabMain)
    FloatingActionButton fabMain;

    // variable untuk DB Helper
    private DBNotesHelper dbNoteHelper;
    // Penampungan data
    private List<NotesModel> dataNotesList;
    // Membuat variable adapter
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Membuat objek DB Helper
        dbNoteHelper = new DBNotesHelper(this);

        //Kita inisiasikan variable list
        dataNotesList = new ArrayList<>();

        // Mengambil data dari SQLite
        getData();

        // Kita bikin object adapter
        notesAdapter = new NotesAdapter(this, dataNotesList);

        //setting layout manager
        rvMain.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        // Memasang Adapter ke Recycle View
        rvMain.setAdapter(notesAdapter);


    }

    private void getData() {
        // Kita membuat object Sqlitedatabase dengan mode Read
        SQLiteDatabase readData = dbNoteHelper.getReadableDatabase();

        // Membuat perintah mengambil data
        // SELECT * FROM Notes ORDER BY ID Judul DESC
        String query = "SELECT * FROM " + DBNotesHelper.MyColumns.namaTable + " ORDER BY "
                + DBNotesHelper.MyColumns.id_judul + " DESC";

        // Kita akan mengambil data menggunakan cursor
        Cursor cursor = readData.rawQuery(query, null);

        //kita arahkan cursor ke awal
        cursor.moveToFirst();

        // mengambil data secara berulang
        for(int count = 0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);
            dataNotesList.add(new NotesModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataNotesList.clear();
        getData();
        notesAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.fabMain)
    public void onViewClicked() {
        // Perintah untuk berpidah ke activity tambah
        startActivity(new Intent(this, TambahNotesActivity.class));
    }
}
