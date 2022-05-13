package com.woc.jangarana.repositories;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.woc.jangarana.models.FamilyHeadSignup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupRepo {

    private static final SignupRepo instance = new SignupRepo();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<String> token = new MutableLiveData<>();
    private final MutableLiveData<FamilyHeadSignup> user = new MutableLiveData<>();
    RequestQueue requestQueue;
    Boolean isRegister = false;


    public static SignupRepo getInstance() {
        return instance;
    }

    public void createUser(FamilyHeadSignup user, Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("email", user.getEmail());
        params.put("password", user.getPassword());

        String url = "https://jangarana.herokuapp.com/api/auth/signup";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());

                try {
                    Toast.makeText(context, response.get("message").toString(), Toast.LENGTH_SHORT).show();
                    token.postValue(response.get("token").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        }) ;
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public void VerifyUser(FamilyHeadSignup otpObject, Context context) {
        Map<String, String> params = new HashMap<>();
        params.put("token", otpObject.getToken());
        params.put("otp", otpObject.getOtp());
        String url = "https://jangrana.herokuapp.com/api/auth/verify_email";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("OTP Repo", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public MutableLiveData<FamilyHeadSignup> getUser() {
        return user;
    }


    public MutableLiveData<String> getToken() {
        return token;
    }


}
