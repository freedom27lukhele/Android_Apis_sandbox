package com.example.connectingapptoweb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    static String url = "https://www.google.com";
    String apiUrl = "https://jsonplaceholder.typicode.com/todos";

    String apiObjectUrl = "https://jsonplaceholder.typicode.com/todos/1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //we will be using volley libraries to get json apis for our code

        setContentView(R.layout.activity_main);

      //  queue = Volley.newRequestQueue(this);
        queue = MySingleton.getInstance(this.getApplicationContext())
                .getRequestQueue();
        TextView text = findViewById(R.id.textViewJson);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiObjectUrl, null,
                response -> {
                    try {
                        text.setText(response.getString("title"));
                        Log.d("JsonObject", "onCreate : " + response.getString("title"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.d("JsonObject", "ErrorResponse"));

           queue.add(jsonObjectRequest);

        //json array request for a json list or recyclerview

       // jsonArrayRequest();
        //create a request for string

       // getString(queue);
    }

    private void jsonArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.d("jsonObject", "JSON: " + jsonObject.getString("title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                error -> Log.d("jsonObject", "unsuccessful"));

       queue.add(jsonArrayRequest);
    }

    private static void getString(RequestQueue queue) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {

            //display Contents of our Url
            Log.d("Main", "OnCreate: " + response.substring(0, 500));

        }, error -> {
            Log.d("Main", "error to get info");

        });

        queue.add(stringRequest);
    }
}