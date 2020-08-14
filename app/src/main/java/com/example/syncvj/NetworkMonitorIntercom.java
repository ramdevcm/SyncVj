package com.example.syncvj;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class NetworkMonitorIntercom {

    public void deloneonline_intercomm(final String name, final Long int_comm, Context context){
        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_DELONE_INTERCOM, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if(Response.equals("OK")){
                            Log.i(TAG, "onResponse: deleted from server");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Name", name);
                    params.put("Int_comm", String.valueOf(int_comm));
                    return params;
                }
            };
            MySingleton.getInstance(context).adddtoRequestQueue(stringRequest);

        }

    }

    public void updateoneonline_intercomm(final String name, final Long int_comm, final String namenew, final String postnew, final Long int_commnew, final String departmentnew, Context context){
        if(checkNetworkConnection(context)){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DBsync.SERVER_URL_UPDATEONE_INTERCOM, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("response");
                        if(Response.equals("OK")){
                            Log.i(TAG, "onResponse: updated server");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Name", name);
                    params.put("Int_comm", String.valueOf(int_comm));
                    params.put("Namenew", namenew);
                    params.put("Postnew", postnew);
                    params.put("Int_commnew", String.valueOf(int_commnew));
                    params.put("Departmentnew", departmentnew);
                    return params;
                }
            };
            MySingleton.getInstance(context).adddtoRequestQueue(stringRequest);

        }


    }

    public boolean checkNetworkConnection(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}
