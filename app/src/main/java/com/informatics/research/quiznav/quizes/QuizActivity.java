package com.informatics.research.quiznav.quizes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.materialList.MaterialListActivity;
import com.informatics.research.quiznav.materialList.adapter.MaterialsAdapter;
import com.informatics.research.quiznav.materialList.model.Materials;
import com.informatics.research.quiznav.quizes.adapter.QuestionAdapter;
import com.informatics.research.quiznav.quizes.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {

    private ProgressDialog loading;
    private QuestionAdapter questionAdapter;

    private HashMap<String, Questions> questionsHashMap;
    private ArrayList<Questions> questionsArrayList;

    private RecyclerView rc_question_list;
    private LinearLayout number_of_question_list_layout, number_of_question_linear_layout;
    private Button number_of_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        rc_question_list = findViewById(R.id.rc_question_list);
        number_of_question_list_layout = findViewById(R.id.number_of_question_list_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        questionsHashMap = (HashMap<String, Questions>) getIntent().getSerializableExtra("Questions");
        System.out.println("Question: " + questionsHashMap);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_question_list.setLayoutManager(mLayoutManager);
        rc_question_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(QuizActivity.this,
                null,
                "Please wait...",
                true,
                false);

        questionsArrayList = new ArrayList<>();
        int count = 1;
        for(HashMap.Entry<String, Questions> entry : questionsHashMap.entrySet()){
            number_of_question_linear_layout = (LinearLayout) View.inflate(QuizActivity.this, R.layout.list_number_of_question, null);
            number_of_question_list_layout.addView(number_of_question_linear_layout);
            number_of_question = (Button) findViewById(R.id.number_of_question);
            number_of_question.setText(String.valueOf(count));

            questionsArrayList.add(entry.getValue());

            count++;



        }
        questionAdapter = new QuestionAdapter(questionsArrayList, QuizActivity.this);
        rc_question_list.setAdapter(questionAdapter);
        loading.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
