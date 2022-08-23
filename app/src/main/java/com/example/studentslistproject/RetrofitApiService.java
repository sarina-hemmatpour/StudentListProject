package com.example.studentslistproject;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RetrofitApiService {

    private static RetrofitApi retrofitApi;
    private static final String BASE_URL="http://expertdevelopers.ir/api/v1/";

    public RetrofitApiService()
    {
        if (retrofitApi ==null)
        {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitApi=retrofit.create(RetrofitApi.class);
        }
    }

    public void getStudent(GetStudentCallBackR callBack)
    {
        retrofitApi.getStudent().enqueue(new Callback<ArrayList<Student>>() {
            @Override
            public void onResponse(Call<ArrayList<Student>> call, Response<ArrayList<Student>> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Student>> call, Throwable t) {
                callBack.onError(new Exception(t));
            }
        });
    }

    public void saveStudent(String firstName , String lastname , String course , int score ,
                            SaveStudentCallbackR callback)
    {
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("first_name" , firstName);
        jsonObject.addProperty("last_name" , lastname);
        jsonObject.addProperty("course" , course);
        jsonObject.addProperty("score" , score);


        retrofitApi.postStudent(jsonObject).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                callback.onError(new Exception(t));
            }
        });
    }

    interface SaveStudentCallbackR
    {
        void onSuccess(Student student);
        void onError(Exception error);
    }

    public interface GetStudentCallBackR
    {
        void onSuccess(ArrayList<Student> students);
        void onError(Exception e);
    }



    public interface RetrofitApi{

        @GET("experts/student")
        Call<ArrayList<Student>> getStudent();

        //GSON bayad bashe ****
        @POST("experts/student")
        Call<Student> postStudent(@Body JsonObject body);
    }


}
