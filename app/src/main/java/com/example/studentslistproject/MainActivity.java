package com.example.studentslistproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Student> students=new ArrayList<>();
    private RequestQueue requestQueue;
    private static final String TAG = "MainActivityTAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readFromServer();

    }

    private void readFromServer()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                "http://expertdevelopers.ir/api/v1/experts/student",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray studentsJSONArray=new JSONArray(response);
                            for (int i = 0; i < 10; i++) {
                                JSONObject studentJO=studentsJSONArray.getJSONObject(i);
                                Student newStudent=new Student(studentJO.getInt("id"),
                                        studentJO.getString("first_name"),
                                        studentJO.getString("last_name"),
                                        studentJO.getString("course"),
                                        studentJO.getInt("score"));
                                students.add(newStudent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "onResponse: "+students);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: ");
                        /*
                        it is not permitted to send requests to hosts without SSL (http)
                        it has to be https
                        so add this line to the application part of manifest file =>
                        android:usesCleartextTraffic="true"
                         */
                    }
                });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}