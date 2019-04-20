package com.example.appchildren;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
        Intent aux = new Intent(this, Juego3.class);
        startActivity(aux);
    }

}
