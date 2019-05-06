package com.example.superpositionexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.superpositionexample.utilities.Utils;

public class Juego3 extends AppCompatActivity {

    public int ban1;
    public int ban2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego3);

        ban1=0;
        ban2=0;
    }


    public void opcionIncorrecta(View view){
        Toast.makeText(this,"Has escogido una opción incorrecta",Toast.LENGTH_LONG).show();
        ban1=0;
        ban2=0;
    }

    public void opcionCorrecta1(View view){
        Toast.makeText(this,"Correcto",Toast.LENGTH_LONG).show();
        ban1=1;
        if (ban2==1)
        {
            int num=readDB();
            update(null,(num-1)+"",null,null);
           /* int numExcercice=readExerciceNum();
            updateExerciceNum(numExcercice);*/
            //readExerciceNum();
            ban2=0;
            ban1=0;
            goToHome();
        }
    }

    public void opcionCorrecta2(View view){
        Toast.makeText(this,"Opcion acertada",Toast.LENGTH_LONG).show();
        ban2=1;
        if(ban1==1)
        {
            int num=readDB();
            update(null,(num-1)+"",null,null);
           /* int numExcercice=readExerciceNum();
            updateExerciceNum(numExcercice);*/
            //readExerciceNum()
            ban2=0;
            ban1=0;
            goToHome();
        }
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
        Toast.makeText(this, "Configuracion actualizada", Toast.LENGTH_LONG).show();
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

