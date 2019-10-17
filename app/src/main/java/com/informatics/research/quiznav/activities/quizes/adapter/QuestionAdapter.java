package com.informatics.research.quiznav.activities.quizes.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.database.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    public HashMap<String, HashMap<String, String>> dfAnswerUser;
    private ArrayList<Questions> dfQuestion;
    private ArrayList<String> dfQuizStatus = new ArrayList<>();
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

    public QuestionAdapter(ArrayList<Questions> dfQuestion, Activity mActivity, HashMap<String, HashMap<String, String>> dfAnswerUser) {
        this.dfQuestion = dfQuestion;
        this.mActivity = mActivity;
        this.dfAnswerUser = dfAnswerUser;

        dfQuizStatus.add("passed");
        dfQuizStatus.add("done");
        dfQuizStatus.add("remidial");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_question, parent, false);

        return new QuestionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Questions questions = dfQuestion.get(position);
        final String key = questions.getQuestion_code();
        String quiz_status = dfAnswerUser.get("Data to Send").get("Quiz Status");

        holder.txt_question_categorize.setTextColor(CategorizeLabelColor(questions.getCategorize()));
        holder.txt_question_point.setText("Point: " + questions.getPoint());
        holder.txt_number_of_question_card_view.setText(String.valueOf(position + 1));
        holder.txt_question_desc.setText(questions.getDesc());
        holder.txt_question_categorize.setText( questions.getCategorize());

        if (!(dfQuizStatus.contains(quiz_status))) {
            //Doing and To Do List
            for (HashMap.Entry<String, String> entry : questions.getAnswers().entrySet()) {
                RadioButton rb = new RadioButton(QuestionAdapter.this.mActivity);
                int id = Integer.parseInt(entry.getKey(), 29);

                rb.setId(id);
                rb.setText(entry.getValue());
                rb.setPadding(1, 1, 1, 1);

                if (!dfAnswerUser.isEmpty()) {
                    rb.setChecked(false);
                    if(dfAnswerUser.get("Answer List").containsKey(key)){
                        if (dfAnswerUser.get("Answer List").get(key).equalsIgnoreCase(entry.getKey())) {
                            rb.setChecked(true);
                        }
                    }
                }
                holder.radio_group_answer.addView(rb);
            }

            holder.radio_group_answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton rbChecked = (RadioButton) group.findViewById(checkedId);
                    lastChecked = GetKeyAnswer(rbChecked.getId());

                    dfAnswerUser.get("Answer List").put(key, lastChecked);
                }
            });
        }else{
            String answer = GetDescAnswer(questions.getAnswers(), dfAnswerUser.get("Answer List").get(key));

            TextView tv = new TextView(QuestionAdapter.this.mActivity);
            tv.setText("Jawaban: " + answer);
            tv.setTypeface(null, Typeface.BOLD);

            holder.radio_group_answer.addView(tv);
        }
    }

    @Override
    public int getItemCount() {
        return dfQuestion.size();
    }

    private Integer CategorizeLabelColor(String Categorize) {
        Integer color = Color.DKGRAY;

        switch (Categorize) {
            case "SULIT":
                color = Color.RED;
                break;
            case "MUDAH":
                color = Color.CYAN;
                break;
            case "SEDANG":
            case "MENENGAH":
                color = Color.DKGRAY;
                break;
        }

        return color;
    }

    private String GetKeyAnswer(int key) {
        HashMap<Integer, String> dfKeyAnswer = new HashMap<>();
        dfKeyAnswer.put(0,"a");
        dfKeyAnswer.put(1,"b");
        dfKeyAnswer.put(2,"c");
        dfKeyAnswer.put(3,"d");
        dfKeyAnswer.put(4,"e");

        String res = "";
        for (HashMap.Entry<Integer, String> entry : dfKeyAnswer.entrySet()) {
            if (entry.getKey().equals(key-10)) {
                res = entry.getValue();
                break;
            }
        }
        return res;
    }

    private String GetDescAnswer(HashMap<String, String> dfAnswer, String key){
        String res = "";

        for (HashMap.Entry<String, String> entry : dfAnswer.entrySet()) {
            if(key.equalsIgnoreCase(entry.getKey())){
                res = entry.getValue();
                break;
            }
        }
        return res;
    }
}
