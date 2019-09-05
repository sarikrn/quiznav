package com.informatics.research.quiznav.activities.materialList;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.activities.materialList.adapter.MaterialsListAdapter;
import com.informatics.research.quiznav.activities.quizes.QuizesListActivity;
import com.informatics.research.quiznav.database.model.Materials;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static java.sql.Types.NULL;

public class MaterialListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ProgressDialog loading;

    private RecyclerView rc_material_list_layout;
    private TextView setTitle, setLecturer;
    private LinearLayout card_view_layout;
    private CardView material_name_layout;

    private HashMap<String, String> tempHistory;
    private HashMap<String, Materials> materials;
    private ArrayList<Materials> materialsArrayList;
    private String SubjectCode, SubjectTitle, LecturerName;
    private MaterialsListAdapter materialsListAdapter;
    private LinearLayout content, content_value_material_quizes, upper_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        tempHistory = new HashMap<>((HashMap<String, String>) getIntent().getSerializableExtra("Temp History"));
        materials = (HashMap<String, Materials>) getIntent().getSerializableExtra("Materials");
        SubjectCode = tempHistory.get("Subject Code");
        SubjectTitle = getIntent().getStringExtra("Subject Title");


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        rc_material_list_layout = findViewById(R.id.rc_material_list_layout);
        setTitle = (TextView) findViewById(R.id.subject_name);
        setLecturer = (TextView) findViewById(R.id.lecturer_name);
        card_view_layout = findViewById(R.id.card_view_layout);
        material_name_layout = findViewById(R.id.material_name_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        card_view_layout.setBackgroundColor(Color.parseColor(tempHistory.get("Color")));
        material_name_layout.setRadius(20);

        setTitle.setText(SubjectTitle);
        setLecturer.setText(LecturerName);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rc_material_list_layout.setLayoutManager(mLayoutManager);
        rc_material_list_layout.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(MaterialListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        materialsArrayList = new ArrayList<>();
        for(HashMap.Entry<String, Materials> entry : materials.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
            materialsArrayList.add(entry.getValue());
        }
        materialsListAdapter = new MaterialsListAdapter(materialsArrayList, MaterialListActivity.this, tempHistory);
        rc_material_list_layout.setAdapter(materialsListAdapter);
        loading.dismiss();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() != NULL){
                switch (item.getItemId()) {
                    case R.id.navigation_dashboard:
                        content = (LinearLayout) View.inflate(MaterialListActivity.this, R.layout.content_material_list, null);

                        content_value_material_quizes.removeAllViews();
                        content_value_material_quizes.addView(content);

                        break;
                    case R.id.navigation_notifications:
                        content = (LinearLayout) View.inflate(MaterialListActivity.this, R.layout.content_quizes_list, null);

                        content_value_material_quizes.removeAllViews();
                        content_value_material_quizes.addView(content);

                        break;
                }
                return true;
            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.material, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_class) {
            // Handle the camera action
        } else if (id == R.id.nav_quiz) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
