package com.woc.jangarana.authentication.staff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.woc.jangarana.R;
import com.woc.jangarana.staff.StaffDashboardActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StaffLoginActivity extends AppCompatActivity {


    RequestQueue requestQueue;
    TextView staffId, password;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        staffId = findViewById(R.id.staffID);
        password = findViewById(R.id.staffPassword);
        
        progressBar=findViewById(R.id.progressBarStaffLogin);

        AppCompatButton button = findViewById(R.id.staff_login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = staffId.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(id)){
                    staffId.setError("Enter Id");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Enter password");
                }

                progressBar.setVisibility(View.VISIBLE);
                userSignIn(id, pass);
            }
        });



    }

    public void userSignIn(String email,String password) {

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);

        String url = "https://jangarana.herokuapp.com/api/staff-auth/signin";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {

                    SharedPreferences sharedPreferences= getSharedPreferences("Staff Login Details",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("token",response.get("token").toString());
                    JSONObject details=(JSONObject) response.get("staff");
                    editor.putString("email",details.get("email").toString());
                    editor.commit();

                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(StaffLoginActivity.this, StaffDashboardActivity.class));
                    finish();

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(StaffLoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(StaffLoginActivity.this, "Client Error", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
