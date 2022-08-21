package com.example.studentslistproject;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiService {

    private static final String TAG = "ApiService";
    private static RequestQueue requestQueue;
    private static final String BASE_URL ="http://expertdevelopers.ir/api/v1/";
    private String REQUEST_TAG;

    public ApiService(Context context , String requestTag) {
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(context.getApplicationContext());
        }
        REQUEST_TAG=requestTag;
    }

    public void saveStudents(String firstName , String lastname , String course , int score , SaveStudentCallback callback)
    {

        //creating JSONObject to send
        JSONObject joStudent=new JSONObject();
        try {
            joStudent.put("first_name" , firstName);
            joStudent.put("last_name" ,lastname );
            joStudent.put("course" , course);
            joStudent.put("score" , score);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //posting
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST,
                BASE_URL +"experts/student",
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


                            //result cal back
                            // chon tu ye interface e digas nemitunim student ro return konim
                            callback.onSuccess(newStudent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "onErrorResponse: " + error.toString());
                        callback.onError(error);
                    }
                });

        request.setTag(REQUEST_TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(10000 , 3 , 2 ));
        //sending
        requestQueue.add(request);
    }

    void getStudents(GetStudentCallback callback)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                BASE_URL +"experts/student",
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

                            //***************
                            callback.onSuccess(students);


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
                        callback.onError(error);
                    }
                });

        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000 , 3 , 2));
        stringRequest.setTag(REQUEST_TAG);
    }

    interface SaveStudentCallback
    {
        void onSuccess(Student student);
        void onError(VolleyError error);
    }
    interface GetStudentCallback{
        void onSuccess(ArrayList<Student> students);
        void onError(VolleyError error);
    }
    public void cancel()
    {
        if (requestQueue!=null)
        {
            requestQueue.cancelAll(TAG);
        }
    }
}
