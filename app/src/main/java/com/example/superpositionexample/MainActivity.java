package com.example.superpositionexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.superpositionexample.utilities.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    public ValueEventListener eventListener;
    private CountDownTimer time;
    private long timeLeft = 3000;
    private boolean timerRunning;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mauthListenner;

    private EditText txtUsr;
    private EditText txtPass;

    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("a");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        txtUsr= findViewById(R.id.txtUrs);
        txtPass= findViewById(R.id.txtPass);

        //Database
        //insert();
        //update();
        //readDB();
        //Toast.makeText(this, new GetUserSQLite().getUsr()+"si se armo ", Toast.LENGTH_SHORT).show();


        /*DbSQL conn= new DbSQL(this,"db_con",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.execSQL("drop table config");

        db.close();
        Toast.makeText(this, "Insert", Toast.LENGTH_LONG).show();*/

        //insert();
        if(readDB()==0)
        {
            insert();
        }
        readDB();
        //Bubble

    }

    public void logIn(View view)
    {
        //checkDB();
        String usr=txtUsr.getText().toString();
        String pass=txtPass.getText().toString();
        if(usr==null | pass==null)
        {
            usr="a";
            pass="a";
        }

        mAuth.signInWithEmailAndPassword(usr, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(MainActivity.this, "Auth corect: "+user.getEmail(), Toast.LENGTH_SHORT).show();
                            update("1","1","5",txtUsr.getText().toString().replace(".",""));

                            Intent intent = new Intent(MainActivity.this, SelectChild.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
    public void insert()
    {
        DbSQL conn= new DbSQL(this,"db_con",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("id","1");
        content.put("totalgamestoplay","5");
        content.put("time","6");
        content.put("user","mario2.0");
        content.put("remainGames","5");

        try {
            db.insert("config",null,content);
            Toast.makeText(this, "Insert", Toast.LENGTH_LONG).show();
        }catch (SQLException sqle)
        {
            Toast.makeText(this, "No se pudo ingresar el campo", Toast.LENGTH_SHORT).show();
        }

        db.close();

    }
    public void update(String id, String games, String time, String user)
    {
        DbSQL conn= new DbSQL(this,"db_con",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues content = new ContentValues();
        content.put("id",id);
        content.put("totalgamestoplay",games);
        content.put("time",time);
        content.put("user",user);
        content.put("remainGames",games);

        try {
            db.update("config",content,"id=1",null);
        }catch (SQLException sqle)
        {
            Toast.makeText(this, "No se pudo ingresar el campo", Toast.LENGTH_SHORT).show();
        }

        db.close();
        Toast.makeText(this, "Todo bien todo correcto", Toast.LENGTH_LONG).show();
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
                Toast.makeText(this, row.getString(3)+"->actual user", Toast.LENGTH_SHORT).show();
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
        //db.execSQL("DROP TABLE IF EXIST");
        String insert = "INSERT INTO "+ Utils.TABLE_CONFIG+" ( "+Utils.FIELD_ID+","+Utils.FIELD_TOTALGAMESTOPLAY+ ","+Utils.FIELD_TIME +","+Utils.FIELD_USER+") " +
                "VALUES "+"(a,b,c,d)";

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
            Toast.makeText(this, "cantidad de ejercicios :"+cursor.getString(0), Toast.LENGTH_LONG).show();
            int result = cursor.getInt(0);

            cursor.close();
            return result;
        }
        catch(Exception e)
        {
            Toast.makeText(this, "No se encontro campo id=1", Toast.LENGTH_LONG).show();
            return 0;
        }
    }
    public void checkDB()
    {
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception a)
                {
                    //Toast.makeText(MainActivity.this, "no se armó chinga", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "no se armo", Toast.LENGTH_SHORT).show();

            }
        };
        ref.addValueEventListener(eventListener);

    }
   /* private void initializeView() {
        findViewById(R.id.bubble_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(MainActivity.this, BubbleHeadService.class));
                finish();
            }
        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    //initializeView();
                } else {
                    Toast.makeText(this,
                            "El permiso para posicionar elementos sobre otras aplicaciones no está disponible. Cerrando aplicación",
                            Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}