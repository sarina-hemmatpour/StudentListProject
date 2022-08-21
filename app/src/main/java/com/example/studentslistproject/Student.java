package com.example.studentslistproject;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Student implements Parcelable {
    /*
    agar esme moteqayer ha ba api server yeki nabud az in estefade kon =>
    @SerializedName("id")
     */

    private int id;
    private String first_name;
    private String last_name;
    private String course;
    private int score;

    public Student(int id, String first_name, String last_name, String course, int score) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.course = course;
        this.score = score;
    }



    public Student() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.course);
        dest.writeInt(this.score);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readInt();
        this.first_name = source.readString();
        this.last_name = source.readString();
        this.course = source.readString();
        this.score = source.readInt();
    }

    protected Student(Parcel in) {
        this.id = in.readInt();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.course = in.readString();
        this.score = in.readInt();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
