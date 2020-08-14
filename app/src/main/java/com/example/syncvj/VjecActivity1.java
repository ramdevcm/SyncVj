package com.example.syncvj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VjecActivity1 extends AppCompatActivity {
    Button dept_button,int_comm_button;
    int ADMIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ADMIN = getIntent().getIntExtra("ADMIN",0);
        setContentView(R.layout.activity_vjec1);

        dept_button = (Button) findViewById(R.id.vjecDeptButton);
        int_comm_button = (Button) findViewById(R.id.vjecIntercomButton);
        dept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, VjecDeptActivity1.class);
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });

        int_comm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, showActivityIntercom.class);
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });
    }
}