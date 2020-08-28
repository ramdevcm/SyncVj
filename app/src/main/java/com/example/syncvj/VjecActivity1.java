package com.example.syncvj;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class VjecActivity1 extends AppCompatActivity {
    Button dept_button,int_comm_button,adminstrator_button,general_button,frequents_button;
    int ADMIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ADMIN = getIntent().getIntExtra("ADMIN",0);
        setContentView(R.layout.activity_vjec1);

        dept_button = (Button) findViewById(R.id.vjecDeptButton);
        frequents_button = (Button) findViewById(R.id.vjecUsefulLinksButton);
        general_button = (Button) findViewById(R.id.vjecGeneralFacilitiesButton);
        adminstrator_button = (Button) findViewById(R.id.vjecAdministrationButton);
        int_comm_button = (Button) findViewById(R.id.vjecIntercomButton);
        dept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, VjecDeptActivity1.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT1","none");
                intent.putExtra("DEPT2","none");
                intent.putExtra("DEPT3","none");
                intent.putExtra("DEPT4","none");
                startActivity(intent);
            }
        });

        int_comm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, showActivityIntercom.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT","none");
                startActivity(intent);
            }
        });

        adminstrator_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, showActivity.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT","Management");
                intent.putExtra("DEPT1","none");
                intent.putExtra("DEPT2","none");
                intent.putExtra("DEPT3","none");
                intent.putExtra("DEPT4","none");
                startActivity(intent);
            }
        });

        general_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, showActivity.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT","Library");
                intent.putExtra("DEPT1","Accounts");
                intent.putExtra("DEPT2","Office");
                intent.putExtra("DEPT3","Placement");
                intent.putExtra("DEPT4","Maintenance");
                startActivity(intent);
            }
        });

        frequents_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, showActivityIntercom.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT","Link");
                startActivity(intent);
            }
        });
    }
}
