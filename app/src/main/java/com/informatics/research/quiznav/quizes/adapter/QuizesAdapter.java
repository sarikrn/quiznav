package com.informatics.research.quiznav.quizes.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.quizes.QuizActivity;
import com.informatics.research.quiznav.quizes.model.Quizes;

import java.util.ArrayList;

public class QuizesAdapter extends RecyclerView.Adapter<QuizesAdapter.MyViewHolder> {

    private ArrayList<Quizes> dfQuizes;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout quizes_list_layout;
        public TextView txt_quiz_title, txt_due_date, txt_result_average, txt_score_averagee;
        public ProgressBar progress_bar_number_of_quizes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            quizes_list_layout = itemView.findViewById(R.id.quizes_list_layout);
            txt_quiz_title = itemView.findViewById(R.id.quiz_title);
            txt_due_date = itemView.findViewById(R.id.due_date);
            txt_result_average = itemView.findViewById(R.id.result_average);
            txt_score_averagee = itemView.findViewById(R.id.score_averagee);
            progress_bar_number_of_quizes = itemView.findViewById(R.id.progress_bar_number_of_quizes);
        }
    }

    public QuizesAdapter(ArrayList<Quizes> dfQuizes, Activity mActivity) {
        this.dfQuizes = dfQuizes;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_quizes, parent, false);

        return new QuizesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Quizes quizes = dfQuizes.get(position);

        holder.txt_quiz_title.setText(quizes.getTitle());
        holder.txt_due_date.setText(String.valueOf(quizes.getDue_to()));
        holder.txt_score_averagee.setText(quizes.getScore_average());
        holder.progress_bar_number_of_quizes.setProgress(89);

        holder.quizes_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goDetail = new Intent(mActivity, QuizActivity.class);
                goDetail.putExtra("Quiz Code", quizes.getKey())
                        .putExtra("Questions", quizes.getQuestions());
                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dfQuizes.size();
    }
}
