package com.informatics.research.quiznav.activities.quizes;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.activities.quizes.adapter.QuizesAdapter;
import com.informatics.research.quiznav.database.model.Quizes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;

import static java.sql.Types.NULL;

public class QuizesListActivity extends AppCompatActivity {

    private ArrayList<Quizes> quizesArrayList;
    private HashMap<String, String> tempHistory = new HashMap<>(), resultContent;
    private HashMap<String, HashMap<String, String>> dfQuizesResult;
    private String MaterialCode, MaterialName, MaterialDesc;

    private DatabaseReference dbQuizes, dbResults;
    private QuizesAdapter quizesAdapter;

    private TextView material_name, material_desc;
    private LinearLayout content, content_value_material_quizes, upper_info;
    private RecyclerView rc_quizes_list_layout;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizes_list);

        tempHistory = (HashMap<String, String>) getIntent().getSerializableExtra("Temp History");
        MaterialCode = tempHistory.get("Material Code");
        MaterialName = getIntent().getStringExtra("Material Title");
        MaterialDesc = getIntent().getStringExtra("Material Desc");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        content_value_material_quizes = (LinearLayout) findViewById(R.id.content_value_material_quizes);

        dbQuizes = FirebaseDatabase.getInstance().getReference("quizes").child(MaterialCode);
        dbResults = FirebaseDatabase.getInstance().getReference("results").child("16523060");

        Toolbar toolbar = findViewById(R.id.toolbar_material_and_quizes);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(Color.parseColor(tempHistory.get("Color")));
        toolbar.setTitleTextColor(Color.WHITE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(Color.parseColor(tempHistory.get("Color")));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        UpdateUIMateri();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
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
        upper_info = (LinearLayout) findViewById(R.id.upper_info);
        material_name = (TextView) findViewById(R.id.material_name);
        material_desc = (TextView) findViewById(R.id.material_desc);

        upper_info.setBackgroundColor(Color.parseColor(tempHistory.get("Color")));
        material_name.setText(MaterialName);
        material_name.setTextColor(Color.WHITE);

        material_desc.setText(MaterialDesc);
    }

    protected void UpdateUIKuis(){
        dbResults.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dfQuizesResult = new HashMap<>();
                resultContent = new HashMap<>();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    resultContent.put("quiz_status", ds.child("quiz_status").getValue().toString());
                    resultContent.put("scores", ds.child("scores").getValue().toString());
                    resultContent.put("trying_count", ds.child("trying_count").getValue().toString());

                    dfQuizesResult.put(ds.child("quiz_code").getValue().toString(), resultContent);
                }
                ShowQuizesList(dfQuizesResult);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void ShowQuizesList(final HashMap<String, HashMap<String, String>> dfQuizesResult){
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
                quizesArrayList = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    quizesArrayList.add(ds.getValue(Quizes.class));
                }

                quizesAdapter = new QuizesAdapter(quizesArrayList, QuizesListActivity.this, tempHistory, dfQuizesResult);
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
