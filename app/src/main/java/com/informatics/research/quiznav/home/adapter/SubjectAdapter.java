package com.informatics.research.quiznav.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.home.model.MaterialModel;
import com.informatics.research.quiznav.home.model.SubjectModel;
import com.informatics.research.quiznav.material.MaterialActivity;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {

    private List<SubjectModel> dfSubjectModel;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView subject_list_layout;
        public TextView txt_subject_name, txt_year_of_study, txt_lecturer_name, txt_students_number;

        public MyViewHolder(View view) {
            super(view);
            subject_list_layout = view.findViewById(R.id.subject_list_layout);
            txt_subject_name = view.findViewById(R.id.subject_name);
            txt_year_of_study = view.findViewById(R.id.year_of_study);
            txt_lecturer_name = view.findViewById(R.id.lecturer_name);
            txt_students_number = view.findViewById(R.id.students_number);
        }
    }

    public SubjectAdapter(List<SubjectModel> dfSubjectModel, Activity activity) {
        this.dfSubjectModel = dfSubjectModel;
        this.mActivity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final SubjectModel subjectModel = dfSubjectModel.get(position);

        holder.txt_subject_name.setText(subjectModel.getTitle());
        holder.txt_year_of_study.setText(subjectModel.getYear());
        holder.txt_lecturer_name.setText(subjectModel.getLecturer());
        holder.txt_students_number.setText(subjectModel.getStudentsCount());

        holder.subject_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(mActivity, MaterialActivity.class);
                goDetail.putExtra("Subject Code", subjectModel.getKey());
                goDetail.putExtra("Subject Title", subjectModel.getTitle());
                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dfSubjectModel.size();
    }

}

