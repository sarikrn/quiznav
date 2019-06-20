package com.informatics.research.quiznav.quizes.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.quizes.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private ArrayList<String> dfAnswers;
    private ArrayList<Questions> dfQuestion;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_question_categorize, txt_question_desc, txt_number_of_question_card_view;
        private Button btn_submit_answer;
//        private RadioButton answer_A, answer_B, answer_C, answer_D, answer_E;
        private RecyclerView rc_answer_list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_number_of_question_card_view = itemView.findViewById(R.id.number_of_question_card_view);
            txt_question_categorize = itemView.findViewById(R.id.question_categorize);
            txt_question_desc = itemView.findViewById(R.id.question_desc);
            btn_submit_answer = itemView.findViewById(R.id.submit_answer);
//            answer_A = itemView.findViewById(R.id.answer_A);
//            answer_B = itemView.findViewById(R.id.answer_B);
//            answer_C = itemView.findViewById(R.id.answer_C);
//            answer_D = itemView.findViewById(R.id.answer_D);
//            answer_E = itemView.findViewById(R.id.answer_E);
            rc_answer_list = itemView.findViewById(R.id.rc_answer_list);
        }
    }

    public QuestionAdapter(ArrayList<Questions> dfQuestion, Activity mActivity) {
        this.dfQuestion = dfQuestion;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question, parent, false);

        return new QuestionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyViewHolder holder, int position) {
        final Questions questions = dfQuestion.get(position);

        holder.txt_number_of_question_card_view.setText(String.valueOf(position + 1));
        holder.txt_question_desc.setText(questions.getDesc());
        holder.txt_question_categorize.setText(questions.getCategorize());
        holder.txt_question_categorize.setTextColor(CategorizeLabelColor(questions.getCategorize()));

//        RecyclerView Answer
        AnswerAdapter answerAdapter;
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        holder.rc_answer_list.setLayoutManager(mLayoutManager);
        holder.rc_answer_list.setItemAnimator(new DefaultItemAnimator());

        dfAnswers = new ArrayList<>();
        for(HashMap.Entry<String, String> entry : questions.getAnswers().entrySet()){
            dfAnswers.add(entry.getValue());
        }
        answerAdapter = new AnswerAdapter(dfAnswers, mActivity);
        holder.rc_answer_list.setAdapter(answerAdapter);

        holder.btn_submit_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dfQuestion.size();
    }

    private Integer CategorizeLabelColor(String Categorize){
        Integer color = Color.DKGRAY;

        switch (Categorize){
            case "sulit" :
                color = Color.MAGENTA;
                break;
            case "mudah" :
                color = Color.BLUE;
                break;
            case "menengah":
                color = Color.DKGRAY;
                break;
        }

        return color;
    }
}
