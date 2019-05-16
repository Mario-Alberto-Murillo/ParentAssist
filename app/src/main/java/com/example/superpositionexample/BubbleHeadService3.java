package com.example.superpositionexample;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Map;

public class BubbleHeadService3 extends Service implements Runnable{
    TimerPermissionV2 time;
    Thread t;
    boolean running=true;
    Class[] games={Game1.class,Juego2.class,Juego3.class,Game1.class};
    int aux=0;
    int timePerGame=10;
    int actualExcerciceNum=0;
    int totalGames=0;

    public BubbleHeadService3() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Crear DB
        /*setExerciceNum();
        updateExerciceNum(2);
        aux=getExerciceNum();*/
        aux=readDB();

        //Crear timer
        time= new TimerPermissionV2();
        time.setTimeLimit(timePerGame);
        time.start();

        t= new Thread(this);
        t.start();
        //Toast.makeText(this, "mames", Toast.LENGTH_SHORT).show();

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
                totalGames = Integer.valueOf( row.getString(1) );
                timePerGame = Integer.valueOf( row.getString(2) );
                actualExcerciceNum = Integer.valueOf( row.getString(4) );
            }

            return 1;
        }
        catch(Exception e)
        {
            return 0;
        }
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
    private void setExerciceNum()
    {
        DbSQL conn = new DbSQL(this, "db_config", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String insert = "INSERT INTO "+ Utils.TABLE_CONFIG+" ( "+Utils.FIELD_ID+","+Utils.FIELD_TOTALGAMESTOPLAY+ ","+Utils.FIELD_TIME +") " +
                "VALUES "+"("+1+","+2+","+5+")";

        db.execSQL(insert);
        db.close();
    }
    private int getExerciceNum()
    {
        DbSQL conn = new DbSQL(this, "db_config", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        String[] pram={"1"};
        String[] fields={Utils.FIELD_TOTALGAMESTOPLAY,Utils.FIELD_TOTALGAMESTOPLAY};

        try
        {
            Cursor cursor=db.query(Utils.TABLE_CONFIG, fields, Utils.FIELD_ID+"=?",pram,null,null,null);
            cursor.moveToFirst();
            //Toast.makeText(this, "cantidad de ejercicios :"+cursor.getString(0), Toast.LENGTH_LONG).show();
            int result = cursor.getInt(0);

            cursor.close();
            return result;
        }
        catch(Exception e)
        {
            //Toast.makeText(this, "No se encontro campo id=1", Toast.LENGTH_LONG).show();
            return 0;
        }
    }

    public void goToHome()
    {
        Intent intent= new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Nullable
    public static Activity getActivity() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {

        Class activityThreadClass = Class.forName("android.app.ActivityThread");
        Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
        Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
        activitiesField.setAccessible(true);

        Map<Object, Object> activities = (Map<Object, Object>) activitiesField.get(activityThread);
        if (activities == null)
            return null;

        for (Object activityRecord : activities.values()) {
            Class activityRecordClass = activityRecord.getClass();
            Field pausedField = activityRecordClass.getDeclaredField("paused");
            pausedField.setAccessible(true);
            if (!pausedField.getBoolean(activityRecord)) {
                Field activityField = activityRecordClass.getDeclaredField("activity");
                activityField.setAccessible(true);
                Activity activity = (Activity) activityField.get(activityRecord);
                return activity;
            }
        }

        return null;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

        startService(new Intent(BubbleHeadService3.this, BlockService.class));
        Toast.makeText(this, "bloqueado", Toast.LENGTH_SHORT).show();
        running=false;
    }


    @Override
    public void run() {

        while(running==true)
        {


           try {
                t.sleep(1500);

                Intent intent = new Intent(BubbleHeadService3.this, Game1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    }
}