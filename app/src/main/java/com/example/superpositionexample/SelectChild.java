package com.example.superpositionexample;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SelectChild extends Activity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private EditText editTxt;
    private Button btn;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    private TextView txtChildinfo;
    public ValueEventListener eventListener;
    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Usuarios");

    private String user=null;
    String[] children= new String[50];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_child);

        txtChildinfo= findViewById(R.id.ChildInfo);
        //firebase DB
        //Toast.makeText(this, new GetUserSQLite().getUsr(), Toast.LENGTH_SHORT).show();

        //SqlDB
        readDB();

        checkDB();

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);

        } else {
            initializeView();
        }*/

    }
    /*private void initializeView() {
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(SelectChild.this, BubbleHeadService.class));
                finish();
            }
        });
    }*/
    public void selectChild (View view)
    {
        //enlazar el boton y obtener el texto del label para buscar si existe el morro o no
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Iterable dataChildren= dataSnapshot.child(user).child("niño").getChildren();


                }
                catch (Exception a)
                {
                    Toast.makeText(SelectChild.this, "No se encontro el registro", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SelectChild.this, "no se armo", Toast.LENGTH_SHORT).show();

            }

        };
        ref.addValueEventListener(eventListener);
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
                this.user=row.getString(3);

                Toast.makeText(this, row.getString(3)+"user-selectchild", Toast.LENGTH_SHORT).show();
            }
            return 1;
        }
        catch(Exception e)
        {
            return 0;
        }


    }

    public void checkDB()
    {
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    Iterable dataChildren= dataSnapshot.child(user).child("Hijos").getChildren();

                    txtChildinfo.setText("Actualmente los hijos que tiene registrados son:\n");

                    for(int i=0 ;dataChildren.iterator().hasNext(); i++)
                    {
                        children[i]=dataChildren.iterator().next().toString();
                        //Toast.makeText(SelectChild.this, childen[0], Toast.LENGTH_SHORT).show();

                        txtChildinfo.append("\n"+children[i].split(" ")[4].substring(0,children[i].split(" ")[4].length()-1));
                    }
                }
                catch (Exception a)
                {
                    Toast.makeText(SelectChild.this, "No se encontro el registro", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SelectChild.this, "no se armo", Toast.LENGTH_SHORT).show();

            }

        };
        ref.addValueEventListener(eventListener);

    }

}
