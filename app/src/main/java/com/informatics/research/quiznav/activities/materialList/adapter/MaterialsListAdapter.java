package com.informatics.research.quiznav.activities.materialList.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.database.model.Materials;
import com.informatics.research.quiznav.activities.quizes.QuizesListActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MaterialsListAdapter extends RecyclerView.Adapter<MaterialsListAdapter.MyViewHolder> {

    private HashMap<String, String> tempHistory;
    private ArrayList<Materials> dfMaterials;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView material_list_layout;
        public TextView txt_material_name, txt_date_of_publication;
//        public ProgressBar progress_bar_material;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            material_list_layout = itemView.findViewById(R.id.material_list_layout);
            txt_material_name = itemView.findViewById(R.id.material_name);
            txt_date_of_publication = itemView.findViewById(R.id.date_of_publication);
//            progress_bar_material = itemView.findViewById(R.id.progress_bar_material);
        }
    }

    public MaterialsListAdapter(ArrayList<Materials> dfMaterials, Activity mActivity, HashMap<String, String> tempHistory) {
        this.dfMaterials = dfMaterials;
        this.mActivity = mActivity;
        this.tempHistory = tempHistory;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_material, parent, false);

        return new MaterialsListAdapter.MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Materials materials = dfMaterials.get(position);

        holder.txt_material_name.setText(materials.getTitle());
//        holder.progress_bar_material.setProgress(78, true);

        holder.material_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tempHistory.put("Material Code", materials.getMaterials_code());

                Intent goDetail = new Intent(mActivity, QuizesListActivity.class);
                goDetail.putExtra("Temp History", tempHistory)
                        .putExtra("Material Title", materials.getTitle())
                        .putExtra("Material Desc", materials.getDesc());
                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dfMaterials.size();
    }
}
