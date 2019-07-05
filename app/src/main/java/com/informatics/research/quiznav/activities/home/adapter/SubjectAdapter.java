package com.informatics.research.quiznav.activities.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.database.model.Subjects;
import com.informatics.research.quiznav.activities.materialList.MaterialListActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {

    private ArrayList<Subjects> dfSubjects;
    private HashMap<String, String> tempHistory = new HashMap<>();
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView subject_list_layout;
        public LinearLayout card_view_layout;
        public TextView txt_subject_name, txt_year_of_study, txt_lecturer_name, txt_students_number;

        public MyViewHolder(View view) {
            super(view);
            card_view_layout = view.findViewById(R.id.card_view_layout);
            subject_list_layout = view.findViewById(R.id.subject_list_layout);
            txt_subject_name = view.findViewById(R.id.subject_name);
            txt_year_of_study = view.findViewById(R.id.year_of_study);
            txt_lecturer_name = view.findViewById(R.id.lecturer_name);
            txt_students_number = view.findViewById(R.id.students_number);
        }
    }

    public SubjectAdapter(ArrayList<Subjects> dfSubjects, Activity activity) {
        this.dfSubjects = dfSubjects;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_subject, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Subjects subjects = dfSubjects.get(position);

        holder.txt_subject_name.setText(subjects.getTitle());
        holder.txt_year_of_study.setText(subjects.getYear());
        holder.txt_lecturer_name.setText(subjects.getLecturer());
        holder.txt_students_number.setText(String.valueOf(subjects.getStudentsCount()) + " students");

        holder.card_view_layout.setBackgroundColor(Color.parseColor(subjects.getCard_colour()));
        holder.subject_list_layout.setRadius(20);
        holder.subject_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempHistory.clear();
                tempHistory.put("Subject Code", subjects.getKey());
                tempHistory.put("Color", subjects.getCard_colour());

                Intent goDetail = new Intent(mActivity, MaterialListActivity.class);
                goDetail.putExtra("Subject Title", subjects.getTitle())
                        .putExtra("Lecturer Name", subjects.getLecturer())
                        .putExtra("Materials", subjects.getMaterials())
                        .putExtra("Temp History", tempHistory);
                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dfSubjects.size();
    }

}

