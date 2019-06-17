package com.informatics.research.quiznav.material.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;
import com.informatics.research.quiznav.material.model.Materials;
import com.informatics.research.quiznav.quizes.QuizesActivity;

import java.util.ArrayList;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsAdapter.MyViewHolder> {

    private ArrayList<Materials> dfMaterials;
    private Activity mActivity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView material_list_layout;
        public TextView txt_material_name, txt_date_of_publication, txt_number_of_quizes;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            material_list_layout = itemView.findViewById(R.id.material_list_layout);
            txt_material_name = itemView.findViewById(R.id.material_name);
            txt_date_of_publication = itemView.findViewById(R.id.date_of_publication);
            txt_number_of_quizes = itemView.findViewById(R.id.number_of_quizes);
        }
    }

    public MaterialsAdapter(ArrayList<Materials> dfMaterials, Activity mActivity) {
        this.dfMaterials = dfMaterials;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_material, parent, false);

        return new MaterialsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Materials materials = dfMaterials.get(position);

        holder.txt_material_name.setText(materials.getTitle());

        holder.material_list_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDetail = new Intent(mActivity, QuizesActivity.class);
                goDetail.putExtra("Material Code", materials.getKey());
                mActivity.startActivity(goDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dfMaterials.size();
    }
}
