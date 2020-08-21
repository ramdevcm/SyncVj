package com.example.syncvj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VjecDeptActivity1 extends AppCompatActivity {
    int ADMIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vjec_dept1);
        ADMIN = getIntent().getIntExtra("ADMIN",0);
        Button CSE_button = findViewById(R.id.CSEButton);
        Button EEE_button = findViewById(R.id.EEEButton);
        Button ECE_button = findViewById(R.id.ECEButton);
        Button IE_button = findViewById(R.id.IEButton);
        Button CE_button = findViewById(R.id.CEButton);
        Button ME_button = findViewById(R.id.MEButton);
        Button ASH_button = findViewById(R.id.ASHButton);

        CSE_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecDeptActivity1.this, showActivity.class);
                intent.putExtra("DEPT","CSE");
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });

        EEE_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecDeptActivity1.this, showActivity.class);
                intent.putExtra("DEPT","EEE");
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });

        ECE_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecDeptActivity1.this, showActivity.class);
                intent.putExtra("DEPT","EC");
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });

        IE_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecDeptActivity1.this, showActivity.class);
                intent.putExtra("DEPT","AEI");
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });

        CE_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecDeptActivity1.this, showActivity.class);
                intent.putExtra("DEPT","CE");
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });

        ME_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecDeptActivity1.this, showActivity.class);
                intent.putExtra("DEPT","ME");
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });

        ASH_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecDeptActivity1.this, showActivity.class);
                intent.putExtra("DEPT","ASH");
                intent.putExtra("ADMIN",ADMIN);
                startActivity(intent);
            }
        });
    }
}