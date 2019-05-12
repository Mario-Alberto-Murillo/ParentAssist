package com.example.superpositionexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SelectChild extends AppCompatActivity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    private TextView contdowntxt;
    private Button contDownbtn;

    private CountDownTimer time;
    private long timeLeft = 3000;
    private boolean timerRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Timer

        //Bubble

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
            Toast.makeText(this, "if weeeee", Toast.LENGTH_SHORT).show();
        } else {
            initializeView();
        }
        initializeView();
    }

    private void initializeView() {
        findViewById(R.id.confirmSelection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(SelectChild.this, BubbleHeadService.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    initializeView();
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