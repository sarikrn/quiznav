package com.informatics.research.quiznav.quizes;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.quizes.adapter.QuizesAdapter;
import com.informatics.research.quiznav.quizes.model.Questions;
import com.informatics.research.quiznav.quizes.model.Quizes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import static java.sql.Types.NULL;

public class QuizesListActivity extends AppCompatActivity {

    private ArrayList<Quizes> quizesArrayList;
    private HashMap<String, Quizes> quizesHashMap;
    private String MaterialCode, MaterialName, MaterialDesc;

    private DatabaseReference dbQuizes;
    private QuizesAdapter quizesAdapter;

    private TextView material_name, material_desc;
    private LinearLayout content, content_value_material_quizes;
    private RecyclerView rc_quizes_list_layout;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes_list);
        setTitle("Materi");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        content_value_material_quizes = (LinearLayout) findViewById(R.id.content_value_material_quizes);

        MaterialCode = getIntent().getStringExtra("Material Code");
        MaterialName = getIntent().getStringExtra("Material Name");
        MaterialDesc = getIntent().getStringExtra("Material Desc");

        dbQuizes = FirebaseDatabase.getInstance().getReference("quizes").child(MaterialCode);
    }

    @Override
    protected void onStart() {
        super.onStart();

        UpdateUIMateri();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() != NULL){
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        content = (LinearLayout) View.inflate(QuizesListActivity.this, R.layout.content_material, null);

                        content_value_material_quizes.removeAllViews();
                        content_value_material_quizes.addView(content);

                        UpdateUIMateri();
                        break;
                    case R.id.navigation_notifications:
                        content = (LinearLayout) View.inflate(QuizesListActivity.this, R.layout.content_quizes_list, null);

                        content_value_material_quizes.removeAllViews();
                        content_value_material_quizes.addView(content);

                        UpdateUIKuis();
                        break;
                }
                return true;
            }
            return false;
        }
    };

    protected void UpdateUIMateri(){
        material_name = (TextView) findViewById(R.id.material_name);
        material_desc = (TextView) findViewById(R.id.material_desc);

        material_name.setText(MaterialName);
        material_desc.setText(MaterialDesc);
    }

    protected void UpdateUIKuis(){
        rc_quizes_list_layout = (RecyclerView) findViewById(R.id.rc_quizes_list_layout);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_quizes_list_layout.setLayoutManager(mLayoutManager);
        rc_quizes_list_layout.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(QuizesListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        dbQuizes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quizesHashMap = new HashMap<>();
                quizesArrayList = new ArrayList<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    System.out.println(ds.getKey() + " " + ds.getValue());
                    quizesArrayList.add(ds.getValue(Quizes.class));
                    quizesHashMap.put(ds.getKey(), ds.getValue(Quizes.class));
                }

                quizesAdapter = new QuizesAdapter(quizesArrayList, QuizesListActivity.this, MaterialCode);
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
