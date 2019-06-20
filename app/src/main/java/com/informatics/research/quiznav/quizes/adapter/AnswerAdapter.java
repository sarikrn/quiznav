package com.informatics.research.quiznav.quizes.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.informatics.research.quiznav.R;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder> {

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private ArrayList<String> dfAnswers;
    private Activity mActivity;

    public AnswerAdapter(ArrayList<String> dfAnswers, Activity mActivity) {
        this.dfAnswers = dfAnswers;
        this.mActivity = mActivity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radio_answer;
        private LinearLayout answer_list_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            radio_answer = itemView.findViewById(R.id.radio_answer);
            answer_list_layout = itemView.findViewById(R.id.answer_list_linear_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_answer, parent, false);

        return new AnswerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final String answer = dfAnswers.get(position);

        holder.radio_answer.setText(answer);
        holder.radio_answer.setChecked(false);
        holder.radio_answer.setTag(new Integer(position));

//        for default check in first item
        if(position == 0 && holder.radio_answer.isChecked()) {
            lastChecked = holder.radio_answer;
            lastCheckedPos = 0;
        }
        holder.radio_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton cb = (RadioButton) v;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked()) {
                    if(lastChecked != null) {
                        lastChecked.setChecked(false);
                    }
                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }else
                    lastChecked = null;

                System.out.println("LastChecked: " + lastCheckedPos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dfAnswers.size();
    }
}
