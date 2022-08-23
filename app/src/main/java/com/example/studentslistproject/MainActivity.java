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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /*
    use GSON to automatically convert java to JASON or JSON to jave
     */

    LottieAnimationView imgLoading;
    private static final String TAG = "MainActivityTAG";
    private static final Integer ADD_NEW_STUDENT_RESULT_CODED=1001;
    RecyclerView rvStudents;
    StudentAdaptor adaptorStudent;


    private RetrofitApiService retrofitApiService;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService=new ApiService(this ,TAG);
        retrofitApiService = new RetrofitApiService();

        //loading
        imgLoading=findViewById(R.id.img_main_loading);

        imgLoading.setRepeatCount(Animation.INFINITE);
        //toolbar
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_title);


//        readFromServer(); //Volley
        readFromServerRetrofit(); //Retrofit

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
        apiService.getStudents(new ApiService.GetStudentCallback() {
            @Override
            public void onSuccess(ArrayList<Student> students) {
                imgLoading.setVisibility(View.GONE);

                //recyclerview
                rvStudents=findViewById(R.id.rv_main_students);
                rvStudents.setLayoutManager(new LinearLayoutManager(MainActivity.this ,
                        RecyclerView.VERTICAL , false));
                adaptorStudent=new StudentAdaptor(students);
                rvStudents.setAdapter(adaptorStudent);
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(MainActivity.this, "خطا", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void readFromServerRetrofit()
    {
        retrofitApiService.getStudent(new RetrofitApiService.GetStudentCallBackR() {
            @Override
            public void onSuccess(ArrayList<Student> students) {
                imgLoading.setVisibility(View.GONE);

                //recyclerview
                rvStudents=findViewById(R.id.rv_main_students);
                rvStudents.setLayoutManager(new LinearLayoutManager(MainActivity.this ,
                        RecyclerView.VERTICAL , false));
                adaptorStudent=new StudentAdaptor(students);
                rvStudents.setAdapter(adaptorStudent);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(MainActivity.this, "خطا", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        apiService.cancel();
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