package com.ziyata.notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TambahNotesActivity extends AppCompatActivity {

    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtIsiCatatan)
    EditText edtIsiCatatan;
    @BindView(R.id.btnSave)
    Button btnSave;

    // Buat variable untuk database
    private DBNotesHelper dbNotesHelper;
    // Buat variable untuk menampung data dari user
    private String getJudul, getIsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_notes);
        ButterKnife.bind(this);

        // Untuk mengganti judul actionbar
        setTitle("Add New Data");

        // Membuat Objek untuk memanggil DB Helper
        dbNotesHelper = new DBNotesHelper(this);
    }

    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        getData();
        saveData();
    }

    private void saveData() {
        // Membuat Objek SQLite Database dengan mode menulis
        SQLiteDatabase create = dbNotesHelper.getWritableDatabase();

        // Kita tampung dara dari user ke dalam ContentValues agar meringkas
        ContentValues values = new ContentValues();
        values.put(DBNotesHelper.MyColumns.Judul, getJudul);
        values.put(DBNotesHelper.MyColumns.isi, getIsi);

        // Kita tambahkan data baru ke dalam table
        create.insert(DBNotesHelper.MyColumns.namaTable, null, values);
        Toast.makeText(this, "Tersimpan", Toast.LENGTH_SHORT).show();

        // Menghapus isian dari user pada EditText agar terlihat sudah tersimpan
        clearData();

        // Finish
        finish();
    }

    private void clearData() {
        edtIsiCatatan.setText("");
        edtJudul.setText("");
    }

    private void getData() {
        // Mengambil data dari inputan dari user
        getJudul = edtJudul.getText().toString();
        getIsi = edtIsiCatatan.getText().toString();
    }
}
