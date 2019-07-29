package com.informatics.research.quiznav.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResultDb {
    private HashMap<String, HashMap<String, String>> dfAnswerUser;
    private DatabaseReference dbResult;

//    Tidak Terpakai
    public HashMap<String, HashMap<String, String>> getResultByID(String userID, String quizResultID){
        dfAnswerUser = new HashMap<>();
        dbResult = FirebaseDatabase.getInstance().getReference("results").child(userID);

        dbResult.child(quizResultID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String, String> additional = new HashMap<>();
                        additional.put("Quiz Status", dataSnapshot.child("quiz_status").getValue().toString());

                        dfAnswerUser.put("Answer List",(HashMap<String, String>) dataSnapshot.child("data").child("answer").getValue());
                        dfAnswerUser.put("Data to Send", additional);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        return dfAnswerUser;
    }

    public void saveResultByID(){

    }
}
