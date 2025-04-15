package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {

    Button callLogin,regBtn;
    TextInputLayout name,username,email,password;
    String fullNameDB, usernameDB, emailDB, passwordDB,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        callLogin = findViewById(R.id.returnLogin_btn);
        regBtn = findViewById(R.id.reg_btn);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullNameDB = name.getEditText().getText().toString();
                usernameDB = username.getEditText().getText().toString();
                emailDB = email.getEditText().getText().toString();
                passwordDB = password.getEditText().getText().toString();

                url = "http://10.0.2.2:9999/clicker/signup?fullname=" + fullNameDB + "&username=" + usernameDB + "&email=" + emailDB+ "&password=" + passwordDB;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //handle response from servlet
                                Log.d("HTTP response",response);
                                if(response.equals("successful")){
                                    Intent intent = new Intent(SignUp.this,Dashboard.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(),"User not registered successfully!",Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //handle error
                                Log.e("HTTP error",error.toString());
                            }
                        });
                queue.add(request);
            }
        });


    }


}