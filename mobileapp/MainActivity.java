package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import kotlin.OptionalExpectation;

public class MainActivity extends AppCompatActivity {

    Button optionA, optionB, optionC, optionD,logout;
    String choice,url1,url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        optionA = (Button) findViewById(R.id.btnA);
        optionB = (Button) findViewById(R.id.btnB);
        optionC = (Button) findViewById(R.id.btnC);
        optionD = (Button) findViewById(R.id.btnD);
        logout =  (Button) findViewById(R.id.logout_btn);

        //check the status of the duration first start/stop, if its start enable the button if not disable the button
        optionA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                choice = "A";
                url1 = "http://10.0.2.2:9999/clicker/checkStatus";
                url2 = "http://10.0.2.2:9999/clicker/select?choice=" + choice;

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request1 = new StringRequest(Request.Method.GET, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //handle response from servlet
                                if (response.equals("Start")){
                                    StringRequest request2 = new StringRequest(Request.Method.GET, url2,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    //handle response from servlet
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //handle error
                                                }
                                            });
                                    queue.add(request2);
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

        optionB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                choice = "B";
                url1 = "http://10.0.2.2:9999/clicker/checkStatus";
                url2 = "http://10.0.2.2:9999/clicker/select?choice=" + choice;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request1 = new StringRequest(Request.Method.GET, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //handle response from servlet
                                if (response.equals("Start")){
                                    StringRequest request2 = new StringRequest(Request.Method.GET, url2,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    //handle response from servlet
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //handle error
                                                }
                                            });
                                    queue.add(request2);
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

        optionC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                choice = "C";
                url1 = "http://10.0.2.2:9999/clicker/checkStatus";
                url2 = "http://10.0.2.2:9999/clicker/select?choice=" + choice;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.GET, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //handle response from servlet
                                if (response.equals("Start")){
                                    StringRequest request2 = new StringRequest(Request.Method.GET, url2,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    //handle response from servlet
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //handle error
                                                }
                                            });
                                    queue.add(request2);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //handle error
                            }
                        });
                queue.add(request);
            }
        });

        optionD.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                choice = "D";
                url1 = "http://10.0.2.2:9999/clicker/checkStatus";
                url2 = "http://10.0.2.2:9999/clicker/select?choice=" + choice;
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(Request.Method.GET, url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //handle response from servlet
                                if (response.equals("Start")){
                                    StringRequest request2 = new StringRequest(Request.Method.GET, url2,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    //handle response from servlet
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    //handle error
                                                }
                                            });
                                    queue.add(request2);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //handle error
                            }
                        });
                queue.add(request);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

    }
}