package com.example.studentslistproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class AddNewStudentFormActivity extends AppCompatActivity {

    private static final String TAG = "FormActivity";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newstudent_form);

        requestQueue= Volley.newRequestQueue(this);

        Toolbar toolbar=findViewById(R.id.toolbar_form);
        setSupportActionBar(toolbar);

        //back btn
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setting icon for back btn (cause its color is black)
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //posting data
        TextInputEditText etFirstName=findViewById(R.id.et_form_firstName);
        TextInputEditText etLastName=findViewById(R.id.et_form_lastName);
        TextInputEditText etCourse=findViewById(R.id.et_form_Course);
        TextInputEditText etScore=findViewById(R.id.et_form_score);

        View fabSave=findViewById(R.id.fab_form_save);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etFirstName.length()>0 && etLastName.length()>0
                        && etCourse.length()>0 && etScore.length()>0)
                {
                    //creating JSONObject to send
                    JSONObject joStudent=new JSONObject();
                    try {
                        joStudent.put("first_name" , etFirstName.getText().toString().trim());
                        joStudent.put("last_name" , etLastName.getText().toString().trim());
                        joStudent.put("course" , etCourse.getText().toString().trim());
                        joStudent.put("score" , etScore.getText().toString().trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //posting
                    JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,
                            "http://expertdevelopers.ir/api/v1/experts/student",
                            joStudent,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.i(TAG, "onResponse: ");

                                    try {
                                        Student newStudent=new Student(response.getInt("id") ,
                                                response.getString("first_name") ,
                                                response.getString("last_name"),
                                                response.getString("course") ,
                                                response.getInt("score"));
                                        Intent intent=new Intent();
                                        intent.putExtra("student" , newStudent);

                                        setResult(RESULT_OK , intent);
                                        finish();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.i(TAG, "onErrorResponse: " + error.toString());
                                }
                            });

                    //sending
                    requestQueue.add(request);
                }


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}