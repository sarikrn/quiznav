package com.informatics.research.quiznav.activities.quizes.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.activities.quizes.adapter.QuizesAdapter;
import com.informatics.research.quiznav.database.model.Quizes;

import java.util.ArrayList;
import java.util.HashMap;

public class MaterialFragment extends Fragment {
    private ArrayList<Quizes> quizesArrayList;
    private HashMap<String, String> tempHistory = new HashMap<>(), resultContent;
    private HashMap<String, HashMap<String, String>> dfQuizesResult;
    private String MaterialCode, MaterialName, MaterialDesc;

    private DatabaseReference dbQuizes, dbResults;
    private QuizesAdapter quizesAdapter;

    private TextView material_name, material_desc;
    private View divider;
    private LinearLayout content, content_value_material_quizes, upper_info;
    private RecyclerView rc_quizes_list_layout;

    private ProgressDialog loading;

    public MaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_material, container, false);

        tempHistory = (HashMap<String, String>) getActivity().getIntent().getSerializableExtra("Temp History");
        MaterialCode = tempHistory.get("Material Code");
        MaterialName = getActivity().getIntent().getStringExtra("Material Title");
        MaterialDesc = getActivity().getIntent().getStringExtra("Material Desc");

        dbQuizes = FirebaseDatabase.getInstance().getReference("quizes").child(MaterialCode);
        dbResults = FirebaseDatabase.getInstance().getReference("results").child("16523060");

        UpdateUIMateri(tempHistory, rootView);

        return rootView;
    }

    protected void UpdateUIMateri(HashMap<String, String> tempHistory, View rootView){
        material_name = (TextView) rootView.findViewById(R.id.material_name);
        material_desc = (TextView) rootView.findViewById(R.id.material_desc);
        divider = (View) rootView.findViewById(R.id.divider);

        material_name.setText(MaterialName);
        material_name.setTextColor(Color.parseColor(tempHistory.get("Color")));
        material_desc.setText(MaterialDesc);
        divider.setBackgroundColor(Color.parseColor(tempHistory.get("Color")));
    }

}
