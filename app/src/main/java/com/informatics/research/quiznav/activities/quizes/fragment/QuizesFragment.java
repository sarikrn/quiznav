package com.informatics.research.quiznav.activities.quizes.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    public QuizesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_quizes, container, false);

        tempHistory = (HashMap<String, String>) getActivity().getIntent().getSerializableExtra("Temp History");
        MaterialCode = tempHistory.get("Material Code");

        dbQuizes = FirebaseDatabase.getInstance().getReference("quizes").child(MaterialCode);
        dbResults = FirebaseDatabase.getInstance().getReference("results").child("16523060");

        UpdateUIKuis(rootView);

        return rootView;
    }

    protected void UpdateUIKuis(final View rootView){
        dbResults.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dfQuizesResult = new HashMap<>();
                resultContent = new HashMap<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    resultContent.put("quiz_status", ds.child("quiz_status").getValue().toString());
                    resultContent.put("scores", ds.child("score").getValue().toString());
                    resultContent.put("trying_count", ds.child("trying_count").getValue().toString());

                    dfQuizesResult.put(ds.child("quiz_code").getValue().toString(), resultContent);
                }
                ShowQuizesList(dfQuizesResult, rootView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void ShowQuizesList(final HashMap<String, HashMap<String, String>> dfQuizesResult, View rootView){
        rc_quizes_list_layout = (RecyclerView) rootView.findViewById(R.id.rc_quizes_list_layout);

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
