package com.example.syncvj;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class VjimActivity1 extends AppCompatActivity {
     Button staff , intercom;
    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_vjim1);
            staff = (Button) findViewById(R.id.vjim_staff);
            intercom=(Button)findViewById(R.id.vjim_intercom);

     /* staff_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VjecActivity1.this, VjecDeptActivity1.class);
                startActivity(intent);
            }
        });   */
    }
}