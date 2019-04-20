package com.example.appchildren;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void opcionIncorrecta (View view){
        int numrandom = (int) (Math.random()*4);
        switch (numrandom){
            case 0:
                Toast.makeText(this, "Elige otra opción ", Toast.LENGTH_SHORT).show();
                break;

            case 1:
                Toast.makeText(this, "Prueba otra vez ", Toast.LENGTH_SHORT).show();
                break;

            case 2:
                Toast.makeText(this, "Intentan de nuevo ", Toast.LENGTH_SHORT).show();
                break;

            case 3:
                Toast.makeText(this, "Vuelve a intentar ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void opcionCorrecta (View view){
        Toast.makeText(this, "Mariossdijfskdjfskdfsdf", Toast.LENGTH_SHORT).show();
        Intent aux = new Intent(this,Juego2.class);
        startActivity(aux);
    }

}
