package com.informatics.research.quiznav.database;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.informatics.research.quiznav.activities.quizes.QuizesListActivity;
import com.informatics.research.quiznav.database.model.Result;

import java.util.HashMap;

public class ResultDb {
    private DatabaseReference dbResult;

    public void saveResultByID(Result result, final Activity mActivity, final HashMap<String, String> tempHistory, String userID, String child_code){
        dbResult = FirebaseDatabase.getInstance().getReference("results").child(userID);

        dbResult.child(child_code)
                .setValue(result)
                .addOnSuccessListener(mActivity, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mActivity, "Kuis berhasil dikerjakan", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(mActivity, QuizesListActivity.class);
                        intent.putExtra("Temp History", tempHistory);
                        mActivity.startActivity(intent);
                    }
                });
    }
}
