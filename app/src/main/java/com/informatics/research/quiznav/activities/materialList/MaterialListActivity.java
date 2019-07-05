package com.informatics.research.quiznav.activities.materialList;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.activities.materialList.adapter.MaterialsListAdapter;
import com.informatics.research.quiznav.database.model.Materials;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_list);

        tempHistory = new HashMap<>((HashMap<String, String>) getIntent().getSerializableExtra("Temp History"));
        materials = (HashMap<String, Materials>) getIntent().getSerializableExtra("Materials");
        SubjectCode = tempHistory.get("Subject Code");
        SubjectTitle = getIntent().getStringExtra("Subject Title");
        LecturerName = getIntent().getStringExtra("Lecturer Name");

        rc_material_list_layout = findViewById(R.id.rc_material_list_layout);
        setTitle = (TextView) findViewById(R.id.subject_name);
        setLecturer = (TextView) findViewById(R.id.lecturer_name);
        card_view_layout = findViewById(R.id.card_view_layout);
        material_name_layout = findViewById(R.id.material_name_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
