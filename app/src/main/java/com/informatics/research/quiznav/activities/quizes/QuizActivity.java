package com.informatics.research.quiznav.activities.quizes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.database.ResultDb;
import com.informatics.research.quiznav.activities.quizes.adapter.QuestionAdapter;
import com.informatics.research.quiznav.database.model.Questions;
import com.informatics.research.quiznav.database.model.Result;

import java.util.ArrayList;
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
    private String MaterialCode, QuizCode;
    private int resultCount;

    private RecyclerView rc_question_list;
    private LinearLayout number_of_question_list_layout, number_of_question_linear_layout, button_submit_layout;
    private Button number_of_question, submit_answer, save_and_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        rc_question_list = (RecyclerView) findViewById(R.id.rc_question_list);
        number_of_question_list_layout = findViewById(R.id.number_of_question_list_layout);
        submit_answer = findViewById(R.id.submit_answer);
        save_and_exit = findViewById(R.id.save_and_exit);
        button_submit_layout = findViewById(R.id.button_submit_layout);

        tempHistory = new HashMap<>((HashMap<String, String>) getIntent().getSerializableExtra("Temp History"));
        QuizCode = tempHistory.get("Quiz Code");
        MaterialCode = tempHistory.get("Material Code");

        dbQuestion = database.getReference("quizes").child(MaterialCode).child(QuizCode).child("questions");
        dbResult = database.getReference("results").child("16523060");

        resultCount = getAnswerHistoryCount();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ReadHistoryOfResultQuiz();
//        submit_answer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Submit();
//            }
//        });
//        save_and_exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SaveAndExit();
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void ReadHistoryOfResultQuiz() {
        System.out.println("resultDb " +  resultDb.getResultByID("16523060", "1"));
//        dfAnswerUser = new HashMap<>();
//
//        dbResult.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String child = String.valueOf(getAnswerHistoryCount());
//                Boolean status = false;
//
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    if (
//                            ds.child("material").getValue().equals(tempHistory.get("Material Code")) &&
//                                    ds.child("quiz_code").getValue().equals(tempHistory.get("Quiz Code")) &&
//                                    ds.child("subject").getValue().equals(tempHistory.get("Subject Code"))
//                    ) {
//                        child = ds.getKey();
//                        status = true;
//
//                        if(ds.child("quiz_status").getValue().toString().equalsIgnoreCase("finished")){
//                            button_submit_layout.setVisibility(View.GONE);
//                        }
//                        break;
//                    }
//                }
//
//                if(!status){
////                    resultDb.getResult("16523060", child);
//                    System.out.println(resultDb.getResult("16523060", child));
////                    dbResult.child(child)
////                            .addValueEventListener(new ValueEventListener() {
////                                @Override
////                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                                    HashMap<String, String> additional = new HashMap<>();
////                                    additional.put("Quiz Status", dataSnapshot.child("quiz_status").getValue().toString());
////
////                                    dfAnswerUser.put("Answer List",(HashMap<String, String>) dataSnapshot.child("data").child("answer").getValue());
////                                    dfAnswerUser.put("Data to Send", additional);
////                                    QuestionList(dfAnswerUser);
////                                }
////
////                                @Override
////                                public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                }
////                            });
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

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
        int count = 1;
        for (HashMap.Entry<String, Questions> entry : questionsHashMap.entrySet()) {
            number_of_question_linear_layout = (LinearLayout) View.inflate(QuizActivity.this, R.layout.list_number_of_question, null);

            number_of_question_list_layout.addView(number_of_question_linear_layout);
            number_of_question = (Button) findViewById(R.id.number_of_question);
            number_of_question.setText(String.valueOf(count));

            questionsArrayList.add(entry.getValue());

            count++;
        }
        questionAdapter = new QuestionAdapter(questionsArrayList, QuizActivity.this, dfAnswerUser);
        rc_question_list.setAdapter(questionAdapter);
        loading.dismiss();
    }

//    private void Submit() {
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//        final String currentTime = dateFormat.format(new Date());
//
//        dbQuestion.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                HashMap<String, String> remidial_status = new HashMap<>();
//                remidial_status.put("remidial_chance","");
//                remidial_status.put("source","Save and Exit");
//
//                int score = 0, wa = 0, acc = 0, passed_score = 0;
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    remidial_status.put("remidial_status", ds.child("remidial_chance").getValue().toString());
//                    passed_score = Integer.parseInt(ds.child("passed_score").getValue().toString());
//
//                    String keyAnswer = ds.child("key").getValue().toString();
//                    if (questionAdapter.dfAnswerUser.get(ds.getKey()).equalsIgnoreCase(keyAnswer)) {
//                        acc += 1;
//                        score += Integer.parseInt(ds.child("weight").getValue().toString());
//                    } else {
//                        wa += 1;
//                    }
//                }
//
//                dfAnswerUser.remove("Quiz Status");
//                SaveResultAnswer(new Result(
//                        currentTime,
//                        tempHistory.get("Subject Code"),
//                        tempHistory.get("Material Code"),
//                        tempHistory.get("Quiz Code"),
//                        String.valueOf(score),
//                        "finished",
//                        getScoreStatus(score, passed_score),
//                        null,
//                        new UserAnswer(
//                                String.valueOf(wa),
//                                String.valueOf(acc),
//                                questionAdapter.dfAnswerUser
//                        )
//                ),remidial_status);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    protected void SaveAndExit() {
//        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//        final String currentTime = dateFormat.format(new Date());
//
//        dbQuestion.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                HashMap<String, String> remidial_status = new HashMap<>();
//                remidial_status.put("remidial_chance","");
//                remidial_status.put("source","Save and Exit");
//
//                int score = 0, wa = 0, acc = 0, passed_score = 0;
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    remidial_status.put("remidial_chance", ds.child("remidial_chance").getValue().toString());
//                    passed_score = Integer.parseInt(ds.child("passed_score").getValue().toString());
//
//                    String keyAnswer = ds.child("key").getValue().toString();
//                    if (questionAdapter.dfAnswerUser.get(ds.getKey()).equalsIgnoreCase(keyAnswer)) {
//                        acc += 1;
//                        score += Integer.parseInt(ds.child("weight").getValue().toString());
//                    } else {
//                        wa += 1;
//                    }
//                }
//
//                dfAnswerUser.remove("Quiz Status");
//                SaveResultAnswer(new Result(
//                        currentTime,
//                        tempHistory.get("Subject Code"),
//                        tempHistory.get("Material Code"),
//                        tempHistory.get("Quiz Code"),
//                        String.valueOf(score),
//                        "doing",
//                        getScoreStatus(score, passed_score),
//                        null,
//                        new UserAnswer(
//                                String.valueOf(wa),
//                                String.valueOf(acc),
//                                questionAdapter.dfAnswerUser
//                        )
//                ),remidial_status);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    protected void SaveResultAnswer(final Result result, final HashMap<String, String> remidial_status) {
        dbResult.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String child = String.valueOf(getAnswerHistoryCount() + 1);
                int trying_count = 0;

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (
                            ds.child("material").getValue().equals(tempHistory.get("Material Code")) &&
                                    ds.child("quiz_code").getValue().equals(tempHistory.get("Quiz Code")) &&
                                    ds.child("subject").getValue().equals(tempHistory.get("Subject Code"))
                    ) {
                        child = ds.getKey();
                        trying_count = Integer.parseInt(ds.child("remidial").getValue().toString());
                        break;
                    }
                }

                int remidial_chance = Integer.parseInt(remidial_status.get("remidial_chance"));
                if(remidial_status.get("source").toString().equalsIgnoreCase("Submit")){
                    remidial_chance = remidial_chance - trying_count;
                }

                result.setTrying_count(String.valueOf(remidial_chance));

                dbResult.child(child)
                        .setValue(result)
                        .addOnSuccessListener(QuizActivity.this, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(QuizActivity.this, "Kuis berhasil dikerjakan", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(QuizActivity.this, QuizesListActivity.class);
                                intent.putExtra("Temp History", tempHistory);
                                startActivity(intent);
                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    protected String getScoreStatus(int score, int passed_score){
        if(score >= passed_score){
            return "passed";
        }
        return "not passed";
    }
}
