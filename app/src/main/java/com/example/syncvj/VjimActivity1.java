package com.example.syncvj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class VjimActivity1 extends AppCompatActivity {
     Button staff , intercom;
     int ADMIN;
    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vjim1);
            ADMIN = getIntent().getIntExtra("ADMIN",0);
            staff = (Button) findViewById(R.id.vjim_staff);
            intercom=(Button)findViewById(R.id.vjim_intercom);

            staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjimActivity1.this, showActivity.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT","VJIM");
                startActivity(intent);
            }
            });

        intercom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjimActivity1.this, showActivityIntercom.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT","VJIM");
                startActivity(intent);
            }
        });
        }
}
