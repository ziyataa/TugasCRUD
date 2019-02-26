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

public class UpdateNotesActivity extends AppCompatActivity {

    @BindView(R.id.edtJudul)
    EditText edtJudul;
    @BindView(R.id.edtIsiCatatan)
    EditText edtIsiCatatan;
    @BindView(R.id.btnSave)
    Button btnSave;

    //TODO 2 Membuat variable yang dibutuhkan
    private Bundle bundle;
    private String judul,isi;
    private int id_judul;
    private DBNotesHelper dbNotesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);
        ButterKnife.bind(this);

        // TODO 3
        setTitle("Update Note");

        // Membuat object DB helper
        dbNotesHelper = new DBNotesHelper(this);

        // Menangkap data bundle
        bundle = getIntent().getExtras();

        // Pengecekan bundle
        if(bundle != null){
            showData();
        }

    }

    private void showData() {
        // Mengambil data id yang ada di dalam bundle
        id_judul = bundle.getInt(Constant.KEY_ID);
        judul = bundle.getString(Constant.KEY_ISI);
        isi = bundle.getString(Constant.KEY_ISI);

        // Menampilkan data ke layar
        edtJudul.setText(judul);
        edtIsiCatatan.setText(isi);
    }

    //TODO 1 Butterknife
    @OnClick(R.id.btnSave)
    public void onViewClicked() {
        // TODO 4 Menyimpan data update ke SQLite
        //Mengambil data dari EditText
        getData();

        // Mengupdate data
        updateData();

        // Menghapus data di EditText agar terlihat terupdate
        clearData();

        // Finish
        finish();
    }

    private void clearData() {
        edtIsiCatatan.setText("");
        edtJudul.setText("");
    }

    private void updateData() {
        //Bikin object SQLiteDatabase untuk kita dapat menggunakan operasi SQLite, mode Read untuk update
        SQLiteDatabase database = dbNotesHelper.getReadableDatabase();

        // Menampilkan data ke dalam content values
        ContentValues values = new ContentValues();

        // Mengisi data ke content values
        values.put(DBNotesHelper.MyColumns.Judul, judul);
        values.put(DBNotesHelper.MyColumns.isi, isi);

        // Membuat query untuk pencarian data bedasarkan ID Judul
        String selection = DBNotesHelper.MyColumns.id_judul + " LIKE ? ";

        // Menampung id yang ditargetkan
        String[] selectionArgs = {String.valueOf(id_judul)};

        // Melakukan operasi update
        database.update(DBNotesHelper.MyColumns.namaTable, values, selection, selectionArgs);
        Toast.makeText(this, "Berhasil diubah", Toast.LENGTH_SHORT).show();
    }

    private void getData() {
        judul = edtJudul.getText().toString();
        isi = edtIsiCatatan.getText().toString();
    }
}
