package com.informatics.research.quiznav.quizes.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.quizes.QuizActivity;
import com.informatics.research.quiznav.quizes.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private HashMap<Integer, String> dfKeyAnswer = new HashMap<>();
    public HashMap<String, String> dfAnswerFromUser = new HashMap<>();
    private ArrayList<Questions> dfQuestion;

    private String lastChecked = null;

    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_question_categorize, txt_question_desc, txt_number_of_question_card_view, txt_question_point;
        private RadioGroup radio_group_answer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_number_of_question_card_view = itemView.findViewById(R.id.number_of_question_card_view);
            txt_question_categorize = itemView.findViewById(R.id.question_categorize);
            txt_question_desc = itemView.findViewById(R.id.question_desc);
            radio_group_answer = itemView.findViewById(R.id.radio_group_answer);
            txt_question_point = itemView.findViewById(R.id.question_point);
        }
    }

    public QuestionAdapter(ArrayList<Questions> dfQuestion, Activity mActivity) {
        this.dfQuestion = dfQuestion;
        this.mActivity = mActivity;

        this.dfKeyAnswer.put(10, "a");
        this.dfKeyAnswer.put(11, "b");
        this.dfKeyAnswer.put(12, "c");
        this.dfKeyAnswer.put(13, "d");
        this.dfKeyAnswer.put(14, "e");

        System.out.println("Questions in Adapter: " + this.dfQuestion);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question, parent, false);

        return new QuestionAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull QuestionAdapter.MyViewHolder holder, int position) {
        final Questions questions = dfQuestion.get(position);

        this.dfAnswerFromUser.put(questions.getQuestion_code(), "");

        holder.txt_number_of_question_card_view.setText(String.valueOf(position + 1));
        holder.txt_question_desc.setText(questions.getDesc());
        holder.txt_question_categorize.setText(questions.getCategorize());
        holder.txt_question_categorize.setTextColor(CategorizeLabelColor(questions.getCategorize()));
        holder.txt_question_point.setText("Point: " + questions.getWeight());

        for (HashMap.Entry<String, String> entry : questions.getAnswers().entrySet()) {
            RadioButton rb = new RadioButton(QuestionAdapter.this.mActivity);
            int key = Integer.parseInt(entry.getKey(), 29);

            rb.setId(key);
            rb.setText(entry.getValue());
            rb.setPadding(1, 1, 1, 1);

            holder.radio_group_answer.addView(rb);
        }

        holder.radio_group_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rbChecked = (RadioButton) group.findViewById(checkedId);
                lastChecked = GetKeyAnswer(rbChecked.getId());
                System.out.println("Last Checked: " + lastChecked + " from: " + questions.getQuestion_code());

                dfAnswerFromUser.put(questions.getQuestion_code(), lastChecked);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dfQuestion.size();
    }

    private Integer CategorizeLabelColor(String Categorize) {
        Integer color = Color.DKGRAY;

        switch (Categorize) {
            case "sulit":
                color = Color.RED;
                break;
            case "mudah":
                color = Color.CYAN;
                break;
            case "menengah":
                color = Color.DKGRAY;
                break;
        }

        return color;
    }

    private String GetKeyAnswer(int key) {
        String res = "";

        for (HashMap.Entry<Integer, String> entry : dfKeyAnswer.entrySet()) {
            if (entry.getKey().equals(key)) {
                res = entry.getValue();
                break;
            }
        }
        return res;
    }
}
