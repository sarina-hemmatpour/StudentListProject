package com.example.studentslistproject;

public class Student {
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
}
