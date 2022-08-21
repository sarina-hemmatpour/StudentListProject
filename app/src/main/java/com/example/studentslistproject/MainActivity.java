package com.example.studentslistproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private RequestQueue requestQueue;
    LottieAnimationView imgLoading;
    private static final String TAG = "MainActivityTAG";
    private static final Integer ADD_NEW_STUDENT_RESULT_CODED=1001;
    RecyclerView rvStudents;
    StudentAdaptor adaptorStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loading
        imgLoading=findViewById(R.id.img_main_loading);

        imgLoading.setRepeatCount(Animation.INFINITE);
        //toolbar
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_title);


        readFromServer();

        ExtendedFloatingActionButton fabAdd=findViewById(R.id.fab_main_addStudent);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddNewStudentFormActivity.class) ,ADD_NEW_STUDENT_RESULT_CODED );
            }
        });

    }

    private void readFromServer()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                "http://expertdevelopers.ir/api/v1/experts/student",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<Student> students=new ArrayList<>();
                        try {

                            JSONArray studentsJSONArray=new JSONArray(response);
                            for (int i = 0; i < 20; i++) {
                                JSONObject studentJO=studentsJSONArray.getJSONObject(i);
                                Student newStudent=new Student(studentJO.getInt("id"),
                                        studentJO.getString("first_name"),
                                        studentJO.getString("last_name"),
                                        studentJO.getString("course"),
                                        studentJO.getInt("score"));
                                students.add(newStudent);
                            }

                            imgLoading.setVisibility(View.GONE);

                            //recyclerview
                            rvStudents=findViewById(R.id.rv_main_students);
                            rvStudents.setLayoutManager(new LinearLayoutManager(MainActivity.this ,
                                    RecyclerView.VERTICAL , false));
                            adaptorStudent=new StudentAdaptor(students);
                            rvStudents.setAdapter(adaptorStudent);

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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000 , 3 , 2));
        stringRequest.setTag(TAG);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        requestQueue.cancelAll(TAG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if(requestCode==ADD_NEW_STUDENT_RESULT_CODED &&
            resultCode==RESULT_OK && adaptorStudent!=null &&data!=null){

            Student newStudent=data.getParcelableExtra("student");
            adaptorStudent.addNewStudent(newStudent);
            rvStudents.smoothScrollToPosition(0);

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}