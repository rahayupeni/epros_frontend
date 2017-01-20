package com.example.renaissance.epros;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class splashscreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000; //set timer (5000 = 5 dtk)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashscreen.this, login_ac.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
