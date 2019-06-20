package com.informatics.research.quiznav.quizes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.quizes.adapter.QuestionAdapter;
import com.informatics.research.quiznav.quizes.model.Questions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class QuizActivity extends AppCompatActivity {

    private DatabaseReference dbQuestion;

    private ProgressDialog loading;
    private QuestionAdapter questionAdapter;

    private HashMap<String, String> dfAnswersUser = new HashMap<>();
    private HashMap<String, Questions> questionsHashMap;
    private ArrayList<Questions> questionsArrayList;
    private String MaterialCode, QuizCode;

    private RecyclerView rc_question_list;
    private LinearLayout number_of_question_list_layout, number_of_question_linear_layout;
    private Button number_of_question, submit_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        MaterialCode = getIntent().getStringExtra("Material Code");
        QuizCode = getIntent().getStringExtra("Quiz Code");

        dbQuestion = FirebaseDatabase.getInstance().getReference("quizes").child(MaterialCode).child(QuizCode).child("questions");

        rc_question_list = (RecyclerView) findViewById(R.id.rc_question_list);
        number_of_question_list_layout = findViewById(R.id.number_of_question_list_layout);
        submit_answer = findViewById(R.id.submit_answer);
    }

    @Override
    protected void onStart() {
        super.onStart();

        submit_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbQuestion.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.out.println("Jawaban diterima: " + questionAdapter.dfAnswerFromUser);

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String keyAnswer = ds.child("key").getValue().toString();
                            System.out.println("Quiz Code: " + ds.getKey());
                            System.out.println("Key: " + keyAnswer);
                            System.out.println("Jawaban: " + questionAdapter.dfAnswerFromUser.get(ds.getKey()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//                for (HashMap.Entry<String, String> entry : questionAdapter.dfAnswerFromUser.entrySet()){
//
//                }
            }
        });

        QuestionList();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void QuestionList() {
        questionsHashMap = (HashMap<String, Questions>) getIntent().getSerializableExtra("Questions");
        System.out.println("Questions Intent: " + questionsHashMap);

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
        for (HashMap.Entry<String, Questions> entry : questionsHashMap.entrySet()) {
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
}
