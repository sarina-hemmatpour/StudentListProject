package com.example.studentslistproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newstudent_form);

        //api
        apiService=new ApiService(this , TAG);

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
                    fabSave.setEnabled(false);
                    apiService.saveStudents(etFirstName.getText().toString().trim(),
                            etLastName.getText().toString().trim(),
                            etCourse.getText().toString().trim(),
                            Integer.parseInt(etScore.getText().toString().trim()),
                            new ApiService.SaveStudentCallback() {
                                @Override
                                public void onSuccess(Student student) {
                                    Intent intent=new Intent();
                                    intent.putExtra("student" , student);

                                    setResult(RESULT_OK , intent);
                                    finish();
                                }

                                @Override
                                public void onError(VolleyError error) {
                                    Toast.makeText(AddNewStudentFormActivity.this, "خطا", Toast.LENGTH_SHORT).show();
                                    fabSave.setEnabled(true);
                                }
                            });

                }
                else {
                    Toast.makeText(AddNewStudentFormActivity.this, "Complete all the fields", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.cancel();
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