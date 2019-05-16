package com.example.superpositionexample;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class GetUserSQLite extends Activity {
    private String user="kk";
    public GetUserSQLite()
    {

    }

    public String getUsr()
    {
            DbSQL conn= new DbSQL(this,"db_con",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            Cursor row = db.rawQuery
                    ("select * from config where id = 1 ",null);

            if(row.moveToFirst())
            {
                this.user=row.getString(3);

                //Toast.makeText(this, row.getString(3), Toast.LENGTH_SHORT).show();
            }
            return this.user;


    }
}
