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

public class Dashboard extends AppCompatActivity {

    Button logout_btn,enter_btn;

    TextInputLayout code_input;

    String url,entered_Code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logout_btn = (Button) findViewById(R.id.logout);
        enter_btn = (Button) findViewById(R.id.enter_RM);
        code_input = findViewById(R.id.code);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
            }
        });

        enter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entered_Code = code_input.getEditText().getText().toString();
                url = "http://10.0.2.2:9999/clicker/validateCode?code=" + entered_Code;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request1 = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //handle response from servlet
                                if(response.equals("Correct")) {
                                    Intent intent = new Intent(Dashboard.this,MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(),"Wrong Code!",Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //handle error
                            }
                        });
                queue.add(request1);
            }
        });

    }
}