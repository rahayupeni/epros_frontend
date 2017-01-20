package com.example.renaissance.epros;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class pilihstatus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihstatus);
        ImageButton btn_perusahaan = (ImageButton) findViewById(R.id.btn_perusahaan);
        btn_perusahaan.setOnClickListener(new View.OnClickListener(){
                                              public void onClick(View v){
                                                  startActivity(new Intent(pilihstatus.this, register_perusahaan.class));
                                              }
                                          }
        );
        ImageButton btn_komunitas = (ImageButton) findViewById(R.id.btn_komunitas);
        btn_komunitas.setOnClickListener(new View.OnClickListener(){
                                             public void onClick(View v){
                                                 startActivity(new Intent(pilihstatus.this, register_komins.class));
                                                 finish();
                                             }
                                         }
        );
        ImageButton btn_instansi = (ImageButton) findViewById(R.id.btn_instansi);
        btn_instansi.setOnClickListener(new View.OnClickListener(){
                                            public void onClick(View v){
                                                startActivity(new Intent(pilihstatus.this, register_komins.class));
                                                finish();
                                            }
                                        }
        );
    }

}
