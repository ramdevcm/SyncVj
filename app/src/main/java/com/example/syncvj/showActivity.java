package com.example.syncvj;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class showActivity extends AppCompatActivity {

    ListView listUser;
    EditText Name;
    EditText Post;
    EditText Number;
    EditText Email;
    TextView Department;
    String department_select;
    Button addDeptStaffBt;
    int ADMIN;
    ListAdapter adapter;
    ArrayList<DBcontrol> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userview);
        ADMIN = getIntent().getIntExtra("ADMIN",0);
        addDeptStaffBt = (Button) findViewById(R.id.addNewDepartmentStaff);
        if(ADMIN == 0){
            addDeptStaffBt.setVisibility(View.GONE);
        }
        department_select = getIntent().getStringExtra("DEPT");
        listUser = (ListView) findViewById(R.id.listUser);
        arrayList = new ArrayList<DBcontrol>();
        adapter = new ListAdapter(getApplicationContext(),R.layout.staff_view,arrayList);
        listUser.setAdapter(adapter);
        readFromLocalStorage();
        addDeptStaffBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.admin_departmentactivity);
                Name = (EditText) findViewById(R.id.textView1);
                Post = (EditText) findViewById(R.id.textView2);
                Number = (EditText) findViewById(R.id.textView3);
                Email = (EditText) findViewById(R.id.textView4);
                Department = findViewById(R.id.textView5);
                Department.setText(department_select);
            }
        });
        listUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String name = arrayList.get(i).getName();
                String post = arrayList.get(i).getPost();
                final Long number = arrayList.get(i).getNumber();
                String email = arrayList.get(i).getEmail();
                String department = arrayList.get(i).getDepartment();
                Intent intent = new Intent(showActivity.this,lookclose.class);
                intent.putExtra("Name",name);
                intent.putExtra("Post",post);
                intent.putExtra("Number",number);
                intent.putExtra("Email",email);
                intent.putExtra("Department",department);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT",department_select);
                startActivity(intent);


            }
        });
    }

    public void addNewDeptStaff(View view){

        String name = Name.getText().toString();
        String post = Post.getText().toString();
        String number = (Number.getText().toString());
        String email = Email.getText().toString();
        String department = department_select;
        if(name.isEmpty() || post.isEmpty() || number.isEmpty() || email.isEmpty() || department.isEmpty()){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();

        }
        else{
            try {
                long num = Long.parseLong(number);
                saveToAppServer(name,post, num,email,department);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Name.setText("");
                Post.setText("");
                Number.setText("");
                Email.setText("");
                Department.setText("");
                Intent intent = new Intent(showActivity.this,showActivity.class);
                intent.putExtra("ADMIN",ADMIN);
                intent.putExtra("DEPT",department_select);
                startActivity(intent);

            } catch (NumberFormatException nfe) {
                System.out.println("Please enter a valid number");
            }
            }


    }






    private void readFromLocalStorage(){
        arrayList.clear();
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database =  dbHelper.getReadableDatabase();

        Cursor cursor = dbHelper.readFromLocalDatabase(database,department_select);

        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex(DBsync.NAME));
            String post = cursor.getString(cursor.getColumnIndex(DBsync.POST));
            Long number = cursor.getLong(cursor.getColumnIndex(DBsync.NUMBER));
            String email = cursor.getString(cursor.getColumnIndex(DBsync.EMAIL));
            String department = cursor.getString(cursor.getColumnIndex(DBsync.DEPARTMENT));
            int sync_status = cursor.getInt(cursor.getColumnIndex(DBsync.SYNC_STATUS));
            arrayList.add(new DBcontrol(name,post,number,email,department,sync_status));
        }


        adapter.notifyDataSetChanged();
        cursor.close();
        dbHelper.close();

    }

    private void saveToAppServer(final String name, final String post, final Long number, final String email, final String department){


        if(true){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_SYNC, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject =new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if(Response.equals("OK")){
                            saveToLocalDatabase(name,post,number,email,department,DBsync.SYNC_STATUS_OK);
                        }
                        else{
                            saveToLocalDatabase(name,post,number,email,department,DBsync.SYNC_STATUS_FAILED);
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    saveToLocalDatabase(name,post,number,email,department,DBsync.SYNC_STATUS_FAILED);
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("Name",name);
                    params.put("Post",post);
                    params.put("Number",String.valueOf(number));
                    params.put("Email",email);
                    params.put("Department",department);

                    return params;
                }
            };
            MySingleton.getInstance(showActivity.this).adddtoRequestQueue(stringRequest);
            readFromLocalStorage();


        }
        else{
            saveToLocalDatabase(name,post,number,email,department,DBsync.SYNC_STATUS_FAILED);

        }


    }

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

    public void saveToLocalDatabase(String name,String post,Long number,String email,String department,int sync_status){
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.saveToLocalDatabase(name,post,number,email,department,sync_status,database);
        readFromLocalStorage();
        dbHelper.close();
    }

    public void syncStaff(View view){
        //NetworkMonitor networkMonitor = new NetworkMonitor();
        //networkMonitor.syncAll(getApplicationContext());
    }
}
