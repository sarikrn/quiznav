package com.informatics.research.quiznav.activities.quizes.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.activities.quizes.QuizActivity;
import com.informatics.research.quiznav.database.model.Quizes;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizesAdapter extends RecyclerView.Adapter<QuizesAdapter.MyViewHolder> {

    private HashMap<String, HashMap<String, String>> dfQuizesResult;
    private ArrayList<Quizes> dfQuizes;
    private HashMap<String, String> tempHistory;
    private String quiz_status;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout quizes_list_layout;
        public TextView txt_quiz_title, txt_due_date, txt_result_average, txt_score_average, txt_remidial_count;
        public ProgressBar progress_bar_doing_question;
        public Button btn_take_remidial;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            quizes_list_layout = itemView.findViewById(R.id.quizes_list_layout);
            txt_quiz_title = itemView.findViewById(R.id.quiz_title);
            txt_due_date = itemView.findViewById(R.id.due_date);
            txt_result_average = itemView.findViewById(R.id.result_average);
            txt_score_average = itemView.findViewById(R.id.score_average);
            txt_remidial_count = itemView.findViewById(R.id.remidial_count);
            btn_take_remidial = itemView.findViewById(R.id.take_remidial);
            progress_bar_doing_question = itemView.findViewById(R.id.progress_bar_doing_question);
        }
    }

    public QuizesAdapter(ArrayList<Quizes> dfQuizes, Activity mActivity, HashMap<String, String> tempHistory, HashMap<String, HashMap<String, String>> dfQuizesResult, String quiz_status) {
        this.dfQuizes = dfQuizes;
        this.mActivity = mActivity;
        this.tempHistory = tempHistory;
        this.dfQuizesResult = dfQuizesResult;
        this.quiz_status = quiz_status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_quizes, parent, false);

        return new QuizesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Quizes quizes = dfQuizes.get(position);

        int remidial_chance = Integer.parseInt(quizes.getChance());
        int trying_count = 0;
        int average = Integer.parseInt(quizes.getScore_average());

        holder.txt_quiz_title.setText(quizes.getTitle());
        holder.txt_due_date.setText(String.valueOf(quizes.getDue_to()));
        holder.txt_score_average.setText(String.valueOf(average));
        holder.progress_bar_doing_question.setProgress(89);

        // status = true : When dfQuizesResult is not empty
        if (!(quiz_status.equalsIgnoreCase("todo"))) {
            int result = 0;

            for (HashMap.Entry<String, String> entry : dfQuizesResult.get(quizes.getQuiz_code()).entrySet()) {
                switch (entry.getKey()) {
                    case "quiz_status":
                        String status = entry.getValue();
                        if (status.equalsIgnoreCase("doing"))
                            holder.btn_take_remidial.setVisibility(View.GONE);
                        else{
                            holder.btn_take_remidial.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    tempHistory.put("Quiz Code", quizes.getQuiz_code());
                                    tempHistory.put("Quiz Status", "doing");

                                    Intent goDetail = new Intent(mActivity, QuizActivity.class);
                                    goDetail.putExtra("Questions", quizes.getQuestions())
                                            .putExtra("Temp History", tempHistory);
                                    mActivity.startActivity(goDetail);
                                }
                            });
                        }
                        break;
                    case "scores":
                        result = Integer.parseInt(entry.getValue());
                        holder.txt_result_average.setText(String.valueOf(result));

                        //percabangan untuk perbedaan remidi
                        if(result < average){
                            holder.txt_result_average.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorDanger));
                        }else{
                            holder.txt_result_average.setTextColor((ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPassed)));
                        }

                        break;
                    case "trying_count":
                        trying_count = Integer.parseInt(entry.getValue());

                        String remidial_status = "";
                        if(remidial_chance - trying_count > 0) {
                            remidial_status = "Jumlah Kesempatan Remidial : " + String.valueOf(remidial_chance - trying_count);
                            holder.txt_remidial_count.setTextColor(Color.BLACK);
                        }else{
                            remidial_status = "Kesempatan HABIS";
                            holder.txt_remidial_count.setTextColor(Color.RED);
                            holder.btn_take_remidial.setVisibility(View.GONE);
                        }

                        holder.txt_remidial_count.setText(remidial_status);
                        break;
                }
            }
        } else{
            holder.btn_take_remidial.setVisibility(View.GONE);
            holder.txt_result_average.setText("0");
            holder.txt_result_average.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.textColorPrimary));

            String remidial_status = "";
            remidial_status = "Jumlah Kesempatan Remidial : " + String.valueOf(remidial_chance);
            holder.txt_remidial_count.setText(remidial_status);
        }

        holder.quizes_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempHistory.put("Quiz Code", quizes.getQuiz_code());

                Intent goDetail = new Intent(mActivity, QuizActivity.class);
                goDetail.putExtra("Questions", quizes.getQuestions())
                        .putExtra("Temp History", tempHistory);
                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dfQuizes.size();
    }
}
