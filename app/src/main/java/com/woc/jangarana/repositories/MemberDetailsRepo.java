package com.woc.jangarana.repositories;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.woc.jangarana.models.Person;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MemberDetailsRepo {

    private static final MemberDetailsRepo instance = new MemberDetailsRepo();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    ProgressDialog progressDialog;
    JSONObject json;

    public static MemberDetailsRepo getInstance() {
        return instance;
    }

    public void detailscreate(Person model, Context context) {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Uploading data. Please Wait");
        progressDialog.show();


        Gson gson = new Gson();
        String params = gson.toJson(model);


        try {
             json = new JSONObject(params);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Please try after sometime.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://jangarana.herokuapp.com/api/form/create";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                json, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                progressDialog.dismiss();
                try {
                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                    message.postValue(response.get("message").toString());
                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.getLocalizedMessage()+"",  Toast.LENGTH_SHORT).show();
                Log.d("error", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences= context.getSharedPreferences("Head SignUp",Context.MODE_PRIVATE);
                String tomken=sharedPreferences.getString("family_head_token","");
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "Bearer "+tomken);
                return map;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }
    
}
