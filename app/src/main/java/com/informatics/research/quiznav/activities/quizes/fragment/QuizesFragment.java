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

import java.util.ArrayList;
import java.util.HashMap;

public class QuizesFragment extends Fragment {

    private ArrayList<Quizes> quizesArrayList;
    private HashMap<String, String> tempHistory = new HashMap<>(), resultContent;
    private HashMap<String, HashMap<String, String>> dfQuizesResult;
    private String MaterialCode;

    private DatabaseReference dbQuizes, dbResults;
    private QuizesAdapter quizesAdapter;

    private RecyclerView rc_quizes_list_layout;
    private ProgressDialog loading;
    private RadioButton rb_todo, rb_doing, rb_remidial, rb_done;
    private TextView none_content;

    public QuizesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        MaterialCode = tempHistory.get("Material Code");

        dbQuizes = FirebaseDatabase.getInstance().getReference("quizes").child(MaterialCode);
        dbResults = FirebaseDatabase.getInstance().getReference("results").child("16523060");

        UpdateQuizesList(rootView, "todo");
        QuizSorting(rootView);

        return rootView;
    }

    protected void QuizSorting(final View rootView){
        rb_todo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList(rootView, "todo");
                }
            }
        });

        rb_doing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList(rootView, "doing");
                }
            }
        });

        rb_remidial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList(rootView, "remidial");
                }
            }
        });

        rb_done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    UpdateQuizesList(rootView, "passed");
                }
            }
        });

    }

    protected void UpdateQuizesList(final View rootView, final String quiz_status){
        dbResults.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean status = false;

                dfQuizesResult = new HashMap<>();
                resultContent = new HashMap<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String material_code = ds.child("material_code").getValue().toString();
                    String status_quiz = ds.child("quiz_status").getValue().toString();

                    if(material_code.equalsIgnoreCase(MaterialCode) && status_quiz.equalsIgnoreCase(quiz_status)){
                        status = true;

                        resultContent.put("quiz_status", ds.child("quiz_status").getValue().toString());
                        resultContent.put("scores", ds.child("score").getValue().toString());
                        resultContent.put("trying_count", ds.child("trying_count").getValue().toString());

                        dfQuizesResult.put(ds.child("quiz_code").getValue().toString(), resultContent);
                    }
                }

                if(status){
                    rc_quizes_list_layout.setVisibility(View.VISIBLE);
                    none_content.setVisibility(View.GONE);

                    ShowQuizesList(dfQuizesResult);
                }else{
                    rc_quizes_list_layout.setVisibility(View.GONE);
                    none_content.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void ShowQuizesList(final HashMap<String, HashMap<String, String>> dfQuizesResult){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rc_quizes_list_layout.setLayoutManager(mLayoutManager);
        rc_quizes_list_layout.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(getActivity(),
                null,
                "Please wait...",
                true,
                false);

        dbQuizes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizesArrayList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    quizesArrayList.add(ds.getValue(Quizes.class));
                }

                quizesAdapter = new QuizesAdapter(quizesArrayList, getActivity(), tempHistory, dfQuizesResult);
                rc_quizes_list_layout.setAdapter(quizesAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }
}
