package com.informatics.research.quiznav.activities.quizes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.database.MyCallback;
import com.informatics.research.quiznav.database.QuizesDb;
import com.informatics.research.quiznav.database.ResultDb;
import com.informatics.research.quiznav.activities.quizes.adapter.QuestionAdapter;
import com.informatics.research.quiznav.database.model.Questions;
import com.informatics.research.quiznav.database.model.Result;
import com.informatics.research.quiznav.database.model.UserAnswer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbQuestion, dbResult;
    private ResultDb resultDb = new ResultDb();

    private ProgressDialog loading;
    private QuestionAdapter questionAdapter;

    private HashMap<String, HashMap<String, String>> dfAnswerUser;
    private HashMap<String, String> tempHistory;
    private HashMap<String, Questions> questionsHashMap;
    private ArrayList<Questions> questionsArrayList;
    private String MaterialCode, QuizCode, SubjectCode;
    private int resultCount;

    private RecyclerView rc_question_list;
    private LinearLayout button_submit_layout;
    private Button submit_answer, save_and_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        rc_question_list = (RecyclerView) findViewById(R.id.rc_question_list);
        submit_answer = findViewById(R.id.submit_answer);
        save_and_exit = findViewById(R.id.save_and_exit);
        button_submit_layout = findViewById(R.id.button_submit_layout);

        tempHistory = new HashMap<>((HashMap<String, String>) getIntent().getSerializableExtra("Temp History"));
        QuizCode = tempHistory.get("Quiz Code");
        MaterialCode = tempHistory.get("Material Code");
        SubjectCode = tempHistory.get("Subject Code");

        dbQuestion = database.getReference("quizes").child(SubjectCode).child(MaterialCode).child(QuizCode).child("questions");
        dbResult = database.getReference("results").child("16523060");

        ResultDb resultDb = new ResultDb();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ReadHistoryOfQuiz();
        submit_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveResultForButton("submit");
            }
        });
        save_and_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveResultForButton("save and exit");
            }
        });
    }

    // READ function: To read the question data from Result and Quizes parent
    private void ReadHistoryOfQuiz() {
        dfAnswerUser = new HashMap<>();

        dbResult.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String child = String.valueOf(getAnswerHistoryCount());
                boolean status = false;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (
                            ds.child("material_code").getValue().equals(MaterialCode) &&
                                    ds.child("quiz_code").getValue().equals(QuizCode) &&
                                    ds.child("subject_code").getValue().equals(SubjectCode)
                    ) {
                        child = ds.getKey();
                        status = true;

                        String quiz_status = ds.child("quiz_status").getValue().toString();

                        if(quiz_status.equalsIgnoreCase("passed") || quiz_status.equalsIgnoreCase("remidial")){
                            button_submit_layout.setVisibility(View.GONE);
                        }
                        break;
                    }
                }

                if(status){
                    dbResult.child(child)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    HashMap<String, String> additional = new HashMap<>();
                                    additional.put("Quiz Status", dataSnapshot.child("quiz_status").getValue().toString());

                                    dfAnswerUser.put("Answer List",(HashMap<String, String>) dataSnapshot.child("data").child("answer").getValue());
                                    dfAnswerUser.put("Data to Send", additional);
                                    QuestionList(dfAnswerUser);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                }else{
                    HashMap<String, String> additional = new HashMap<>();

                    additional.put("Quiz Status", "todo");
                    dfAnswerUser.put("Data to Send", additional);
                    QuestionList(dfAnswerUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Adapter function: Read the quiestion data and making the looping
    private void QuestionList(HashMap<String, HashMap<String, String>> dfAnswerUser) {
        questionsHashMap = (HashMap<String, Questions>) getIntent().getSerializableExtra("Questions");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_question_list.setLayoutManager(mLayoutManager);
        rc_question_list.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(QuizActivity.this,
                null,
                "Please wait...",
                true,
                false);

        questionsArrayList = new ArrayList<>();
        for (HashMap.Entry<String, Questions> entry : questionsHashMap.entrySet()) {

            questionsArrayList.add(entry.getValue());
        }
        questionAdapter = new QuestionAdapter(questionsArrayList, QuizActivity.this, dfAnswerUser);
        rc_question_list.setAdapter(questionAdapter);
        loading.dismiss();
    }

    protected int getAnswerHistoryCount() {
        dbResult.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                resultCount = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return resultCount;
    }

    protected void SaveResultForButton(final String button_status){
        QuizesDb quizesDb = new QuizesDb();

        final HashMap<String, String> remidial_status = new HashMap<>();
        remidial_status.put("source", button_status);

        tempHistory.put("Key", "chance");
        quizesDb.getDataByKey(new MyCallback() {
            @Override
            public void onCallback(String value) {
                remidial_status.put("remidial_chance", value);
            }
        }, tempHistory);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        final String currentTime = dateFormat.format(new Date());

        dbQuestion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int score = 0, wa = 0, acc = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String keyAnswer = ds.child("key").getValue().toString();
                    if(questionAdapter.dfAnswerUser.get("Answer List").containsKey(ds.getKey())){
                        if (questionAdapter.dfAnswerUser.get("Answer List").get(ds.getKey()).equalsIgnoreCase(keyAnswer)) {
                            acc += 1;
                            score += Integer.parseInt(ds.child("point").getValue().toString());
                        } else {
                            wa += 1;
                        }
                    }
                }

                SaveResultAnswer(new Result(
                        currentTime,
                        SubjectCode,
                        MaterialCode,
                        QuizCode,
                        String.valueOf(score),
                        questionAdapter.dfAnswerUser.get("Data to Send").get("Quiz Status"),
                        null,
                        new UserAnswer(
                                String.valueOf(wa),
                                String.valueOf(acc),
                                questionAdapter.dfAnswerUser.get("Answer List")
                        )
                ),remidial_status);
                questionAdapter.dfAnswerUser.get("Data to Send").remove("Quiz Status");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void SaveResultAnswer(final Result result, final HashMap<String, String> remidial_status) {
        dbResult.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String child = String.valueOf(getAnswerHistoryCount() + 1);
                int trying_count = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (
                            ds.child("material_code").getValue().equals(MaterialCode) &&
                                    ds.child("quiz_code").getValue().equals(QuizCode) &&
                                    ds.child("subject_code").getValue().equals(SubjectCode)
                    ) {
                        child = ds.getKey();
                        trying_count = Integer.parseInt(ds.child("trying_count").getValue().toString());
                        break;
                    }
                }

                int remidial_chance = Integer.parseInt(remidial_status.get("remidial_chance"));
                if(remidial_status.get("source").equalsIgnoreCase("submit")){
                    trying_count = remidial_chance - trying_count;
                }

                result.setTrying_count(String.valueOf(trying_count));
                resultDb.saveResultByID(result, QuizActivity.this, tempHistory, "16523060", child);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
