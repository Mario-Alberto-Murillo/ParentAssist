package com.example.superpositionexample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbSQL extends SQLiteOpenHelper {


    public DbSQL(Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE config (id int, totalgamestoplay TEXT, time TEXT, user TEXT, remainGames TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //db.execSQL("DROP TABLE IF EXISTS config");
    }
}
