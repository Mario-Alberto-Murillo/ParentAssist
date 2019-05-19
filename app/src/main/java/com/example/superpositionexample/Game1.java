package com.example.superpositionexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.superpositionexample.utilities.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Game1 extends AppCompatActivity {
    File exerciceStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        this.setTitle("Game 1");
        //Toast.makeText(this, "holaprro", Toast.LENGTH_SHORT).show();


    }

    public void check(View view)
    {
        //int num=readDB();
       // update(null,(num-1)+"",null,null);
        /*int numExcercice=readExerciceNum();
        updateExerciceNum(numExcercice);*/
        //readExerciceNum();

        int num=readDB();
        Toast.makeText(this,(num-1)+" restantes", Toast.LENGTH_SHORT).show();
        update(null,(num-1)+"",null,null);

        goToHome();
    }
    public int readDB()
    {
        try
        {
            DbSQL conn= new DbSQL(this,"db_con",null,1);
            SQLiteDatabase db = conn.getWritableDatabase();

            Cursor row = db.rawQuery
                    ("select * from config where id = 1 ",null);

            if(row.moveToFirst())
            {
                return Integer.valueOf(row.getString(4) );
            }

            return 1;
        }
        catch(Exception e)
        {
            return 0;
        }
    }
    public void update(String id, String games, String time, String user)
    {
        DbSQL conn= new DbSQL(this,"db_con",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("remainGames",games);

        try {
            db.update("config",content,"id=1",null);
        }catch (SQLException sqle)
        {
            Toast.makeText(this, "No se pudo ingresar el campo", Toast.LENGTH_SHORT).show();
        }

        db.close();
        //Toast.makeText(this, "Configuracion actualizada", Toast.LENGTH_LONG).show();
    }
    public void goToHome()
    {
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
