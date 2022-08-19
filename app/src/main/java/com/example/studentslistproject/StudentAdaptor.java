package com.example.studentslistproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentAdaptor extends RecyclerView.Adapter<StudentAdaptor.StudentViewHolder> {

    private ArrayList<Student> students;

    public StudentAdaptor(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{

        TextView tvFullName;
        TextView tvScore;
        TextView tvCourse;
        TextView tvFirstChar;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFullName=itemView.findViewById(R.id.tv_student_fullName);
            tvCourse=itemView.findViewById(R.id.tv_student_course);
            tvScore=itemView.findViewById(R.id.tv_student_score);
            tvFirstChar=itemView.findViewById(R.id.tv_student_firstChar);
        }

        public void bind(Student student)
        {
            tvFirstChar.setText(student.getFirst_name().substring(0,1));
            tvScore.setText(String.valueOf(student.getScore()));
            tvCourse.setText(student.getCourse());
            tvFullName.setText(student.getFirst_name().trim() + " " + student.getLast_name().trim());
        }
    }
}
