package com.example.appchildren;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    }

    public void opcionCorrecta2(View view){
        Toast.makeText(this,"Opcion acertada",Toast.LENGTH_LONG).show();
        ban2=1;
    }

}

