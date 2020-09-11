package com.example.syncvj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class lookcloseIntercomm extends AppCompatActivity {
    int ADMIN;
    String department_select;
    Button editCurrent;
    TextView lookclose1;
    TextView lookclose2;
    TextView lookclose3;
    TextView lookclose4;
    TextView lookclose5;
    TextView lookclose6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ADMIN = getIntent().getIntExtra("ADMIN",0);
        department_select = getIntent().getStringExtra("DEPT");
        final String name = getIntent().getStringExtra("Name");
        final String[] post = {getIntent().getStringExtra("Post")};
        final Long int_comm = getIntent().getLongExtra("Int_comm",0);
        final String department = getIntent().getStringExtra("Department");
        setContentView(R.layout.activity_lookclose_intercomm);
        editCurrent = (Button) findViewById(R.id.editCurrent);
        lookclose1 = findViewById(R.id.lookView1_intercomm);
        lookclose1.setText(name);
        lookclose2 = findViewById(R.id.lookView2_intercomm);
        lookclose2.setText(post[0]);
        lookclose3 = findViewById(R.id.lookView3_intercomm);
        lookclose3.setText(String.valueOf(int_comm));
        lookclose4 = findViewById(R.id.lookView4_intercomm);
        lookclose4.setText(department);
        if(department_select.equals("Link")){
            lookclose3.setVisibility(View.GONE);
            lookclose4.setVisibility(View.GONE);
            lookclose2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!post[0].startsWith("http://") && !post[0].startsWith("https://"))
                        post[0] = "http://" + post[0];
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(post[0]));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.android.chrome");
                    startActivity(intent);
                }
            });
        }
        if(ADMIN == 0){
            editCurrent.setVisibility(View.GONE);
        }


   /*
        */

        if(ADMIN==1){
            editCurrent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setContentView(R.layout.update_intercomm);
                    final EditText update1 = findViewById(R.id.updateView1_intercomm);
                    update1.setText(name);
                    final EditText update2 = findViewById(R.id.updatetView2_intercomm);
                    update2.setText(post[0]);
                    final EditText update3 = findViewById(R.id.updateView3_intercomm);
                    update3.setText(String.valueOf(int_comm));
                    final EditText update4 = findViewById(R.id.updateView4_intercomm);
                    update4.setText(department);
                    if(department_select.equals("Link")){
                        update4.setVisibility(View.GONE);
                        update3.setVisibility(View.GONE);
                    }
                    Button update_intercomm = findViewById(R.id.update_intercomm);
                    Button delete_intercomm = findViewById(R.id.delete_intercomm);
                    delete_intercomm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DBHelper dbHelper = new DBHelper(lookcloseIntercomm.this);
                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                            dbHelper.deleteoneLocalDatabase_intercomm(name, int_comm, department,database);
                            dbHelper.close();
                            NetworkMonitorIntercom networkMonitor = new NetworkMonitorIntercom();
                            networkMonitor.deloneonline_intercomm(name, int_comm, getApplicationContext());
                            Toast.makeText(lookcloseIntercomm.this, "Deleted", Toast.LENGTH_SHORT).show();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(lookcloseIntercomm.this,showActivityIntercom.class);
                            intent.putExtra("ADMIN",ADMIN);
                            intent.putExtra("DEPT",department_select);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    });
                    update_intercomm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DBHelper dbHelper = new DBHelper(lookcloseIntercomm.this);
                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                            String namenew = update1.getText().toString();
                            String postnew = update2.getText().toString();
                            Long int_commnew = Long.parseLong(update3.getText().toString());
                            String departmentnew = update4.getText().toString();
                            dbHelper.updateoneLocalDatabase_intercomm(name, int_comm, namenew, postnew, int_commnew, departmentnew, database);
                            dbHelper.close();
                            NetworkMonitorIntercom networkMonitor = new NetworkMonitorIntercom();
                            networkMonitor.updateoneonline_intercomm(name, int_comm, namenew, postnew, int_commnew,  departmentnew, getApplicationContext());
                            Toast.makeText(lookcloseIntercomm.this, "Updated", Toast.LENGTH_SHORT).show();
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(lookcloseIntercomm.this,showActivityIntercom.class);
                            intent.putExtra("ADMIN",ADMIN);
                            intent.putExtra("DEPT",department_select);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);


                        }


                    });
                }
            });

        }
    }
}
