package com.informatics.research.quiznav.activities.quizes.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.activities.quizes.adapter.QuizesAdapter;
import com.informatics.research.quiznav.database.model.Quizes;
import com.informatics.research.quiznav.database.model.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.security.auth.Subject;

public class QuizesFragment extends Fragment {

    private ArrayList<Quizes> quizesArrayList;
    private HashMap<String, String> tempHistory = new HashMap<>(), resultContent, dfQuizes;
    private HashMap<String, HashMap<String, String>> dfQuizesResult;
    private String MaterialCode, SubjectCode;

    private DatabaseReference dbQuizes, dbResults, dbSql;
    private QuizesAdapter quizesAdapter;

    private RecyclerView rc_quizes_list_layout;
    private ProgressDialog loading;
    private RadioButton rb_todo, rb_doing, rb_remidial, rb_done;
    private TextView none_content;

    public QuizesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quizes, container, false);

        // Radio Button initial
        rb_todo = (RadioButton) rootView.findViewById(R.id.rb_todo);
        rb_doing = (RadioButton) rootView.findViewById(R.id.rb_doing);
        rb_remidial = (RadioButton) rootView.findViewById(R.id.rb_remidial);
        rb_done = (RadioButton) rootView.findViewById(R.id.rb_done);

        rc_quizes_list_layout = (RecyclerView) rootView.findViewById(R.id.rc_quizes_list_layout);

        none_content = (TextView) rootView.findViewById(R.id.none_content);

        tempHistory = (HashMap<String, String>) getActivity().getIntent().getSerializableExtra("Temp History");
        SubjectCode = tempHistory.get("Subject Code");
        MaterialCode = tempHistory.get("Material Code");

        dbQuizes = FirebaseDatabase.getInstance().getReference("quizes").child(SubjectCode).child(MaterialCode);
        dbResults = FirebaseDatabase.getInstance().getReference("results").child("16523060");
        dbSql = FirebaseDatabase.getInstance().getReference("sql").child("16523060").child(SubjectCode).child(MaterialCode);

        UpdateQuizesList("todo");
        QuizSorting();

        return rootView;
    }

    protected void QuizSorting(){
        rb_todo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList("todo");
            }
            }
        });

        rb_doing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList("doing");
                }
            }
        });

        rb_remidial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList("remidial");
                }
            }
        });

        rb_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList("passed");
                }
            }
        });

    }

    protected void UpdateQuizesList(final String quiz_status){
        switch (quiz_status){
            case "todo":
                dfQuizesResult = new HashMap<>();
                dfQuizes = new HashMap<>();

                dbSql.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            dfQuizes.put(ds.getKey(), ds.getValue().toString());
                        }
                        dfQuizesResult.put("List", dfQuizes);

                        LayoutViewer(!(dfQuizes.isEmpty()), "todo", dfQuizesResult);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
                default:
                    dfQuizesResult = new HashMap<>();
                    resultContent = new HashMap<>();
                    dfQuizes = new HashMap<>();

                    dbResults.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean status = false;
                            String quiz_code;

                            for(DataSnapshot ds : dataSnapshot.getChildren()){
                                String material_code = ds.child("material_code").getValue().toString();
                                String status_quiz = ds.child("quiz_status").getValue().toString();

                                if(material_code.equalsIgnoreCase(MaterialCode)){
                                    if(status_quiz.equalsIgnoreCase(quiz_status)){
                                        status = true;
                                        quiz_code = ds.child("quiz_code").getValue().toString();
                                        resultContent.put("quiz_status", ds.child("quiz_status").getValue().toString());
                                        resultContent.put("trying_count", ds.child("trying_count").getValue().toString());
                                        resultContent.put("scores", ds.child("score").getValue().toString());

                                        dfQuizes.put(ds.getKey(), quiz_code);
                                        dfQuizesResult.put(quiz_code, resultContent);
                                    }
                                }
                            }

                            dfQuizesResult.put("List", dfQuizes);
                            LayoutViewer(status, "not todo", dfQuizesResult);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;
        }
    }

    protected void LayoutViewer(boolean status, String quiz_status, HashMap<String, HashMap<String, String>> dfAppearedQuiz){
        if(status){
            rc_quizes_list_layout.setVisibility(View.VISIBLE);
            none_content.setVisibility(View.GONE);

            ShowQuizesList(dfAppearedQuiz, quiz_status);
        }else{
            rc_quizes_list_layout.setVisibility(View.GONE);
            none_content.setVisibility(View.VISIBLE);
        }
    }

    protected void ShowQuizesList(final HashMap<String, HashMap<String, String>> dfQuizesResult, final String quiz_status){
        // Recycle View iniitial
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rc_quizes_list_layout.setLayoutManager(mLayoutManager);
        rc_quizes_list_layout.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(getActivity(),
                null,
                "Please wait...",
                true,
                false);

        quizesArrayList = new ArrayList<>();
        dbQuizes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(quiz_status.equalsIgnoreCase("not todo")){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if (dfQuizesResult.get("List").containsValue(ds.child("quiz_code").getValue())){
                            quizesArrayList.add(ds.getValue(Quizes.class));
                        }
                    }
                }else{
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        if (!(dfQuizesResult.get("List").containsValue(ds.child("quiz_code").getValue()))){
                            quizesArrayList.add(ds.getValue(Quizes.class));
                        }
                    }
                }
                quizesAdapter = new QuizesAdapter(quizesArrayList, getActivity(), tempHistory, dfQuizesResult, quiz_status);
                rc_quizes_list_layout.setAdapter(quizesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        loading.dismiss();
    }
}
