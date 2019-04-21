package com.example.superpositionexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.superpositionexample.utilities.Utils;

public class Juego2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego2);
    }

    public void par (View view){
        Toast.makeText(this, "Par es un 2",Toast.LENGTH_LONG).show();
    }

    public void tercia (View view){
        Toast.makeText(this, "Tercia es un 3",Toast.LENGTH_LONG).show();
    }

    public void mediaDocenta (View view){
        Toast.makeText(this, "Media Docena es un 6",Toast.LENGTH_LONG).show();
        int numExcercice=readExerciceNum();
        updateExerciceNum(numExcercice);
        //readExerciceNum();
        goToHome();

    }
    public void updateExerciceNum(int num)
    {
        if(num>0)
        {
            DbSQL conn = new DbSQL(this, "db_config", null, 1);
            SQLiteDatabase db = conn.getWritableDatabase();

            String[] pram={"1"};
            ContentValues values= new ContentValues();
            values.put(Utils.FIELD_TOTALGAMESTOPLAY,num-1);

            db.update(Utils.TABLE_CONFIG,values,Utils.FIELD_ID+"=?",pram);
            db.close();
        }

    }
    public int readExerciceNum()
    {
        DbSQL conn = new DbSQL(this, "db_config", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] pram={"1"};
        String[] fields={Utils.FIELD_TOTALGAMESTOPLAY,Utils.FIELD_TOTALGAMESTOPLAY};

        try
        {
            Cursor cursor=db.query(Utils.TABLE_CONFIG, fields, Utils.FIELD_ID+"=?",pram,null,null,null);
            cursor.moveToFirst();

            Toast.makeText(this, "cantidad de ejercicios :"+cursor.getString(0), Toast.LENGTH_LONG).show();
            int result=cursor.getInt(0);
            cursor.close();

            return result;
        }
        catch(Exception e)
        {
            Toast.makeText(this, "No se encontro campo id=1", Toast.LENGTH_LONG).show();

        }
        return 0;
    }
    public void goToHome()
    {
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
