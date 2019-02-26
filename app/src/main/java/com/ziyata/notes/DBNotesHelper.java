package com.ziyata.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBNotesHelper extends SQLiteOpenHelper {
    //TODO 4 Membuat variable constant nama table dan nama kolom yang diinginkan
    //Variable ini bisa diakses semua class
    public static abstract class MyColumns implements BaseColumns{
        public static final String namaTable = "Notes";
        public static final String id_judul = "ID_Judul";
        public static final String Judul = "Judul";
        public static final String isi = "Isi";
    }

    //TODO 2 membuat variable namaDB dan versi
    private static final String namaDatabase = "catatan.db";
    private static final int versiDatabase = 1;

    //TODO 3 Membuat konstruktor DBHelper
    public DBNotesHelper(Context context){
        super(context, namaDatabase, null, versiDatabase);
    }

    //TODO 1 Implement method dari SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO 6 Menjalankan perintah untuk membuat table
        db.execSQL(SQL_CREATE_TABLE);

    }

    //TODO 5 Perintah untuk membuat table
    // "CREATE TABLE  Notes(ID Judul INTEGER PRIMARY KEY AUTO INCREMENT, Judul TEXT NOT NULL, Isi TEXT NOT NULL)"
    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + MyColumns.namaTable +
            "(" + MyColumns.id_judul + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MyColumns.Judul + " TEXT NOT NULL, "
            + MyColumns.isi + " TEXT NOT NULL)";

    //TODO 7 Perintah untuk menghapus table
    // "Drop TABLE IF EXISTS Notes"
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + MyColumns.namaTable;

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);

    }
}
